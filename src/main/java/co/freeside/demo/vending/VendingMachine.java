package co.freeside.demo.vending;

import java.util.*;
import com.google.common.collect.*;

/**
 * Represents a vending machine that has various products.
 */
public class VendingMachine {

	private CoinDispenser coinDispenser;
	private Multiset<Coin> change = EnumMultiset.create(Coin.class);
	private int credit = 0;

	VendingMachine(CoinDispenser coinDispenser) {
		this.coinDispenser = coinDispenser;
	}

	/**
	 * @return the value of all coins that have been inserted.
	 */
	int readCredit() {
		return credit;
	}

	/**
	 * Inserts a coin adding to credit.
	 */
	void insertCoin(Coin coin) {
		change.add(coin);
		credit += coin.getValue();
	}

	/**
	 * Loads change into the machine without adding to credit.
	 */
	void loadChange(Collection<Coin> coins) {
		this.change.addAll(coins);
	}

	/**
	 * Purchases a product, deducting its price from the current credit.
	 */
	void purchase(Product product) {

	}

	void returnCoins() {
		while (credit > 0) {
			Coin coin = removeLargestCoinFromChange(credit);
			credit -= coin.getValue();
			coinDispenser.dispense(coin);
		}
	}

	private Coin removeLargestCoinFromChange(int maxValue) {
		Collection<Coin> smallerCoins = Collections2.filter(change, Coin.maxValuePredicate(maxValue));
		Coin biggestSmallerCoin = Coin.MOST_VALUABLE_FIRST.max(smallerCoins);
		assert change.remove(biggestSmallerCoin);
		return biggestSmallerCoin;
	}

}
