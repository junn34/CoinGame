package coingame.coin;

import java.util.List;
import java.util.Random;

public class CoinHandler {
    private final List<Coin> coins;
    private final Random random = new Random();

    public CoinHandler(List<Coin> coins) {
        this.coins = coins;
    }

    public void simulateToday() {
        for (Coin coin : coins) {
            if (coin.getBeforePrice() == 0) {
                coin.setBeforePrice(coin.getAfterPrice());
                coin.setFluctuationRate(0);
            }
            int pct = random.nextInt(21) - 10;
            int base = coin.getBeforePrice();
            int delta = (int) Math.round(base * (pct / 100.0));
            int newAfter = Math.max(1, base + delta);
            coin.setAfterPrice(newAfter);
            coin.updateFluctuationRate();
        }
    }

    public void closeDay() {
        for (Coin coin : coins) {
            coin.nextDayUpdate();
        }
    }

    public void advanceDays(int days) {
        for (int i = 0; i < days; i++) {
            simulateToday();
            closeDay();
        }
    }
}
