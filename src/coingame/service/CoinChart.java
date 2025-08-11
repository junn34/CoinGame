package coingame.service;

import java.util.List;
import coingame.coin.Coin;

public class CoinChart {	
    private final List<Coin> coins;   // 새로 만들지 말고 외부 것을 참조

    public CoinChart(List<Coin> coins) {
        this.coins = coins;
    }

    public Coin getCoin(int index) {
        if (index >= 0 && index < coins.size()) return coins.get(index);
        return null;
    }

    public void showCoinList() {
        System.out.println("============구매할 수 있는 코인 목록입니다.=========");
        for (int i = 0; i < coins.size(); i++) {
            System.out.println("[" + i + "] " + coins.get(i));
        }
        System.out.println("=============================================");
    }
}
