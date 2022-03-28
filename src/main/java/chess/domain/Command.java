package chess.domain;

import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.HashMap;
import java.util.Map;

public class Command {

    private static final int SOURCE_INDEX = 1;
    private static final int TARGET_INDEX = 2;

    private final String command;

    private Command(String command) {
        this.command = command;
    }

    public static Command from(String command) {
        validateCommand(command);
        return new Command(command);
    }

    private static void validateCommand(String command) {
        checkStartOrEnd(command);
    }

    private static void checkStartOrEnd(String command) {
        if (!command.equals("start") && !command.equals("end") && !command.equals("status")) {
            checkMove(command);
        }
    }

    private static void checkMove(String command) {
        if (!command.startsWith("move")) {
            throw new IllegalArgumentException("잘못된 커멘드 입니다.");
        }

        String[] token = command.split(" ");

        if((!isPosition(token[1])) || (!isPosition(token[2]))) {
            throw new IllegalArgumentException("형식이 잘못되었거나 범위를 벗어났습니다.");
        }
    }

    private static boolean isPosition(String token) {
        char first = token.charAt(0);
        char second = token.charAt(1);

        if (File.isFile(first) && Rank.isRank(Character.getNumericValue(second))) {
            return true;
        }
        return false;
    }

    public boolean isEnd() {
        if (command.equals("end")) {
            return true;
        }

        return false;
    }

    public boolean isMoveCommand() {
        if (command.startsWith("move")) {
            return true;
        }

        return false;
    }

    public boolean isStart() {
        if (command.equals("start")) {
            return true;
        }

        return false;
    }

    public boolean isStatus() {
        if (command.equals("status")) {
            return true;
        }

        return false;
    }

    public Map<String, Position> makePositions() {
        checkMoveCommand();

        Map<String, Position> positions = new HashMap<>();

        String[] token = command.split(" ");

        positions.put("source", makePosition(token, SOURCE_INDEX));
        positions.put("target", makePosition(token, TARGET_INDEX));

        return positions;
    }

    private Position makePosition(String[] token, int index) {
        File file = File.toFile(token[index].charAt(0));
        Rank rank = Rank.toRank(token[index].charAt(1));

        return Position.of(file, rank);
    }

    private void checkMoveCommand() {
        if (!isMoveCommand()) {
            throw new IllegalArgumentException("커맨드가 잘못되었습니다. move 커맨드가 와야 합니다.");
        }
    }
}
