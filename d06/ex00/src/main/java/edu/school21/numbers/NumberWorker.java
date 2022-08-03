package edu.school21.numbers;

public class NumberWorker {


	public int sumDigits(long num) {
		int sum = 0;
		while (num > 0) {
			sum += num % 10;
			num /= 10;
		}
		return sum;
	}

	public boolean isPrime(int num) {

		if (num <= 1) {
			throw new IllegalNumberException();
		}
		if (num == 2) {
			return true;
		}

		int sqrt = 2;
		while (sqrt < num / sqrt)
			sqrt++;

		for (int i = 2; i <= sqrt; i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	}
}
