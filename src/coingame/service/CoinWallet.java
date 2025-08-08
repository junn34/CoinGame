package coingame.service;

import java.util.ArrayList;

import coingame.coin.Coin;

public class CoinWallet {
	private ArrayList<Coin> coins = new ArrayList<>();
	
	public void addCoin(Coin coin) { 
		coins.add(coin);
//		System.out.println("당신의 코인 지갑에 " + coin + "을 넣었습니다.");
	}
	
	public void removeCoin(int index) {
//		System.out.println("당신의 코인 지갑에서 " + coins.get(index) + "을 제거 하였습니다.");
		coins.remove(index);
	}
	
	public int getCount() {
		return coins.size();
	}
	
	public void showMyCoins() {
		for (int i = 0; i < coins.size(); i++) {
			System.out.println("======= 현재 보유 중인 코인 목록입니다. ======");
			System.out.println("[" + i + "] " + coins.get(i));	
			System.out.println("현재 보유중인 코인은 총 " + getTotalCoinsPrice() + "원 입니다.");
			System.out.println("=======================================");
		}
	}
	
	public int getTotalCoinsPrice() {
		int totalCoinsPrice = 0;
		for (Coin coin : coins) {
			totalCoinsPrice += coin.getAfterPrice();
		}
		
		return totalCoinsPrice;
	}
}
