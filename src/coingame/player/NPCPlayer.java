package coingame.player;

import java.util.*;

public class NPCPlayer extends Player {
    private Map<String, Integer> actionRatio; // 행동 비율

    public NPCPlayer(String name, int startingCash, Map<String, Integer> actionRatio) {
        super(name, startingCash);
        this.actionRatio = actionRatio;
    }

    // NPC 하루 행동 (비율 기반)
    public void randomAction(Map<String, Integer> coinPrices) {
        int rand = new Random().nextInt(100) + 1; // 1~100
        int sum = 0;
        String chosenAction = "";

        for (Map.Entry<String, Integer> entry : actionRatio.entrySet()) {
            sum += entry.getValue();
            if (rand <= sum) {
                chosenAction = entry.getKey();
                break;
            }
        }

        String coin = getRandomCoin(coinPrices);
        int amount = new Random().nextInt(3) + 1; // 1~3개

        switch (chosenAction) {
            case "buy" -> buyCoin(coin, amount, coinPrices.get(coin));
            case "sell" -> sellCoin(coin, amount, coinPrices.get(coin));
            case "wait" -> System.out.println(getName() + "는 대기 중");
        }
    }

    private String getRandomCoin(Map<String, Integer> coinPrices) {
        List<String> coins = new ArrayList<>(coinPrices.keySet());
        return coins.get(new Random().nextInt(coins.size()));
    }
}
