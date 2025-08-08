package coingame.service;

import java.util.HashMap;
import java.util.Map;

import coingame.coin.Coin;

public class CoinWallet {
	
	public HashMap<Coin, Integer> coins = new HashMap<>();
	public HashMap<String, Integer> coinPrices = new HashMap<>();
	
	public void addCoin(Coin coin, int count) {
        int currentCount = coins.getOrDefault(coin, 0);
        coins.put(coin, currentCount + count);
        
        // 코인 가격 정보도 업데이트
        coinPrices.put(coin.getCoinName(), coin.getAfterPrice());
    }
	
	public void removeCoin(String coinName, int count) {
        Coin coinToUpdate = null;
        for (Coin coin : coins.keySet()) {
            if (coin.getCoinName().equals(coinName)) {
                coinToUpdate = coin;
                break;
            }
        }
        
        if (coinToUpdate != null) {
            int currentCount = coins.get(coinToUpdate);
            if (currentCount <= count) {
                // 보유량보다 많이 제거하려면 전부 제거
                coins.remove(coinToUpdate);
                coinPrices.remove(coinName);
            } else {
                // 일부만 제거
                coins.put(coinToUpdate, currentCount - count);
            }
        }
    }
	
	public int getCount() {
		return coins.size();
	}
	
    public void showMyCoins() {
        System.out.println("======= 현재 보유 중인 코인 목록입니다. ======");
        int index = 0;
        for (Map.Entry<Coin, Integer> entry : coins.entrySet()) {
            Coin coin = entry.getKey();
            int quantity = entry.getValue();
            int currentPrice = coinPrices.getOrDefault(coin.getCoinName(), coin.getAfterPrice());
            
            System.out.println("[" + index + "] " + coin.getCoinName() + ": " + quantity + "개 (현재가: " + currentPrice + "원)");
            index++;
        }
        System.out.println("현재 보유중인 코인은 총 " + getTotalCoinsPrice() + "원 입니다.");
        System.out.println("=======================================");
    }

    public int getTotalCoinsPrice() {
        int totalCoinsPrice = 0;
        
        for (Map.Entry<Coin, Integer> entry : coins.entrySet()) {
            Coin coin = entry.getKey();
            int quantity = entry.getValue();
            
            // coinPrices에서 최신 가격을 가져오고, 없으면 coin 객체의 가격 사용
            int currentPrice = coinPrices.getOrDefault(coin.getCoinName(), coin.getAfterPrice());
            
            totalCoinsPrice += currentPrice * quantity;
        }

        return totalCoinsPrice;
    }
}
