# Groovier Testing With Spock

## Intro

- huge advantages to Groovy
	- applies also to _JUnit_
	- Closures for assertions - e.g. `assert collection.every { it > x }`
	- no need to declare exceptions on test methods

## Basics

### `StockTest`

- structure:
	- `expect`
	- `when`/`then` vs `given`/`expect`
	- `given`/`when`/`then`
	- `and`
- implicit assertions
- diagnostics
	- demo power assert
- `setup` method
- labelling blocks
	- not just comments - retained in bytecode
- stubs _just work_

## Paramaterization

- one of the most painful things in JUnit

### `CreditParamaterizedTest`

- behaviour of data setup method is complex
- results are reported separately but no description
- cannot use different parameters for different test methods

### `CreditTheory`

- data setup is a little easier
- no separate reporting
	- this really sucks
	- _show it breaking_
	- might as well just use a loop
- again parameters are per class not per test

### to Spock…

- work up gradually:
	- start with `where: coins << [...]`
	- add calculated param
	- change to table for clarity when using multiple coins
	- demonstrate @Unroll with report implications
	- why `@Unroll` pattern is sometimes better than embedding it in method name

## Mocks

### `CoinReturnTest`

- asserting never invoked
- wildcards
- Closure matchers
- mock vs stubs
	- ordering feels more natural for mocks than jMock's up front expectations

### `TransactionStoryTest`

- ordered interactions

### Limitations

- diagnostics not that great when something was called but not the thing you expected
- cannot do static or partial mocking
	- could be argued requiring those is a design smell anyway

## Exception handling

### `VendingTest`

- `thrown` and `notThrown`
- wildcard usage
- mocks throwing exceptions

## The `old` method

### `VendingTest`

- clarity of assertion
- less brittle

## Stepwise specifications

### `TransactionStoryTest`

- adds clarity
- dangers; brittle (people add inappropriate stuff), violation of atomicity (stuff leaks from one test to next), slower to debug

## Asynchronous testing (if time)

### `TransactionReportingTest`

## still to cover…

### Lifecycle

- `@Shared`
- `@AutoCleanup`

### Mocks

- return values
- default return
- accessing parameters

### JUnit compatibility

- `@Rule` support
- Hamcrest matchers
