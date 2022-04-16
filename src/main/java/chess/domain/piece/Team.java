package chess.domain.piece;

public enum Team {
    NOTHING("nothing"),
    BLACK("black"),
    WHITE("white");

    private final String name;

    Team(String name) {
        this.name = name;
    }
}
