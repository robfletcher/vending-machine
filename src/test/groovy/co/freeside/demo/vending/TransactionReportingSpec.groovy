package co.freeside.demo.vending

import spock.lang.Specification

import java.util.concurrent.CountDownLatch

import com.sun.net.httpserver.*

import static co.freeside.demo.vending.Coin.*
import static co.freeside.demo.vending.Product.ApolloBar
import static java.net.HttpURLConnection.*
import spock.util.concurrent.BlockingVariable

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
		def report = new BlockingVariable<List<String>>()
		startReportServer(report)
		machine.reportingURL = 'http://localhost:8082/'

		when:
		machine.sendTransactionReport()

		then:
		report.get().size() == 1
		report.get()[0] ==~ /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2} \| ApolloBar/
	}

	void startReportServer(BlockingVariable<List<String>> report) {
		def handler = { HttpExchange httpExchange ->
			if (httpExchange.requestMethod == 'POST') {
				def reportLines = []
				httpExchange.requestBody.withReader { reader ->
					while (reader.ready()) {
						reportLines << reader.readLine()
					}
				}
				httpExchange.sendResponseHeaders HTTP_OK, 0
				report.set(reportLines)
			} else {
				httpExchange.sendResponseHeaders HTTP_BAD_METHOD, 0
			}
		} as HttpHandler
		httpServer.createContext('/', handler)
	}
}
