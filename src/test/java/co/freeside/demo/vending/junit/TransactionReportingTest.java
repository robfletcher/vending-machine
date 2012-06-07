package co.freeside.demo.vending.junit;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import co.freeside.demo.vending.*;
import com.sun.net.httpserver.*;
import org.hamcrest.*;
import org.jmock.*;
import org.junit.*;
import static co.freeside.demo.vending.Coin.*;
import static co.freeside.demo.vending.Product.*;
import static java.net.HttpURLConnection.*;
import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TransactionReportingTest {

	Mockery context = new Mockery();
	HardwareDevice hardware = context.mock(HardwareDevice.class);
	VendingMachine machine = new VendingMachine(hardware);
	HttpServer httpServer;

	@Before
	public void stubHardware() {
		context.checking(new Expectations() {{
			allowing(hardware).dispense(with(any(Product.class)));
		}});
	}

	@Before
	public void createReportServer() throws IOException {
		InetSocketAddress address = new InetSocketAddress(8082);
		httpServer = HttpServer.create(address, 0);
		httpServer.start();
	}

	@After
	public void destroyReportServer() {
		httpServer.stop(0);
	}

	@Test
	public void machineSendsTransactionReport() throws InterruptedException, ExecutionException, TimeoutException, IOException {
		machine.addStock(ApolloBar);

		machine.insertCoin(Quarter);
		machine.insertCoin(Quarter);
		machine.insertCoin(Dime);
		machine.insertCoin(Nickel);
		machine.purchase(ApolloBar);

		List<String> report = new ArrayList<>();
		CountDownLatch latch = new CountDownLatch(1);
		startReportServer(latch, report);

		machine.setReportingURL("http://localhost:8082/");
		machine.sendTransactionReport();

		assertThat(latch.await(2, SECONDS), is(true));
		assertThat(report.size(), is(1));
		assertThat(report.get(0), equalTo("2012-05-30T09:41:23 | ApolloBar"));
	}

	void startReportServer(final CountDownLatch latch, final List<String> report) {
		httpServer.createContext("/", new HttpHandler() {
			@Override
			public void handle(HttpExchange httpExchange) throws IOException {
				if ("POST".equalsIgnoreCase(httpExchange.getRequestMethod())) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
					while (reader.ready()) {
						report.add(reader.readLine());
					}
					httpExchange.sendResponseHeaders(HTTP_OK, 0);
				} else {
					httpExchange.sendResponseHeaders(HTTP_BAD_METHOD, 0);
				}
				httpExchange.close();
				latch.countDown();
			}
		});
	}
}
