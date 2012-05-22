# Test structure

- Separation of given/when/then/cleanup gives real clarity to tests.

# Paramaterized tests

- Both have the flaw that all tests have to use the parameters; you can't mix parameterized and regular tests.

## `CreditTheory`

This test uses `@RunWith(Theories.class)`

- Data setup for theories is a little nicer
- Theories will fail with the first failing data point - unlike Spock's `where` block. What's the advantage over a loop?

## `CreditTest`

This test used `@RunWith(Parameterized.class)`

- It will run all variations even if some fail
- Data setup is confusing (to me at least)

# Mocks

## `CoinReturnTest`

- Have to declare expectations in advance. Spock's format feels more natural.
- Spock mocks can perform default actions without expectations being set.

# Exception handling

## `VendingTest`

- don't need try/catch block

# Stepwise

- talk about downsides - violation of OCP
- good for telling a story

## TransactionStoryTest

- a run-on sentence