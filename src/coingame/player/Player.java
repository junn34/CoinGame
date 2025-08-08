package coingame.player;

import java.util.Map;

import coingame.service.CoinWallet;

public class Player {
    private String name;     // 플레이어 이름
    private int cash;        // 현재 소지금
    private CoinWallet wallet; // 코인 지갑 (추후 구현체 연결)

    // 생성자 - 난이도 기반 시작 자본금 설정
    public Player(String name, int startingCash) {
        this.name = name;
        this.cash = startingCash;
        this.wallet = new CoinWallet(this); // 나중에 구현체 연결
    }

    // 이름 Getter
    public String getName() {
        return name;
    }

    // 소지금 Getter
    public int getCash() {
        return cash;
    }

    // 소지금 증가 (알바, 매도 시)
    public void increaseMoney(int amount) {
        cash += amount;
    }

    // 소지금 감소 (매수 시)
    public void decreaseMoney(int amount) {
        cash -= amount;
    }

    // 코인 매수
    public void buyCoin(String coinName, int amount, int pricePerCoin) {
        int totalCost = amount * pricePerCoin;
        if (cash >= totalCost) {
            decreaseMoney(totalCost);
            wallet.addCoin(coinName, amount); // CoinWallet 메서드
            System.out.println(coinName + " 코인 " + amount + "개 매수 완료");
        } else {
            System.out.println("잔액이 부족합니다.");
        }
    }

    // 코인 매도
    public void sellCoin(String coinName, int amount, int pricePerCoin) {
        if (wallet.getCoinAmount(coinName) >= amount) {
            wallet.removeCoin(coinName, amount); // CoinWallet 메서드
            increaseMoney(amount * pricePerCoin);
            System.out.println(coinName + " 코인 " + amount + "개 매도 완료");
        } else {
            System.out.println("보유량이 부족합니다.");
        }
    }

    // 현재 총 자산 계산 (소지금 + 보유 코인 가치)
    public int getTotalAssets(Map<String, Integer> coinPrices) {
        int total = cash;
        for (Map.Entry<String, Integer> entry : wallet.getAllCoins().entrySet()) {
            String coinName = entry.getKey();
            int amount = entry.getValue();
            int price = coinPrices.getOrDefault(coinName, 0);
            total += amount * price;
        }
        return total;
    }
}
