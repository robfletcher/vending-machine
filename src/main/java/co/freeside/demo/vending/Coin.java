package co.freeside.demo.vending;

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

	public static final Ordering<Coin> MOST_VALUABLE_FIRST = new Ordering<Coin>() {
		@Override
		public int compare(Coin left, Coin right) {
			return left.getValue() - right.getValue();
		}
	};

	public static Predicate<Coin> maxValuePredicate(final int maxValue) {
		return new Predicate<Coin>() {
			@Override
			public boolean apply(Coin input) {
				return input.getValue() <= maxValue;
			}
		};
	}

}
