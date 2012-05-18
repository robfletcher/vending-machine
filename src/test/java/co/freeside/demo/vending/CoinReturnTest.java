package co.freeside.demo.vending;

import java.util.*;
import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class CoinReturnTest {

    private VendingMachine machine = new VendingMachine();

    @Test
    public void machineReturnsNoCoinsIfNoneAreInserted() {
        assertThat(machine.returnCoins().size(), equalTo(0));
    }

    @Test
    public void machineReturnsCoinsToSameValueInserted() {
        machine.insertCoin(Coin.Penny);
        machine.insertCoin(Coin.Dime);

        Collection<Coin> coins = machine.returnCoins();

        int returnedValue = 0;
        for (Coin coin : coins) returnedValue += coin.getValue();
        assertThat(returnedValue, equalTo(11));
    }

}
