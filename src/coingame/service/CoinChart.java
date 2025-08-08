package coingame.service;

import java.util.ArrayList;

public class CoinChart {
	private ArrayList<Coin> coins = new ArrayList<>();
	
	public void showCoinList() {
		"============구매할 수 있는 코인 목록입니다.========="
		for (int i = 0; i < coins.length; i++) {
			System.out.println("["+ i + "]" + coin);
		}
		
		"============================================="
	}
}
