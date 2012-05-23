package co.freeside.demo.vending;

/**
 * Represents the physical device the vending machine uses to vend products and return coins.
 */
public interface HardwareDevice {

	void dispense(Product product);
	void returnCoin(Coin coin);

}
