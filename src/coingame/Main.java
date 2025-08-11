package coingame;

import java.util.ArrayList;
import java.util.List;

import coingame.coin.Coin;
import coingame.coin.CoinHandler;
import coingame.controller.Controller;

public class Main {

	public static void main(String[] args) {
		List<Coin> coins=new ArrayList<Coin>();
		CoinHandler coinHandler = new CoinHandler(coins);  // CoinHandler 객체 생성
        Controller controller = new Controller();
        controller.startGame();
	}

}
