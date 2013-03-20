package co.freeside.demo.vending

import spock.lang.*
import static co.freeside.demo.vending.Coin.*
import static co.freeside.demo.vending.Product.*

@Unroll
class CreditParamaterizedSpec extends Specification {

	def machine = new VendingMachine()

	void setup() {
		machine.hardware = Stub(HardwareDevice)
	}

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
		machine.readCredit() == totalInserted - totalSpent

		where:
		coins                       | products
		[Quarter] * 4               | [Slurm]
		[Quarter] * 3 + [Dime] * 10 | [ChocolateSaltyBalls, Slurm]

		totalInserted = coins.sum { it.value }
		totalSpent = products.sum { it.price }
	}

}
