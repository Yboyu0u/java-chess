package chess.domain.piece;

import chess.domain.Position;

import java.util.List;
import java.util.Map;

public class Pawn extends Piece {
    private static final int UNICODE_DECIMAL = 9817;

    private final int direction;

    public Pawn(final int direction) {
        this.direction = direction;
    }

    @Override
    public boolean isMovable(Position current, Position destination, Map<Position, Piece> chessBoard) {
        if (!checkPositionRule(current, destination)) {
            return false;
        }
        if (current.checkDiagonalRule(destination)) {
            return chessBoard.containsKey(destination);
        }
        final List<Position> straightPath = current.generateStraightPath(destination);
        return checkEmptyPath(straightPath, chessBoard) && !chessBoard.containsKey(destination);
    }

    @Override
    public boolean checkPositionRule(final Position current, final Position destination) {
        if (isMoved) {
            return checkPositionRuleAfterMove(current, destination);
        }
        return checkPositionRuleFirstMove(current, destination);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    private boolean checkPositionRuleAfterMove(final Position current, final Position destination) {
        if (current.moveXandY(0, direction).equals(destination)) {
            return true;
        }
        return current.checkDiagonalToDirection(destination, direction);
    }

    private boolean checkPositionRuleFirstMove(final Position current, final Position destination) {
        if (current.moveXandY(0, direction).equals(destination) ||
                current.moveXandY(0, direction * 2).equals(destination)) {
            return true;
        }
        return current.checkDiagonalToDirection(destination, direction);
    }

    @Override
    public int hashCode() {
        return UNICODE_DECIMAL * direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        return getClass() == obj.getClass();
    }
}
