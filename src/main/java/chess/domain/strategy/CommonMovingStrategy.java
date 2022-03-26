package chess.domain.strategy;

import chess.domain.ChessBoard;
import chess.domain.piece.Team;
import chess.domain.position.Direction;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonMovingStrategy implements MoveStrategy {

    protected void checkCommonCondition(Position source, Position target, ChessBoard chessBoard) {
        checkStay(source, target);
        checkTargetPosition(source, target, chessBoard);
        checkPath(source, target, chessBoard);
    }

    private void checkStay(Position source, Position target) {
        if (source.isSameFile(target) && source.isSameRank(target)) {
            throw new IllegalStateException("제자리에 머무를 수 없습니다.");
        }
    }

    private void checkPath(Position source, Position target, ChessBoard chessBoard) {
        Direction direction = Direction.of(source, target);

        Position currentPosition = source;

        List<Position> paths = new ArrayList<>();

        while (!currentPosition.equals(target)) {
            currentPosition = currentPosition.toDirection(direction);

            paths.add(currentPosition);
        }

        paths.remove(target);

        if (chessBoard.isContainPiece(paths)) {
            throw new IllegalStateException("경로상에 말이 존재합니다.");
        }
    }

    private void checkTargetPosition(Position source, Position target, ChessBoard chessBoard) {
        Team sourceTeam = chessBoard.findTeam(source);
        Team targetTeam = chessBoard.findTeam(target);

        if (sourceTeam == targetTeam) {
            throw new IllegalStateException("경로상에 말이 존재합니다.");
        }
    }

}
