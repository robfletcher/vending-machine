package co.freeside.demo.vending;

import java.util.*;
import org.jmock.*;
import org.junit.*;
import static co.freeside.demo.vending.Coin.*;

public class CoinReturnTest {

	private Mockery context = new Mockery();

    @Test
    public void machineReturnsNoCoinsIfNoneAreInserted() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);

		context.checking(new Expectations() {{
			never(hardware).dispense(with(any(Coin.class)));
		}});

		machine.returnCoins();

		context.assertIsSatisfied();
	}

    @Test
    public void machineReturnsCoin() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);

		context.checking(new Expectations() {{
			oneOf(hardware).dispense(Penny);
		}});

		machine.insertCoin(Penny);
        machine.returnCoins();

		context.assertIsSatisfied();
    }

    @Test
    public void machineReturnsEfficientChange() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);
		machine.loadChange(Arrays.asList(Quarter, Dime, Nickel));

		context.checking(new Expectations() {{
			oneOf(hardware).dispense(Dime);
			oneOf(hardware).dispense(Nickel);
			exactly(2).of(hardware).dispense(Penny);
		}});

		for (int i = 0; i < 17; i++) machine.insertCoin(Penny);
        machine.returnCoins();

		context.assertIsSatisfied();
    }

    @Test
    public void machineReturnsAvailableChange() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);

		context.checking(new Expectations() {{
			exactly(6).of(hardware).dispense(Penny);
		}});

		for (int i = 0; i < 6; i++) machine.insertCoin(Penny);
        machine.returnCoins();

		context.assertIsSatisfied();
    }

}
