package coingame.coin;

import java.util.List;
import java.util.Random;

public class CoinHandler {
    private final List<Coin> coins;
    private final Random random = new Random();

    public CoinHandler(List<Coin> coins) { this.coins = coins; }

    // 오늘 변동만 적용
    public void simulateToday() {
        for (Coin coin : coins) {
            if (coin.getBeforePrice() == 0) {
                // 1일차 세팅
                coin.setBeforePrice(coin.getAfterPrice());
                coin.setFluctuationRate(0);
                
            } else {
                int pct = random.nextInt(21) - 10; // -10~10%
                int newAfter = coin.getBeforePrice()
                        + (coin.getBeforePrice() * pct / 100);
                coin.setAfterPrice(newAfter);
                coin.updateFluctuationRate(); // (after - before) / before
            }
        }
    }

    // 라운드 종료 시 호출
    public void closeDay() {
        for (Coin coin : coins) {
            coin.nextDayUpdate();
        }
    }
}

