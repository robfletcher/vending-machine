package co.freeside.demo.vending

import spock.lang.Specification

import static co.freeside.demo.vending.Coin.*
import spock.lang.Unroll

import static co.freeside.demo.vending.Product.Slurm
import static co.freeside.demo.vending.Product.ChocolateSaltyBalls

@Unroll
class CreditParamaterizedSpec extends Specification {

	VendingMachine machine = new VendingMachine()

	void 'inserting #coins increments credit to #expectedValue'() {
		when:
		for (coin in coins) machine.insertCoin(coin)

		then:
		machine.readCredit() == expectedValue

		where:
		coins << [[Penny], [Nickel], [Dime, Quarter]]
		expectedValue = coins.sum { it.value }
	}

	void 'purchasing products deducts credit'() {
		given:
		for (coin in coins) machine.insertCoin(coin)

		when:
		for (product in products) machine.purchase(product)

		then:
		machine.readCredit() == old(machine.readCredit()) - products.sum { it.price }

		where:
		coins                           | products
		4 * [Quarter]                   | [Slurm]
		(3 * [Quarter]) + (10 * [Dime]) | [ChocolateSaltyBalls, Slurm]
	}

}
