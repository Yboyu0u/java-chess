package chess.domain.game.state;

import chess.domain.board.Board;
import chess.domain.board.position.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.team.Color;

import java.util.Set;

public class BlackTurn extends Running {
    public static final String BLACK_TURN = "black turn";

    public BlackTurn(Board board) {
        super(board);
    }

    @Override
    public State passTurn() {
        return new WhiteTurn(board());
    }

    @Override
    public State end() {
        if (!isRunning()) {
            return new BlackWin(board());
        }
        return new Draw(board());
    }

    @Override
    public void moveIfValidColor(Position source, Position target) {
        Piece sourcePiece = board().pieceOfPosition(source);
        if (sourcePiece.isWhite()) {
            throw new UnsupportedOperationException(
                    Color.BLACK.name() + "턴엔 " + Color.BLACK.name() + "말만 이동 가능합니다."
            );
        }
        board().moveIfValidPosition(source, target);
    }

    @Override
    public Set<Position> movablePath(Position source) {
        Piece sourcePiece = board().pieceOfPosition(source);
        if (sourcePiece.isWhite()) {
            throw new UnsupportedOperationException(
                    Color.BLACK.name() + "턴엔 " + Color.BLACK.name() + "말만 이동 가능합니다."
            );
        }
        return board().movablePath(source);
    }

    @Override
    public String toString() {
        return BLACK_TURN;
    }
}