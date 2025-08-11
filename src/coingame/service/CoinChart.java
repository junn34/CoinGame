package coingame.service;

import java.util.ArrayList;

import coingame.coin.Coin;

// 1. 코인 차트에 있는 코인의 인덱스를 입력함
// 2. buyCoin(int index, int amount)
// 3. Wallet에 index에 있는 코인을 넣어줌. 


public class CoinChart {	
	private ArrayList<Coin> coins = new ArrayList<>();
    
	// 코인 추가 메서드
    public void addCoin(Coin coin) {
        coins.add(coin);
    }
    
    // 인덱스로 코인 가져오기 (Player가 구매할 때 사용)
    public Coin getCoin(int index) {
        if (index >= 0 && index < coins.size()) {
            return coins.get(index);
        }
        return null;
    }
    
    
	public void showCoinList() {
		System.out.println("============구매할 수 있는 코인 목록입니다.=========");
	
		for (int i = 0; i < coins.size(); i++) {
			System.out.println("["+ i + "]" + coins.get(i));
		}
		
		System.out.println("=============================================");	
	}
}
