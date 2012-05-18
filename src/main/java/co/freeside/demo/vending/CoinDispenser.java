package co.freeside.demo.vending;

/**
 * Represents the physical device the vending machine uses to return coins.
 */
public interface CoinDispenser {

	void dispense(Coin coin);

}
