package co.freeside.demo.vending

import spock.lang.Specification

import java.util.concurrent.CountDownLatch

import com.sun.net.httpserver.*

import static co.freeside.demo.vending.Coin.*
import static co.freeside.demo.vending.Product.ApolloBar
import static java.net.HttpURLConnection.*
import static java.util.concurrent.TimeUnit.SECONDS

class TransactionReportingSpec extends Specification {

	HardwareDevice hardware = Mock(HardwareDevice)
	VendingMachine machine = new VendingMachine(hardware)
	HttpServer httpServer

	void setup() {
		def address = new InetSocketAddress(8082)
		httpServer = HttpServer.create(address, 0)
		httpServer.start()
	}

	void cleanup() {
		httpServer.stop(0)
	}

	void 'machine sends transaction report'() {
		given:
		machine.insertCoin(Quarter);
		machine.insertCoin(Quarter);
		machine.insertCoin(Dime);
		machine.insertCoin(Nickel);
		machine.purchase(ApolloBar);

		and:
		def report = []
		def latch = new CountDownLatch(1)
		startReportServer(latch, report)
		machine.reportingURL = 'http://localhost:8082/'

		when:
		machine.sendTransactionReport()

		then:
		latch.await(2, SECONDS)
		report.size() == 1
		report[0] ==~ /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2} \| ApolloBar/
	}

	void startReportServer(CountDownLatch latch, List<String> report) {
		def handler = { HttpExchange httpExchange ->
			if (httpExchange.requestMethod == 'POST') {
				httpExchange.requestBody.withReader { reader ->
					while (reader.ready()) {
						report << reader.readLine()
					}
				}
				httpExchange.sendResponseHeaders HTTP_OK, 0
			} else {
				httpExchange.sendResponseHeaders HTTP_BAD_METHOD, 0
			}
			httpExchange.close()
			latch.countDown()
		} as HttpHandler
		httpServer.createContext('/', handler)
	}
}
