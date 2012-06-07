package co.freeside.demo.vending

import spock.lang.Specification

import static co.freeside.demo.vending.Coin.*
import spock.lang.Unroll

@Unroll
class CreditSpec extends Specification {

	VendingMachine machine = new VendingMachine()

	void 'inserting #coins increments credit to #expectedValue'() {
		when:
		for (coin in coins) machine.insertCoin(coin)

		then:
		machine.readCredit() == expectedValue

		where:
		coins           | expectedValue
		[Penny]         | 1
		[Nickel]        | 5
		[Dime, Quarter] | 35
	}

}
