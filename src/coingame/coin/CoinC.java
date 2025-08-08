package coingame.coin;

public class CoinC extends Coin {

    public CoinC(String coinName, int beforePrice, int afterPrice) {
		super(coinName, beforePrice, afterPrice);
		// TODO Auto-generated constructor stub
	}

	private String coinName = "도지코인";
    private int afterPrice = 300;
    private int beforePrice = 0;
    private int fluctuationRate;

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
