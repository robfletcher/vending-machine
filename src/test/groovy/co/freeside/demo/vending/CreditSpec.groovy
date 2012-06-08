package co.freeside.demo.vending

import spock.lang.Specification

import static co.freeside.demo.vending.Coin.Penny
import static co.freeside.demo.vending.Coin.Nickel
import static co.freeside.demo.vending.Coin.Dime

class CreditSpec extends Specification {

	VendingMachine machine = new VendingMachine()

	void 'credit should be zero when no coins are inserted'() {
		expect:
		machine.readCredit() == 0
	}

	void 'credit should increment when coins are inserted'() {
		when:
		machine.insertCoin(Penny)

		then:
		machine.readCredit() == Penny.value
	}

	void 'credit should accumulate as more coins are inserted'() {
		given:
		machine.insertCoin(Nickel)

		when:
		machine.insertCoin(Dime)

		then:
		machine.readCredit() == [Nickel, Dime].sum { it.value }
	}

}
