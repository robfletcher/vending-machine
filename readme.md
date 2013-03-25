This is a demo application used for delivering a presentation on [Spock](http://spockframework.org/). The presentation is aimed at Java developers familiar with [JUnit](http://junit.org/) and consists of a re-implementation of some JUnit tests as Spock specifications.

The application is a simple simulation of a vending machine. There are unit tests written using JUnit and [JMock](http://jmock.org/) under `src/test/java` and corresponding Spock specifications under `src/test/groovy`.

In order to run the tests just execute `./gradlew test`.

In addition to the _master_ branch there is a _demo-start_ branch which is the starting point for the demo. It contains only the bare-bones of the Spock specifications.

I have presented this demo at…

* _GR8Conf_ June 2012 in Copenhagen 
* [_Groovy & Grails Exchange_](http://skillsmatter.com/podcast/groovy-grails/groovier-testing-with-spock) December 2012 in London 
* [_Devoxx UK_](http://www.devoxx.com/display/UK13/Groovier+Testing+with+Spock) March 2013 in London