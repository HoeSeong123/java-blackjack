import blackjack.domain.player.BettingAmounts;
import blackjack.domain.Blackjack;
import blackjack.domain.card.Card;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.GamePlayer;
import blackjack.domain.player.Names;
import blackjack.domain.player.Players;
import blackjack.domain.result.Result;
import blackjack.view.BlackjackCommand;
import blackjack.view.InputView;
import blackjack.view.PlayerView;
import blackjack.view.ResultView;

public class BlackjackController {
    private final Blackjack blackjack;

    public BlackjackController(Blackjack blackjack) {
        this.blackjack = blackjack;
    }

    public void playBlackJack() {
        Players players = joinPlayer();
        processGame(players);
        printGameResult(players);
    }

    private Players joinPlayer() {
        Names names = Names.from(InputView.inputPlayerNames());
        BettingAmounts battingAmounts = BettingAmounts.from(
                InputView.inputPlayerBattingAmounts(names));
        Players players = blackjack.acceptPlayers(names, battingAmounts);
        PlayerView.printPlayers(players);
        return players;
    }

    private void processGame(Players players) {
        players.getGamePlayers()
               .forEach(gamePlayer -> processGamePlayer(blackjack, gamePlayer));
        processDealer(blackjack, players.getDealer());
        PlayerView.printPlayersWithScore(players);
    }

    private void printGameResult(Players players) {
        Result result = blackjack.compareResults(players.getDealer(), players.getGamePlayers());
        ResultView.printResult(result);
    }

    private void processGamePlayer(Blackjack blackjack, GamePlayer gamePlayer) {
        while (gamePlayer.isReceivable() && isHit(gamePlayer)) {
            Card card = blackjack.draw();
            gamePlayer.drawCard(card);
            PlayerView.printPlayerOpenCards(gamePlayer);
        }
    }

    private boolean isHit(GamePlayer gamePlayer) {
        BlackjackCommand command = InputView.inputBlackjackCommand(gamePlayer.getName());
        return command.isHit();
    }

    private void processDealer(Blackjack blackjack, Dealer dealer) {
        while (dealer.isReceivable()) {
            PlayerView.printDealerDrawMessage();
            Card card = blackjack.draw();
            dealer.drawCard(card);
        }
        PlayerView.printDealerNotDrawMessage();
    }
}
