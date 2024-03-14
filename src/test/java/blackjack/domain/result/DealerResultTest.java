package blackjack.domain.result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import blackjack.domain.Profit;
import blackjack.domain.player.Name;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DealerResultTest {
    @Test
    @DisplayName("딜러 이름과 게임 플레이어 결과 리스트를 통해 딜러 결과를 생성한다.")
    void DealerResult_Instance_create_with_gamePlayerResultList() {
        Name name = new Name("초롱");
        ResultStatus resultStatus = ResultStatus.DRAW;
        Profit profit = new Profit(0);
        GamePlayerResult gamePlayerResult = new GamePlayerResult(name, profit);
        
        assertThatCode(() -> {
            var sut = DealerResult.of(new Name("딜러"), List.of(gamePlayerResult));
            assertThat(sut.getProfit()).isEqualTo(new Profit(0));
        }).doesNotThrowAnyException();
    }
}
