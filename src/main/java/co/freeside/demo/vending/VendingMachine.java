package co.freeside.demo.vending;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Represents a vending machine that has various products.
 */
public class VendingMachine {

	private Multiset<Coin> change = EnumMultiset.create(Coin.class);
	private HardwareDevice hardware;
	private int credit = 0;

	VendingMachine() {
		this(null); // useful for tests
	}

	VendingMachine(HardwareDevice hardware) {
		this.hardware = hardware;
	}

	public void setHardware(HardwareDevice hardware) {
		this.hardware = hardware;
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
	 * Purchases a product, deducting its price from the current credit.
	 */
	void purchase(Product product) throws OutOfStockException {
		if (credit < product.getPrice()) throw new InsufficientCreditException(credit, product);
		try {
			hardware.dispense(product);
			credit -= product.getPrice();
			transactionLog.add(String.format("%1$tFT%1$tT | %2$s", new Date(), product));
		} catch (OutOfStockException | DispensingFailureException e) {
			// display error
		}
	}

	/**
	 * Loads change into the machine without adding to credit.
	 */
	void loadChange(Collection<Coin> coins) {
		this.change.addAll(coins);
	}

	/**
	 * Loads change into the machine without adding to credit.
	 */
	void loadChange(Coin... coins) {
		loadChange(Arrays.asList(coins));
	}

	/**
	 * Loads change into the machine without adding to credit.
	 */
	void loadChange(int num, Coin coin) {
		loadChange(Collections.nCopies(num, coin));
	}

	void returnCoins() {
		try {
			while (credit > 0) {
				Coin coin = removeLargestCoinFromChange(credit);
				credit -= coin.getValue();
				hardware.returnCoin(coin);
			}
		} catch (CannotMakeChangeException e) {

		}
	}

	private Coin removeLargestCoinFromChange(int maxValue) {
		Collection<Coin> smallerCoins = Collections2.filter(change, Coin.maxValuePredicate(maxValue));
		if (smallerCoins.isEmpty()) {
			throw new CannotMakeChangeException(maxValue);
		} else {
			Coin biggestSmallerCoin = Coin.MOST_VALUABLE_FIRST.max(smallerCoins);
			assert change.remove(biggestSmallerCoin);
			return biggestSmallerCoin;
		}
	}

	private URL reportingURL;
	private Queue<String> transactionLog = new ArrayDeque<>();
	private ExecutorService reportingThread = Executors.newSingleThreadExecutor();

	public void setReportingURL(String url) throws MalformedURLException {
		this.reportingURL = new URL(url);
	}

	public void sendTransactionReport() throws IOException {
		reportingThread.submit(new Runnable() {
			@Override
			public void run() {
				try {
					HttpURLConnection connection = (HttpURLConnection) reportingURL.openConnection();
					connection.setDoOutput(true);
					Writer writer = new OutputStreamWriter(connection.getOutputStream());
					while (!transactionLog.isEmpty()) {
						writer.write(transactionLog.remove());
					}
					writer.close();

					assert connection.getResponseCode() == HTTP_OK;
					connection.getInputStream();
				} catch (IOException e) {
					System.err.printf("Reporting thread failed: %s%n", e.getMessage());
				}
			}
		});
	}
}
