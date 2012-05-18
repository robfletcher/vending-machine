package co.freeside.demo.vending;

import java.util.*;

/**
 * Represents a vending machine that has various products.
 */
public class VendingMachine {

	private CoinDispenser coinDispenser;
	private int credit = 0;

	VendingMachine(CoinDispenser coinDispenser) {
		this.coinDispenser = coinDispenser;
	}

	Map<String, Product> showProducts() {
		return Collections.emptyMap();
	}

	int readCredit() {
		return credit;
	}

	void insertCoin(Coin coin) {
		credit += coin.getValue();
	}

	void purchase(String productCode) {

	}

	void returnCoins() {
		while (credit > 0) {
			for (Coin coin : Coin.getCoinsToValue(credit)) {
				coinDispenser.dispense(coin);
				credit -= coin.getValue();
			}
		}
	}

}
