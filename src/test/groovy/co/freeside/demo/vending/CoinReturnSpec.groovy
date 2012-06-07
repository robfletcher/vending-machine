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
		given:
		machine.insertCoin(Penny)

		when:
		machine.returnCoins()

		then:
		1 * hardware.returnCoin(Penny)
		0 * hardware.returnCoin(_ as Coin)
	}

	void 'machine returns no coins if none are inserted'() {
		when:
		machine.returnCoins()

		then:
		0 * hardware.returnCoin(_)
	}

	void 'machine deducts returned coins from credit'() {
		given:
		hardware.returnCoin(Penny)

		and:
		machine.insertCoin(Penny)

		when:
		machine.returnCoins()

		then:
		machine.readCredit() == 0
	}

	void 'machine returns efficient change'() {
		given:
		machine.loadChange(Quarter, Dime, Nickel)

		when:
		17.times {
			machine.insertCoin(Penny)
		}
		machine.returnCoins()

		then:
		1 * hardware.returnCoin(Dime)
		1 * hardware.returnCoin(Nickel)
		2 * hardware.returnCoin(Penny)
		0 * hardware.returnCoin(_)
	}

	void 'machine returns available change'() {
		given:
		6.times {
			machine.insertCoin(Penny)
		}

		when:
		machine.returnCoins()

		then:
		6 * hardware.returnCoin(Penny)
	}

	void 'machine retains credit if it cannot make change'() {
		given:
		machine.addStock(ChocolateSaltyBalls)

		and:
		4.times {
			machine.insertCoin(Quarter)
		}

		when:
		machine.purchase(ChocolateSaltyBalls)
		machine.returnCoins()

		then:
		1 * hardware.dispense(ChocolateSaltyBalls)
		0 * hardware.returnCoin(_ as Coin)

		and:
		machine.readCredit() == 100 - ChocolateSaltyBalls.price
	}

}
