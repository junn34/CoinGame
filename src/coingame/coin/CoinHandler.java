package coingame.coin;

import java.util.Random;

public class CoinHandler  {
	Random random = new Random();
	int randomInt = random.nextInt(100);
	Coin coin;
	if(randomInt%6==0) {
		coin=new CoinA;
	}
	
}
