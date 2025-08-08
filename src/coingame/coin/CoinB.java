package coingame.coin;

public class CoinB extends Coin {

   

	private String coinName = "이더리움";
    private int afterPrice = 5_400_000;
    private int beforePrice = 0;
    private int fluctuationRate;
    public CoinB(String coinName, int beforePrice, int afterPrice) {
		super(coinName, beforePrice, afterPrice);
		// TODO Auto-generated constructor stub
	}
    @Override
    public int calculate() {
        if (beforePrice == 0) return 0; 
        return (afterPrice - beforePrice) * 100 / beforePrice;
    }

    
    public void updateFluctuationRate() {
        this.fluctuationRate = calculate();
    }

    
    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public int getAfterPrice() {
        return afterPrice;
    }

    public void setAfterPrice(int afterPrice) {
        this.afterPrice = afterPrice;
    }

    public int getBeforePrice() {
        return beforePrice;
    }

    public void setBeforePrice(int beforePrice) {
        this.beforePrice = beforePrice;
    }

    public int getFluctuationRate() {
        return fluctuationRate;
    }

    public void setFluctuationRate(int fluctuationRate) {
        this.fluctuationRate = fluctuationRate;
    }
}
