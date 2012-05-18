package co.freeside.demo.vending;

import org.junit.experimental.theories.*;
import org.junit.runner.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

@RunWith(Theories.class)
public class CreditTheory {

    private VendingMachine machine = new VendingMachine(null);

    @DataPoints
    public static Coin[][] data() {
        return new Coin[][] {
            { Coin.Penny },
            { Coin.Nickel },
            { Coin.Dime, Coin.Quarter }
        };
    }

    @Theory
    public void insertingCoinsIncrementsCredit(Coin[] coins) {
        int expected = 0;

        for (Coin coin : coins) {
            System.out.printf("Inserting %s%n", coin);
            machine.insertCoin(coin);
            expected += coin.getValue();
        }

        assertThat(machine.readCredit(), equalTo(expected));
    }

}
