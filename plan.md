# Groovier Testing With Spock

## Intro

- huge advantages to Groovy
	- applies also to _JUnit_
	- Closures for assertions - e.g. `assert collection.every { it > x }`
	- no need to declare exceptions on test methods

## Basics

### `CreditTest`

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

- asserting invoked
- asserting never invoked
- asserting no further invocations
- wildcards
- Closure matchers
- mock vs stubs
	- ordering feels more natural for mocks than jMock's up front expectations

### Limitations

- diagnostics not that great when something was called but not the thing you expected
- cannot do static or partial mocking
	- could be argued requiring those is a design smell anyway

## `old`

### `VendingTest`

- first test appropriate for `old`

## Exception handling

### `VendingTest`

- `thrown` and `notThrown`
- wildcard usage
- mocks throwing exceptions
	- also mocks accessing arguments

## The `old` method

### `VendingTest`

- clarity of assertion
- less brittle

## Stepwise specifications

### `TransactionStoryTest`

- `@Shared`
	- initialization of `@Shared` variables only happens once (show non-shared & test failing)
	- mocks cannot be `@Shared`
- `old`
- `@Stepwise`
	- adds clarity
	- dangers; brittle (people add inappropriate stuff), violation of atomicity (stuff leaks from one test to next), slower to debug

## Asynchronous testing (if time)

### `TransactionReportingTest`

## still to cover…

### Lifecycle

- `@AutoCleanup`

### Mocks

- return values
- default return
- accessing parameters

### JUnit compatibility

- `@Rule` support
- Hamcrest matchers
