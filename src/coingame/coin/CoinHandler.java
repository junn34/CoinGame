package coingame.coin;

import java.util.Random;

public class CoinHandler {
    private Coin[] coins = { new CoinA(), new CoinB(), new CoinC() };
    private Random random = new Random();

    public void simulatePriceChange(int days) {
        for (int day = 1; day <= days; day++) {
            System.out.println("Day " + day);

            for (Coin coin : coins) {
                if (coin.getBeforePrice() == 0) {
                    // 최초 1회만 초기화
                    coin.setBeforePrice(coin.getAfterPrice());
                    coin.setFluctuationRate(0);

//                    System.out.println("  코인: " + coin.getCoinName());
//                    System.out.println("    [초기] 전일가 없음 → 전일가 설정됨");
                } else {
                    int fluctuationPercent = random.nextInt(21) - 10;
                    int newAfterPrice = coin.getBeforePrice()
                        + (coin.getBeforePrice() * fluctuationPercent / 100);
                    coin.setAfterPrice(newAfterPrice);
                    coin.updateFluctuationRate();
                }

                System.out.println(coin);


                // 마지막에 전일가 업데이트
                coin.nextDayUpdate();
            }

            System.out.println(); 
        }
    }

}
