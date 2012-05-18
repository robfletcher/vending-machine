package co.freeside.demo.vending;

import java.util.*;
import org.jmock.*;
import org.junit.*;
import static co.freeside.demo.vending.Coin.*;

public class CoinReturnTest {

	private Mockery context = new Mockery();

    @Test
    public void machineReturnsNoCoinsIfNoneAreInserted() {
		final CoinDispenser coinDispenser = context.mock(CoinDispenser.class);
		VendingMachine machine = new VendingMachine(coinDispenser);

		context.checking(new Expectations() {{
			never(coinDispenser).dispense(with(any(Coin.class)));
		}});

		machine.returnCoins();

		context.assertIsSatisfied();
	}

    @Test
    public void machineReturnsCoin() {
		final CoinDispenser coinDispenser = context.mock(CoinDispenser.class);
		VendingMachine machine = new VendingMachine(coinDispenser);

		context.checking(new Expectations() {{
			oneOf(coinDispenser).dispense(Penny);
		}});

		machine.insertCoin(Penny);
        machine.returnCoins();

		context.assertIsSatisfied();
    }

    @Test
    public void machineReturnsEfficientChange() {
		final CoinDispenser coinDispenser = context.mock(CoinDispenser.class);
		VendingMachine machine = new VendingMachine(coinDispenser);
		machine.loadChange(Arrays.asList(Quarter, Dime, Nickel));

		context.checking(new Expectations() {{
			oneOf(coinDispenser).dispense(Dime);
			oneOf(coinDispenser).dispense(Nickel);
			exactly(2).of(coinDispenser).dispense(Penny);
		}});

		for (int i = 0; i < 17; i++) machine.insertCoin(Penny);
        machine.returnCoins();

		context.assertIsSatisfied();
    }

    @Test
    public void machineReturnsAvailableChange() {
		final CoinDispenser coinDispenser = context.mock(CoinDispenser.class);
		VendingMachine machine = new VendingMachine(coinDispenser);

		context.checking(new Expectations() {{
			exactly(6).of(coinDispenser).dispense(Penny);
		}});

		for (int i = 0; i < 6; i++) machine.insertCoin(Penny);
        machine.returnCoins();

		context.assertIsSatisfied();
    }

}
