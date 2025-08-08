package coingame.player;

import java.util.Map;

import coingame.coin.Coin;
import coingame.service.CoinChart;
import coingame.service.CoinWallet;

public class Player {
    private String name;     // 플레이어 이름
    private int cash;        // 현재 소지금
    private CoinWallet wallet; // 코인 지갑 (추후 구현체 연결)

    // 생성자 - 난이도 기반 시작 자본금 설정
    public Player(String name, int startingCash) {
        this.name = name;
        this.cash = startingCash;
        this.wallet = new CoinWallet(); // 나중에 구현체 연결
    }

    // 이름 Getter
    public String getName() {
        return name;
    }

    // 소지금 Getter
    public int getCash() {
        return cash;
    }

    // 소지금 증가 (알바, 매도 시)
    public void increaseMoney(int amount) {
        cash += amount;
    }

    // 소지금 감소 (매수 시)
    public void decreaseMoney(int amount) {
        cash -= amount;
    }

    public void buyCoin(CoinChart chart, int index, int amount) {
        // 1. 차트에서 코인 정보 가져오기
        Coin selectedCoin = chart.getCoin(index);
        
        if (selectedCoin == null) {
            System.out.println("잘못된 인덱스입니다. 다시 선택해주세요.");
            return;
        }

        // 2. 구매 비용 계산
        int totalCost = selectedCoin.getAfterPrice() * amount;
        
        // 3. 돈이 충분한지 확인
        if (cash < totalCost) {
            System.out.println("돈이 부족합니다!");
            System.out.println("필요한 금액: " + totalCost + "원, 보유 금액: " + money + "원");
            return;
        }

        // 4. 구매 진행
        cash -= totalCost;  // 돈 차감
        wallet.addCoin(selectedCoin, amount);  // 지갑에 코인 추가
        
        System.out.println("=== 구매 완료 ===");
        System.out.println(selectedCoin.getCoinName() + " " + amount + "개를 구매했습니다!");
        System.out.println("구매가격: " + totalCost + "원");
        System.out.println("남은 돈: " + cash + "원");
        System.out.println("===============");
    }


 // 코인 판매 메서드
    public void sellCoin(String coinName, int amount) {
        // 1. 해당 코인을 보유하고 있는지 확인
        Coin coinToSell = null;
        int currentAmount = 0;
        
        for (Map.Entry<Coin, Integer> entry : wallet.coins.entrySet()) {
            if (entry.getKey().getCoinName().equals(coinName)) {
                coinToSell = entry.getKey();
                currentAmount = entry.getValue();
                break;
            }
        }
        
        // 2. 보유하지 않은 코인인 경우
        if (coinToSell == null) {
            System.out.println(coinName + "을(를) 보유하고 있지 않습니다.");
            return;
        }
        
        // 3. 판매하려는 수량이 보유량보다 많은 경우
        if (amount > currentAmount) {
            System.out.println("보유량이 부족합니다!");
            System.out.println("보유량: " + currentAmount + "개, 판매 시도량: " + amount + "개");
            return;
        }
        
        // 4. 판매 가격 계산 (현재 가격으로 판매)
        int currentPrice = wallet.coinPrices.getOrDefault(coinName, coinToSell.getAfterPrice());
        int sellAmount = currentPrice * amount;
        
        // 5. 판매 진행
        wallet.removeCoin(coinName, amount);  // 지갑에서 코인 제거
        cash += sellAmount;  // 돈 추가
        
        System.out.println("=== 판매 완료 ===");
        System.out.println(coinName + " " + amount + "개를 판매했습니다!");
        System.out.println("판매가격: " + sellAmount + "원");
        System.out.println("현재 돈: " + cash + "원");
        System.out.println("===============");
    }

    // 플레이어 상태 출력
    public void showStatus() {
        System.out.println("=== " + name + "의 현재 상태 ===");
        System.out.println("보유 현금: " + cash + "원");
        System.out.println("총 자산가치: " + (cash + wallet.getTotalCoinsPrice()) + "원");
        wallet.showMyCoins();
    }
}
