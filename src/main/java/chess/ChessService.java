package chess;

import static chess.dao.MysqlConnector.*;

import chess.dao.BoardDao;
import chess.dao.PieceDao;
import chess.domain.Board;
import chess.domain.BoardInitializer;
import chess.domain.Score;
import chess.domain.piece.Symbol;
import chess.domain.command.Command;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.postion.Position;
import chess.domain.state.State;
import chess.domain.state.StateFactory;
import chess.dto.board.MovePositionDto;
import chess.dto.ResponseDto;
import chess.dto.ResultDto;
import chess.dto.board.BoardDto;
import chess.dto.board.BoardInformationDto;
import chess.dto.board.PieceDto;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public final class ChessService {
    private final BoardDao boardDao = new BoardDao();
    private final PieceDao pieceDao = new PieceDao();

    public ResponseDto move(final String positions) throws SQLException {
        final Command command = Command.from("move " + positions);

        try {
            command.changeChessState(makeState());
            updateBoard(positions);

        } catch (IllegalArgumentException ex) {
            return new ResponseDto(400, ex.getMessage(), makeState().isGameOver());
        }

        return new ResponseDto(200, "", makeState().isGameOver());
    }

    private void updateBoard(final String positions) throws SQLException {
        final BoardInformationDto informationDto = boardDao.findRecentBoard(connect());
        final int boardId = informationDto.getId();
        final String turn = informationDto.getTurn();

        final MovePositionDto dto = MovePositionDto.of(positions);
        boardDao.updateTurnById(new BoardInformationDto(boardId, decideTurn(turn)), connect());
        pieceDao.deleteByIdAndPosition(boardId, dto.getTarget(), connect());
        pieceDao.updatePositionByBoardId(MovePositionDto.of(positions), boardId, connect());
    }

    private String decideTurn(final String turn) {
        String newTurn = Team.WHITE.name();
        if (turn.equals(Team.WHITE.name())) {
            newTurn = Team.BLACK.name();
        }

        return newTurn;
    }

    public void restart() throws SQLException {
        final BoardInformationDto informationDto = boardDao.findRecentBoard(connect());
        final int boardId = informationDto.getId();

        boardDao.deleteById(boardId, connect());
        pieceDao.deleteById(boardId, connect());
        save(new BoardInitializer().init());
    }

    private void save(final Board board) throws SQLException {
        final int newBoardId = boardDao.save(Team.WHITE.name(), connect());
        final Map<Position, Piece> cells = board.cells();

        for (Position position : cells.keySet()) {
            Piece piece = cells.get(position);
            savePiece(position, piece.symbol(), newBoardId);
        }
    }

    private void savePiece(final Position position, final String symbol, final int boardId) throws SQLException {
        int file = position.getFile().getNumber();
        int rank = position.getRank().getNumber();

        pieceDao.save(file, rank, Symbol.consoleSymbolToWebSymbol(symbol), boardId, connect());
    }

    public Score status() throws SQLException {
        return Score.from(makeState().board());
    }

    public ResultDto result() throws SQLException {
        return ResultDto.of(makeState());
    }

    public BoardDto getBoard() throws SQLException {
        return BoardDto.of(makeState());
    }

    private State makeState() throws SQLException {
        final BoardInformationDto informationDto = boardDao.findRecentBoard(connect());
        final List<PieceDto> pieces = pieceDao.findPiecesByBoardId(informationDto.getId(), connect());
        final State state = StateFactory.of(informationDto.getTurn(), pieces);

        return state;
    }
}
