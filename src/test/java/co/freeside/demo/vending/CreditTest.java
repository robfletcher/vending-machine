package co.freeside.demo.vending;

import co.freeside.demo.vending.*;
import org.junit.*;
import static co.freeside.demo.vending.Coin.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class CreditTest {

	VendingMachine machine = new VendingMachine();

	@Test
	public void creditShouldBeZeroWhenNoCoinsAreInserted() {
		assertThat(machine.readCredit(), equalTo(0));
	}

	@Test
	public void creditShouldIncrementWhenCoinsAreInserted() {
		machine.insertCoin(Penny);

		assertThat(machine.readCredit(), equalTo(Penny.getValue()));
	}

	@Test
	public void creditShouldAccumulateAsMoreCoinsAreInserted() {
		machine.insertCoin(Nickel);
		machine.insertCoin(Dime);

		assertThat(machine.readCredit(), equalTo(Nickel.getValue() + Dime.getValue()));
	}

}
