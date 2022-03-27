package chess.domain;

import chess.domain.state.Black;
import chess.domain.state.White;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlackTest {

    @DisplayName("Black 상태에서 턴이 바뀌면 White 상태로 되는 지 테스트")
    @Test
    void changeTurn() {
        Black black = new Black();

        assertThat(black.changeTurn()).isInstanceOf(White.class);
    }
}