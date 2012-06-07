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
	}

	void 'purchasing products deducts credit'() {
	}

}
