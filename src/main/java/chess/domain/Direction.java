package chess.domain;

import chess.domain.postion.Position;

import java.util.List;

public enum Direction {
    TOP(0, 1),
    RIGHT(1, 0),
    LEFT(-1, 0),
    BOTTOM(0, -1),

    TOP_RIGHT(1, 1),
    TOP_LEFT(-1, 1),
    BOTTOM_RIGHT(1, -1),
    BOTTOM_LEFT(-1, -1),

    TTR(1, 2),
    TTL(-1, 2),
    BBR(1, -2),
    BBL(-1, -2),
    RRT(2, 1),
    RRB(2, -1),
    LLT(-2, 1),
    LLB(-2, -1);

    private final int file;
    private final int rank;

    Direction(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public static List<Direction> getRookDirection() {
        return List.of(TOP, RIGHT, LEFT, BOTTOM);
    }

    public static List<Direction> getBishopDirection() {
        return null;
    }

    public static List<Direction> getQueenDirection() {
        return null;
    }

    public static List<Direction> getKingDirection() {
        return null;
    }

    public static List<Direction> getKnightDirection() {
        return null;
    }

    public static List<Direction> getWhitePawnDirection() {
        return List.of(TOP);
    }

    public static List<Direction> getBlackPawnDirection() {
        return List.of(BOTTOM);
    }

    public static Direction beMoveDirection(List<Direction> directions, Position source, Position target) {
        return directions.stream()
                .filter(direction -> Direction.isRightDirection(direction, source, target))
                .findFirst()
                .orElse(null);
    }

    public static boolean isRightDirection(Direction direction, Position source, Position target) {
        Position currentPosition = source;

        for (int i = 0; i < 8; i++) {
            currentPosition = currentPosition.from(direction);
            if (currentPosition.equals(target)) {
                return true;
            }
        }

        return false;
    }

    public int file() {
        return file;
    }

    public int rank() {
        return rank;
    }
}