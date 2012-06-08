package co.freeside.demo.vending

import spock.lang.Specification

import static co.freeside.demo.vending.Coin.Quarter
import static co.freeside.demo.vending.Product.Slurm

class VendingSpec extends Specification {

	HardwareDevice hardware = Mock(HardwareDevice)
	VendingMachine machine = new VendingMachine(hardware)

	void 'machine dispenses product and deducts price from credit'() {
		given:
		4.times {
			machine.insertCoin(Quarter)
		}

		when:
		machine.purchase(Slurm)

		then:
		1 * hardware.dispense(Slurm)

		and:
		machine.readCredit() == old(machine.readCredit()) - Slurm.price
	}

	void 'machine does not deduct credit if product is not available'() {
		given:
		3.times {
			machine.insertCoin(Quarter)
		}

		when:
		machine.purchase(Slurm)

		then:
		_ * hardware.dispense(_ as Product) >> { Product product -> throw new OutOfStockException(product) }

		and:
		machine.readCredit() == old(machine.readCredit())
	}

	void 'machine does not dispense product if not enough coins inserted'() {
		given:
		machine.insertCoin(Quarter)

		when:
		machine.purchase(Slurm)

		then:
		thrown InsufficientCreditException

		and:
		0 * hardware.dispense(_)

		and:
		machine.readCredit() == old(machine.readCredit())
	}

}
