# Groovier Testing With Spock

## Intro

- huge advantages to Groovy
  - applies also to _JUnit_
  - Closures for assertions - e.g. `assert collection.every { it > x }`
  - no need to declare exceptions on test methods

## Basics

### `StockTest`

- `expect`
- `when`/`then` vs `given`/`expect`
- `given`/`when`/`then`

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

## Mocks

### `CoinReturnTest`

- asserting never invoked
- wildcards
- Closure matchers
- mock vs stubs

### `TransactionStoryTest`

- ordered interactions

### still to cover…

- return values
- throwing exceptions
- accessing parameters

## Stepwise specifications

### `TransactionStoryTest`

- adds clarity
- dangers; brittle (people add inappropriate stuff), violation of atomicity (stuff leaks from one test to next), slower to debug

## Asynchronous testing (if time)

### `TransactionReportingTest`
