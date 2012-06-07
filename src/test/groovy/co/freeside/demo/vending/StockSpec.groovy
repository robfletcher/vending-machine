package co.freeside.demo.vending

import spock.lang.Specification
import co.freeside.demo.vending.*

import static co.freeside.demo.vending.Coin.*
import static co.freeside.demo.vending.Product.ApolloBar

class StockSpec extends Specification {

	HardwareDevice hardware = Mock(HardwareDevice)
	VendingMachine machine = new VendingMachine(hardware)

	void 'empty machine should not report products in stock'() {
		expect:
		!machine.hasInStock(ApolloBar)
	}

	void 'adding stock means machine reports products in stock'() {
		when:
		machine.addStock(ApolloBar)

		then:
		machine.hasInStock(ApolloBar)
	}

	void 'purchasing products depletes stock'() {
		given:
		machine.addStock(ApolloBar);

		when:
		machine.insertCoin(Quarter);
		machine.insertCoin(Quarter);
		machine.insertCoin(Dime);
		machine.insertCoin(Nickel);

		and:
		machine.purchase(ApolloBar);

		then:
		!machine.hasInStock(ApolloBar)
	}
}
