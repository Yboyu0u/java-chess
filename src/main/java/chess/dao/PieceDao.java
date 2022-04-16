package chess.dao;

import chess.dto.board.MovePositionDto;
import chess.dto.board.PieceDto;
import chess.dto.board.PositionDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public void save(final int file, final int rank, final String symbol, final int boardId,
                     final Connection connection) throws SQLException {
        final String sql = "insert into piece (`file`, `rank`, `symbol`, `board_id`) values (?, ?, ?, ?)";
        final PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, file);
        statement.setInt(2, rank);
        statement.setString(3, symbol);
        statement.setInt(4, boardId);
        statement.executeUpdate();
    }

    public List<PieceDto> findPiecesByBoardId(final int boardId, final Connection connection) throws SQLException {
        final String sql = "select * from piece where board_id = ?";

        final PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, boardId);

        final ResultSet resultSet = statement.executeQuery();
        final List<PieceDto> pieces = new ArrayList<>();

        while (resultSet.next()) {
            final int file = resultSet.getInt("file");
            final int rank = resultSet.getInt("rank");
            final String symbol = resultSet.getString("symbol");

            PieceDto pieceDto = new PieceDto(symbol, file, rank);
            pieces.add(pieceDto);
        }
        return pieces;
    }

    public void updatePositionByBoardId(final MovePositionDto movePositionDto, final int boardId,
                                        final Connection connection)
            throws SQLException {
        final String sql = "update piece set `file`= ?, `rank` = ?  where `file`= ? and `rank` = ? and `board_id` = ?";

        final PositionDto source = movePositionDto.getSource();
        final PositionDto target = movePositionDto.getTarget();

        final int sourceFile = source.getFile();
        final int sourceRank = source.getRank();
        final int targetFile = target.getFile();
        final int targetRank = target.getRank();

        final PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, targetFile);
        statement.setInt(2, targetRank);
        statement.setInt(3, sourceFile);
        statement.setInt(4, sourceRank);
        statement.setInt(5, boardId);
        statement.executeUpdate();
    }

    public void deleteById(int boardId, Connection connection) throws SQLException {
        final String sql = "delete from piece where board_id = ?";

        final PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, boardId);
        statement.executeUpdate();
    }

    public void deleteByIdAndPosition(final int boardId, final PositionDto source, final Connection connection)
            throws SQLException {
        final String sql = "delete from piece where board_id = ? and `file` = ? and `rank` = ?";

        final PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, boardId);
        statement.setInt(2, source.getFile());
        statement.setInt(3, source.getRank());
        statement.executeUpdate();
    }
}
