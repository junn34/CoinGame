package coingame.player;

import java.util.Random;
import java.util.Map;
import coingame.coin.Coin;
import coingame.service.CoinChart;

public class NPCPlayer extends Player {
    private Random random = new Random();

    public NPCPlayer(String name, int startingCash) {
        super(name, startingCash);
    }

    public void takeAction(CoinChart chart) {
        int action = random.nextInt(3); // 0: 매수, 1: 매도, 2: 스킵

        switch (action) {
            case 0 -> buyRandomCoin(chart); // 매수
            case 1 -> sellRandomCoin();     // 매도
            case 2 -> System.out.println(getName() + "은(는) 아무것도 하지 않았습니다."); // 스킵
        }
    }

    private void buyRandomCoin(CoinChart chart) {

        int coinIndex = random.nextInt(3);
        int amount = random.nextInt(3) + 1;

        Coin coin = chart.getCoin(coinIndex);
        int totalCost = coin.getAfterPrice() * amount;

        if (getCash() < totalCost) {
            System.out.println(getName() + "은(는) 돈이 부족해 " + coin.getCoinName() + " 구매를 포기했습니다.");
            return;
        }

        buyCoin(chart, coinIndex, amount);
    }

    private void sellRandomCoin() {
        if (getWallet().coins.isEmpty()) {
            System.out.println(getName() + "은(는) 팔 코인이 없습니다.");
            return;
        }

        // 랜덤 코인 선택
        int index = random.nextInt(getWallet().coins.size());
        Coin coinToSell = (Coin) getWallet().coins.keySet().toArray()[index];
        int maxAmount = getWallet().coins.get(coinToSell);

        if (maxAmount <= 0) {
            System.out.println(getName() + "은(는) 팔 수량이 없습니다.");
            return;
        }

        int amount = random.nextInt(maxAmount) + 1; // 최소 1개 이상
        sellCoin(coinToSell.getCoinName(), amount);
    }
}
