package co.freeside.demo.vending

import spock.lang.Specification

import static co.freeside.demo.vending.Coin.Penny
import static co.freeside.demo.vending.Coin.Quarter
import static co.freeside.demo.vending.Coin.Dime
import static co.freeside.demo.vending.Coin.Nickel
import static co.freeside.demo.vending.Product.ChocolateSaltyBalls

class CoinReturnSpec extends Specification {

	HardwareDevice hardware = Mock(HardwareDevice)
	VendingMachine machine = new VendingMachine(hardware)

	void 'machine returns coin'() {
	}

	void 'machine returns no coins if none are inserted'() {
	}

	void 'machine deducts returned coins from credit'() {
	}

	void 'machine returns efficient change'() {
	}

	void 'machine returns available change'() {
	}

	void 'machine retains credit if it cannot make change'() {
	}

}
