package co.freeside.demo.vending;

import org.jmock.*;
import org.junit.*;
import static co.freeside.demo.vending.Coin.*;
import static co.freeside.demo.vending.Product.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class VendingTest {

	private Mockery context = new Mockery();

	@Test
	public void machineDoesNotDeductCreditIfProductIsNotAvailable() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);

		context.checking(new Expectations() {{
			never(hardware).dispense(with(any(Product.class)));
		}});

		for (int i = 3; i > 0; i--) machine.insertCoin(Quarter);
		try {
			machine.purchase(Slurm);
			fail("Should have thrown OutOfStockException");
		} catch (OutOfStockException e) {
			// expected
		}

		context.assertIsSatisfied();
		assertThat(machine.readCredit(), equalTo(Quarter.getValue() * 3));
	}

	@Test
	public void machineDoesNotDispenseProductIfNotEnoughCoinsInserted() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);

		context.checking(new Expectations() {{
			never(hardware).dispense(with(any(Product.class)));
		}});

		machine.insertCoin(Quarter);
		try {
			machine.purchase(Slurm);
			fail("Should have thrown InsufficientCreditException");
		} catch (InsufficientCreditException e) {
			// expected
		}

		context.assertIsSatisfied();
		assertThat(machine.readCredit(), equalTo(Quarter.getValue()));
	}

	@Test
	public void machineDispensesProductAndDeductsPriceFromCredit() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);
		machine.addStock(Slurm);

		context.checking(new Expectations() {{
			oneOf(hardware).dispense(Slurm);
		}});

		for (int i = 4; i > 0; i--) machine.insertCoin(Quarter);
		machine.purchase(Slurm);

		context.assertIsSatisfied();
		assertThat(machine.readCredit(), equalTo(Quarter.getValue()));
		assertThat(machine.hasInStock(Slurm), is(false));
	}

	@Test
	public void machineKeepsCreditIfDispensingFails() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);
		machine.addStock(Slurm);

		context.checking(new Expectations() {{
			oneOf(hardware).dispense(Slurm); will(throwException(new DispensingFailureException()));
		}});

		for (int i = 4; i > 0; i--) machine.insertCoin(Quarter);
		machine.purchase(Slurm);

		context.assertIsSatisfied();
		assertThat(machine.readCredit(), equalTo(4 * Quarter.getValue()));
	}

}
