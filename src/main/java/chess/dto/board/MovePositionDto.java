package chess.dto.board;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovePositionDto {
    private final PositionDto source;
    private final PositionDto target;

    public MovePositionDto(PositionDto source, PositionDto target) {
        this.source = source;
        this.target = target;
    }

    public static MovePositionDto of (String input) {
        final List<String> positions = Arrays.stream(input.split(" "))
                .collect(Collectors.toList());

        final String source = positions.get(0);
        final String target = positions.get(1);

        return new MovePositionDto(makePositionDto(source), makePositionDto(target));
    }

    private static PositionDto makePositionDto(String position) {
        final int file = position.charAt(0);
        final int rank = position.charAt(1);

        return new PositionDto(Character.getNumericValue(file), Character.getNumericValue(rank));
    }

    public PositionDto getSource() {
        return source;
    }

    public PositionDto getTarget() {
        return target;
    }
}
