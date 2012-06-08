package co.freeside.demo.vending

import spock.lang.*

import static co.freeside.demo.vending.Coin.Quarter
import static co.freeside.demo.vending.Product.Slurm
import static co.freeside.demo.vending.Product.ApolloBar
import static co.freeside.demo.vending.Coin.Dime

@Stepwise
class TransactionStorySpec extends Specification {

	@Shared VendingMachine machine = new VendingMachine()
	HardwareDevice hardware = Mock(HardwareDevice)

	void setupSpec() {
		// the machine has plenty of change
		for (coin in Coin.values()) machine.loadChange(20, coin)
	}

	void setup() {
		machine.hardware = hardware
	}

	void 'user can accumulate credit'() {
		when:
		7.times {
			machine.insertCoin(Quarter)
		}

		then:
		machine.readCredit() == 7 * Quarter.value
	}

	void 'user can buy a product'() {
		when:
		machine.purchase(product)

		then:
		1 * hardware.dispense(product)

		and:
		machine.readCredit() == old(machine.readCredit()) - product.price

		where:
		product << [Slurm, ApolloBar]
	}

	void 'user can get change'() {
		when:
		machine.returnCoins()

		then:
		1 * hardware.returnCoin(Quarter)
		1 * hardware.returnCoin(Dime)
	}

	void 'credit is deducted'() {
		expect:
		machine.readCredit() == 0
	}

}
