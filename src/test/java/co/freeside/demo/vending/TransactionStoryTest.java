package co.freeside.demo.vending;

import co.freeside.demo.vending.*;
import org.jmock.*;
import org.jmock.internal.*;
import org.junit.*;
import static co.freeside.demo.vending.Coin.*;
import static co.freeside.demo.vending.Product.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TransactionStoryTest {

	private Mockery context = new Mockery();

	@Test
	public void userCanCompleteComplexTransaction() {
		final HardwareDevice hardware = context.mock(HardwareDevice.class);
		VendingMachine machine = new VendingMachine(hardware);

		// the machine is well stocked...
		for (Product product : Product.values()) machine.addStock(10, product);

		// ... and has plenty of change
		for (Coin coin : Coin.values()) machine.loadChange(20, coin);

		final Sequence vending = new NamedSequence("vending");
		context.checking(new Expectations() {{
			oneOf(hardware).dispense(Slurm); inSequence(vending);
			oneOf(hardware).dispense(ApolloBar); inSequence(vending);
			oneOf(hardware).returnCoin(Quarter); inSequence(vending);
			oneOf(hardware).returnCoin(Dime); inSequence(vending);
		}});

		for (int i = 7; i > 0; i--) machine.insertCoin(Quarter);
		assertThat(machine.readCredit(), equalTo(7 * Quarter.getValue()));

		// the user purchases a product and credit is deducted
		machine.purchase(Slurm);
		assertThat(machine.readCredit(), equalTo((7 * Quarter.getValue()) - Slurm.getPrice()));

		// the user purchases another product and credit is deducted again
		machine.purchase(ApolloBar);
		assertThat(machine.readCredit(), equalTo((7 * Quarter.getValue()) - Slurm.getPrice() - ApolloBar.getPrice()));

		// the user asks for change
		machine.returnCoins();

		context.assertIsSatisfied();
		assertThat(machine.readCredit(), equalTo(0));
	}

}
