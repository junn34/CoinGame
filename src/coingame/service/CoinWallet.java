package coingame.service;

import java.util.ArrayList;

public class CoinWallet {
	private ArrayList<Coin> coins = new ArrayList<>();
	private Player player;
	
	public CoinWallet(Player player) {
		this.player = player;
	}
	
	public void addCoin(Coin coin) { 
		player.money -= coin.price;
	}
	
	public void removeCoin(int index) {
		coins.remove(index);
	}
	
	public int getCount() {
		return coins.length;
	}
	
	public void showMyCoins() {
		for (int i = 0; i < coins.length; i++) {
			System.out.println("======= 현재 보유 중인 코인 목록입니다. ======");
			
			System.out.println("[" + i + "] " + coins.get(i));	
			System.out.println("현재 보유중인 코인은 총 " + getTotalCoinsPrice() + "입니다.");
			System.out.println("=======================================");
		}
	}
	
	public int getTotalCoinsPrice() {
		int totalCoinsPrice = 0;
		for (Coin coin : coins) {
			totalCoinsPrice += coin.price;
		}
		
		return totalCoinsPrice;
	}
}
