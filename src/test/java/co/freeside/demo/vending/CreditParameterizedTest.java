package co.freeside.demo.vending;

import java.util.*;
import co.freeside.demo.vending.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class CreditParameterizedTest {
    
    public CreditParameterizedTest(Coin... coins) {
        this.coins = coins;
    }

    private VendingMachine machine = new VendingMachine();
    private Coin[] coins;

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Coin[][][] {
            new Coin[][] { { Coin.Penny } },
            new Coin[][] { { Coin.Nickel } },
            new Coin[][] { { Coin.Dime, Coin.Quarter } }
        };
        return Arrays.asList(data);
    }

    @Test
    public void insertingCoinsIncrementsCredit() {
        int expected = 0;

        for (Coin coin : coins) {
            machine.insertCoin(coin);
            expected += coin.getValue();
        }

        assertThat(machine.readCredit(), equalTo(expected));
    }
}
