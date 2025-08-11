package coingame.coin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CoinHandlerTest {

    @Test
    public void testCoinHandler() {
        List<Coin> coins = new ArrayList<>();
        coins.add(new CoinA());
        coins.add(new CoinB());
        coins.add(new CoinC());

        CoinHandler handler = new CoinHandler(coins);
        String message="handler is not found";
        assertNotNull(handler,message);
    }

    @Test
    void testSimulateToday() {
        // given
        List<Coin> coins = new ArrayList<>();
        coins.add(new CoinA());
        coins.add(new CoinB());
        coins.add(new CoinC());
        CoinHandler handler = new CoinHandler(coins);

        int[] base = coins.stream().mapToInt(Coin::getAfterPrice).toArray();

        // when
        handler.simulateToday();
        
        // then
        for (int i = 0; i < coins.size(); i++) {
            Coin c = coins.get(i);
            int before = c.getBeforePrice();
            int after  = c.getAfterPrice();

            assertEquals(base[i], before);

            int lower = Math.max(1, (int)Math.round(before * 0.9));
            int upper = (int)Math.round(before * 1.1);
            assertTrue(after >= lower && after <= upper, "10% 오차 이외");
        }
    }

    @Test
    void testCloseDay() {
        List<Coin> coins = new ArrayList<>();
        coins.add(new CoinA());
        coins.add(new CoinB());
        coins.add(new CoinC());
        CoinHandler handler = new CoinHandler(coins);

        handler.simulateToday();
        int[] afterToday = coins.stream().mapToInt(Coin::getAfterPrice).toArray();

        handler.closeDay();

        for (int i = 0; i < coins.size(); i++) {
            assertEquals(afterToday[i], coins.get(i).getBeforePrice());
        }
    }

}