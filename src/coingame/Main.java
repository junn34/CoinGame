package coingame;

import coingame.coin.CoinHandler;
import coingame.controller.Controller;

public class Main {

	public static void main(String[] args) {
		CoinHandler coinHandler = new CoinHandler();  // CoinHandler 객체 생성
        Controller controller = new Controller(coinHandler);
        controller.startGame();
	}

}
