package coingame.coin;

public abstract class Coin {
    protected String coinName;
    protected int beforePrice;
    protected int afterPrice;
    protected int fluctuationRate;

    public Coin(String coinName, int beforePrice, int afterPrice) {
        this.coinName = coinName;
        this.beforePrice = beforePrice;
        this.afterPrice = afterPrice;
    }

    public void setBeforePrice(int beforePrice) {
        this.beforePrice = beforePrice;
    }
    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public int calculate() {
        if (beforePrice == 0) return 0;
        return (afterPrice - beforePrice) * 100 / beforePrice;
    }

    public void updateFluctuationRate() {
        this.fluctuationRate = calculate();
    }

    public void nextDayUpdate() {
        this.beforePrice = this.afterPrice;
    }

    
    public void setFluctuationRate(int fluctuationRate) {
		this.fluctuationRate = fluctuationRate;
	}

	public String getCoinName() { return coinName; }
    public int getBeforePrice() { return beforePrice; }
    public int getAfterPrice() { return afterPrice; }
    public int getFluctuationRate() { return fluctuationRate; }

    public void setAfterPrice(int afterPrice) {
        this.afterPrice = afterPrice;
    }
    
    @Override
    public String toString() {
        return "[ " + coinName+" ]\n"+
               "  전일가: " + beforePrice + "\n" +
               "  현재가: " + afterPrice + "\n" +
               "  변동률: " + fluctuationRate + "%";
    }
}
