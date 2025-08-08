package coingame.service;

import java.util.ArrayList;

import coingame.coin.Coin;

public class CoinChart {
	private ArrayList<Coin> coins = new ArrayList<>();
	
	public void showCoinList() {
		System.out.println("============구매할 수 있는 코인 목록입니다.=========");
	
		for (int i = 0; i < coins.size(); i++) {
			System.out.println("["+ i + "]" + coins.get(i));
		}
		
		System.out.println("=============================================");	
	}
}
