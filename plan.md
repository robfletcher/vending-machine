# Groovier Testing With Spock

## Intro

- general advantages to Groovy
	- apply also to _JUnit_
	- Closures for assertions - e.g. `assert collection.every { it > x }`
	- no need to declare exceptions on test methods

## Basics: `CreditTest` -> `CreditSpec`

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

## Paramaterization

- one of the most painful things in JUnit

### `CreditParamaterizedTest`

- behaviour of data setup method is complex 
	- how many dimensions in that array?
- results are reported separately but no description
- cannot use different parameters for different test methods

### `CreditTheory`

- data setup is a little easier
- no separate reporting
	- this really sucks
	- _show it breaking_; change `insert(coin)` to `insert(coins[0])`
	- might as well just use a loop
- again parameters are per class not per test

### to Spock… `CreditParamaterizedSpec`

- 1st test:
	- work up gradually:
	- start with `where: coins << [...]`
		- can be any `Iterable`, e.g. method call
	- add calculated param
	- demonstrate @Unroll
		- at method level with auto-complete in pattern
		- report implications
		- move pattern to method name
		- move `@Unroll` to class
		- why `@Unroll` pattern is sometimes better than embedding it in method name

### With Spock it's easy to add more paramaterized specs to the same class

- 2nd test:
	- show table 
		- keep calculations in spec
	- don't use `old`… yet
	- move calculated values to `where` block mixed with table
- even when not iterating `where` blocks can clarify test inputs and outputs

## Mocks: `CoinReturnTest` -> `CoinReturnSpec`

- 1st test:
	- asserting invoked
	- asserting no further invocations
	- wildcard params
- 2nd test:
	- asserting never invoked
- 3rd test:
	- stubs _just work_
	- show explicit stub declaration
- 4th test:
	- grouping assertions using `with`
	
- show expectation failures
- explain `Stub` 
	- for clarity only; you can stub with a `Mock`
- explain `Spy` 
	- evil but helpful especially in legacy
	- must be a class not an interface
	- can act as "partial" mocks

### Limitations

- cannot do static or partial mocking
	- could be argued requiring those is a design smell anyway

## `VendingTest` -> `VendingSpec`

- 1st test appropriate for `old`
	- clarity of assertion
	- less brittle
- 2nd test shows:
	- mocks throwing exceptions
	- mocks accessing arguments
- 3rd test shows exception handling using `thrown`

## `TransactionStoryTest` -> `TransactionStorySpec`

- `@Stepwise`
	- adds clarity
	- dangers; brittle (people add inappropriate stuff), violation of atomicity (stuff leaks from one test to next), slower to debug
- `@Shared`
	- initialization of `@Shared` variables only happens once (show non-shared & test failing)
	- mocks cannot be `@Shared`

### Asynchronous testing: `TransactionReportingTest` -> `TransactionReportingSpec`

- Just show use of `BlockingVariable`

## New stuff to cover

- Interaction closure on `Mock` declaration
- Multiple `then` blocks to enforce interaction order
- Returning things from mocks
	- sequential return values
- Global mocks
- Grouping stuff using `with`
