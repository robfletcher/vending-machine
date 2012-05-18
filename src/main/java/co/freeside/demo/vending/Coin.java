package co.freeside.demo.vending;

import java.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public enum Coin {

	Penny(1), Nickel(5), Dime(10), Quarter(25);

	private int value;

	private Coin(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private static final Ordering<Coin> DESCENDING_VALUE_ORDER = new Ordering<Coin>() {
		@Override
		public int compare(Coin left, Coin right) {
			return left.getValue() - right.getValue();
		}
	};

	/**
	 * Gets a collection of coins that add up to the value of `value`.
	 */
	public static Collection<Coin> getCoinsToValue(int value) {
		Collection<Coin> coins = EnumMultiset.create(Coin.class);
		while (value > 0) {
			Collection<Coin> smallerCoins = Collections2.filter(Arrays.asList(values()), new MaxValuePredicate(value));
			Coin biggestSmallerCoin = DESCENDING_VALUE_ORDER.max(smallerCoins);
			coins.add(biggestSmallerCoin);
			value -= biggestSmallerCoin.getValue();
		}
		return coins;
	}

	private static class MaxValuePredicate implements Predicate<Coin> {

		private final int maxValue;

		private MaxValuePredicate(int maxValue) {
			this.maxValue = maxValue;
		}

		@Override
		public boolean apply(Coin input) {
			return input.getValue() <= maxValue;
		}
	}

}
