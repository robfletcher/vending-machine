# Paramaterized tests

- Both have the flaw that all tests have to use the parameters; you can't mix parameterized and regular tests.

## CreditTheory

This test uses @RunWith(Theories.class)

- Data setup for theories is a little nicer
- Theories will fail with the first failing data point - unlike Spock's where block. What's the advantage over a loop?

## CreditTest

This test used @RunWith(Parameterized.class)

- It will run all variations even if some fail
- Data setup is confusing (to me at least)