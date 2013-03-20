package co.freeside.demo.vending

import spock.lang.Specification
import spock.util.concurrent.BlockingVariable
import com.sun.net.httpserver.*

import static co.freeside.demo.vending.Coin.*
import static co.freeside.demo.vending.Product.ApolloBar
import static java.net.HttpURLConnection.*

class TransactionReportingSpec extends Specification {

	def hardware = Mock(HardwareDevice)
	def machine = new VendingMachine(hardware)
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
		startReportServer report
		machine.reportingURL = 'http://localhost:8082/'

		when:
		machine.sendTransactionReport()

		then:
		report.get().size() == 1
		report.get()[0] ==~ /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2} \| ApolloBar/
	}

	void startReportServer(BlockingVariable<List<String>> report) {
		def handler = { HttpExchange httpExchange ->
			def reportLines = []
			if (httpExchange.requestMethod == 'POST') {
				httpExchange.requestBody.withReader { reader ->
					while (reader.ready()) {
						reportLines << reader.readLine()
					}
				}
				httpExchange.sendResponseHeaders HTTP_OK, 0
			} else {
				httpExchange.sendResponseHeaders HTTP_BAD_METHOD, 0
			}
			httpExchange.close()
			report.set(reportLines)
		} as HttpHandler
		httpServer.createContext('/', handler)
	}
}
