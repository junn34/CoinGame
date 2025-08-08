package coingame.coin;



public class Coin implements CoinCalculate{
    protected String coinName;
    protected int afterPrice;
    protected int beforePrice;
    protected int fluctuationRate;

   
    public Coin(String coinName, int beforePrice, int afterPrice) {
        this.coinName = coinName;
        this.beforePrice = beforePrice;
        this.afterPrice = afterPrice;
        this.fluctuationRate = calculate();
    }
    public int calculate() {
        if (beforePrice == 0) return 0; 
        return (afterPrice - beforePrice) * 100 / beforePrice;
    }
   

    
}

