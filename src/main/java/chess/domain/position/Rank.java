package chess.domain.position;

import java.util.Arrays;

public enum Rank {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    private final int rank;

    Rank(int rank) {
        this.rank = rank;
    }

    public static boolean isRank(int candidate) {
        return Arrays.stream(Rank.values())
                .filter(rank -> rank.getRank() == candidate)
                .findAny()
                .isPresent();
    }

    public int calculateRank(Rank other) {
        return rank - other.getRank();
    }

    public int getRank() {
        return rank;
    }

    public int calculateAbsoluteValue(Rank other) {
        return Math.abs(rank - other.getRank());
    }
}
