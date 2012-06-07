package co.freeside.demo.vending;

import org.jmock.*;
import org.junit.*;
import static co.freeside.demo.vending.Coin.*;
import static co.freeside.demo.vending.Product.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class StockTest {

	Mockery context = new Mockery();

	HardwareDevice hardware = context.mock(HardwareDevice.class);
	VendingMachine machine = new VendingMachine(hardware);

	@Before
	public void stubHardware() {
		context.checking(new Expectations() {{
			allowing(hardware).dispense(with(any(Product.class)));
		}});
	}

	@Test
	public void emptyMachineShouldNotReportProductsInStock() {
		assertThat(machine.hasInStock(ApolloBar), is(false));
	}

	@Test
	public void addingStockMeansMachineReportsProductsInStock() {
		machine.addStock(ApolloBar);

		assertThat(machine.hasInStock(ApolloBar), is(true));
	}

	@Test
	public void purchasingProductsDepletesStock() {
		machine.addStock(ApolloBar);

		machine.insertCoin(Quarter);
		machine.insertCoin(Quarter);
		machine.insertCoin(Dime);
		machine.insertCoin(Nickel);
		machine.purchase(ApolloBar);

		assertThat(machine.hasInStock(ApolloBar), is(false));
	}

}
