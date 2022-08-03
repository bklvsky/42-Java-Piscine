package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {

	private static NumberWorker nw = new NumberWorker();


	@ParameterizedTest
	@ValueSource(ints = {5, 2, 13, 139})
	void isPrimeForPrimes(int x) {
		Assertions.assertTrue(nw.isPrime(x));
	}

	@ParameterizedTest
	@ValueSource(ints = {4, 169, 110, 10})
	void isPrimeForNotPrimes(int x) {
		Assertions.assertFalse(nw.isPrime(x));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, -100, -5})
	void isPrimeForIncorrectNumbers(int x) {
		Assertions.assertThrows(IllegalNumberException.class, () -> nw.isPrime(x));
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv", delimiter = ',')
	void checkDigitsSum(int input, int expected) {
		Assertions.assertEquals(nw.sumDigits(input), expected);
	}
}
