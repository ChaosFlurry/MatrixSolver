import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MathOperation {
/**
	 * Returns a Fraction in its lowest terms. A fraction cannot be further
	 * simplified when its numerator or denominator is prime or 1. If the
	 * denominator of a fraction is negative, it is changed to positive.
	 *
	 * @param f
	 *            The Fraction to be simplified
	 * @return A Fraction in lowest terms equivalent to f
	 */
	public static Fraction simplify(Fraction f) {
		int numerator = f.getNumerator();
		int denominator = f.getDenominator();
		int gcd = gcd(numerator, denominator);
		numerator = (numerator / gcd);
		denominator = (denominator / gcd);
		if (denominator < 0) {
			numerator *= -1;
			denominator *= -1;
		}
		// f.setNumerator(numerator);
		// f.setDenominator(denominator);
		return new Fraction(numerator, denominator);
	}

	/**
	 * Returns the reciprocal of a Fraction f (1/f), which can be represented as
	 * d/n where n is the numerator and d is the denominator.
	 *
	 * @param f
	 *            A Fraction
	 * @return The reciprocal of f as a Fraction
	 */
	public static Fraction reciprocal(Fraction f) {
		int numerator = f.getDenominator();
		int denominator = f.getNumerator();
		return new Fraction(numerator, denominator);
	}

	/**
	 * Returns the absolute value of a fraction. The absolute value cannot be
	 * less than 0. If the denominator of a fraction is negative, it is changed
	 * to positive (by multiplying both the numerator and denominator by -1).
	 *
	 * @param f
	 *            A Fraction
	 * @return A Fraction which has the absolute value of f
	 */

	public static Fraction abs(Fraction f) {
		/*
		 * The numerator and denominator are changed to a positive value (if
		 * already not positive), since the absolute value cannot equate to a
		 * value less than 0.
		 */
		int numerator = (f.getNumerator() < 0) ? f.getNumerator() * -1 : f.getNumerator();
		int denominator = (f.getDenominator() < 0) ? f.getDenominator() * -1 : f.getDenominator();
		return new Fraction(numerator, denominator);
	}

	/**
	 * Uses the Euclidean algorithm to determine the greatest common denominator
	 * of two numbers.
	 *
	 * @param a
	 *            An integer
	 * @param b
	 *            Another integer
	 * @return The greatest common denominator of a and b
	 */
	public static int gcd(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);
		// TODO batch gcd
		// TODO use divisors to find divisors of a and b then find the largest
		// value in the list shared by a and b

		int gcd;
		if (a == b) {
			gcd = a;
		} else {
			while (a != 0 && b != 0) {
				// temporary variable
				int t = b;
				b = a % b;
				a = t;
			}
			gcd = a + b;
		}
		return gcd;
	}

	public static int lcm(int a, int b) {
		return a / gcd(a, b) * b;
	}

	public static int pow(int n, int degree) {
		int result;
		if (degree < 0) {
			result = 0;
		} else if (degree == 0) {
			result = 0;
		} else if (degree == 1) {
			result = n;
		} else {
			// 1 * n = n
			result = 1;
			for (int i = 0; i < degree; i++) {
				result *= n;
			}
		}
		return result;
	}

	/**
	 * Finds the (real-valued) nth root of an integer. Returns 0 if n is 0,
	 * degree < 0, or if n < 0 and the degree is even.
	 *
	 * @param n
	 *            An integer
	 * @return The nth root of n floored to an integer.
	 */
	public static int nthRoot(int n, int degree) {
		if (n == 0 || degree < 0 || (n < 0 && degree % 2 == 0)) {
			return 0;
		}
		if (degree == 1 || n == 1 || n == -1) {
			return n;
		}

		int result = 0;
		if (n > 0) {
			for (int i = 1; i <= n; i++) {
				if (Math.pow(i, degree) > n) {
					result = i - 1;
					break;
				}
			}
		} else if (n < 0) {
			for (int i = -1; i > n; i--) {
				if (Math.pow(i, degree) < n) {
					result = i + 1;
					break;
				}
			}
		}
		return result;
	}

	public static boolean hasExactNthRoot(int n, int degree) {
		boolean result = false;
		int root = nthRoot(n, degree);
		int power = pow(root, degree);
		if (power == n) {
			result = true;
		}
		return result;
	}

	/**
	 * Tests if a number is prime.
	 *
	 * @param n
	 *            An integer
	 * @return true if n is prime, false if n is composite
	 */
	public static boolean isPrime(int n) {
		int sqrtN = (int) Math.sqrt(n);
		boolean isPrime;
		if (n == 0 || n == 1) {
			isPrime = false;
		} else if (n < 0) {
			isPrime = false;
		} else if (n % 2 == 0) {
			isPrime = false;
		} else {
			isPrime = true;
			for (int i = 3; i <= sqrtN; i += 2) {
				if (n % i == 0) {
					isPrime = false;
					break;
				}
			}
		}
		return isPrime;
	}

	/**
	 * Returns the divisors of a number.
	 *
	 * @param n
	 *            An integer
	 * @return A List of all divisors of n
	 */
	public static List<Integer> divisors(int n) {
		n = Math.abs(n);
		List<Integer> divisors = new ArrayList<>();

		if (n == 0 || n == 1) {
			divisors.add(n);
		} else {
			for (int i = 1; i <= Math.sqrt(n); i++) {
				if (n % i == 0) {
					divisors.add(i);
					// avoids duplicate divisors of perfect squares
					if (i != n / i) {
						divisors.add(n / i);
					}
				}
			}
			Collections.sort(divisors);
		}
		return divisors;
	}

	/**
	 * Returns the prime factors of a number.
	 *
	 * @param n
	 *            An integer
	 * @return A List of all prime factors of n
	 */
	public static List<Integer> primeFactors(int n) {
		n = Math.abs(n);
		// 0 and 1 have no prime factors
		if (n == 0 || n == 1) {
			return new ArrayList<>();
		}

		List<Integer> primeFactors = new ArrayList<>();
		List<Integer> primesUpToSqrtN = new ArrayList<>();

		int sqrtN = (int) Math.sqrt(n);
		primesUpToSqrtN.add(2);
		for (int i = 3; i <= sqrtN; i += 2) {
			if (isPrime(i)) {
				primesUpToSqrtN.add(i);
			}
		}

		// check if divisible by factors in primesUpToSqrtN
		// upon n being divisible, n = n / divisor
		// repeat until n is prime

		while (n != 1 && (!isPrime(n))) {
			for (int p : primesUpToSqrtN) {
				if (n % p == 0) {
					primeFactors.add(p);
					n = n / p;
				}
			}
		}

		if (isPrime(n))
			primeFactors.add(n);
		Collections.sort(primeFactors);
		return primeFactors;
	}

	/***
	 * Returns the sum of n!
	 *
	 * @param n
	 *            An integer
	 * @return 0 if n is negative, n! otherwise.
	 */
	// http://nntdm.net/papers/nntdm-19/NNTDM-19-2-30_42.pdf
	public static int factorial(int n) {
		if (n < 0)
			return 0;
		return (n == 0 || n == 1) ? 1 : n * factorial(n - 1);
	}
	
	/**
	 * TODO update this javadoc Returns a normalized String of a term of a
	 * polynomial that is ready to be printed. Instead of ...+1x method will
	 * return ... +x -1x will return -x etc.
	 *
	 * @param n
	 *            An integer
	 * @return A string that does not need to be further formatted to be printed
	 */
	public static String signFormatted(int n) {
		// default case 0
		String result = "";
		if (n == 1)
			result = "+";
		else if (n > 1)
			result = "+" + Integer.toString(n);
		else if (n == -1)
			result = "-";
		else if (n < -1)
			result = Integer.toString(n);
		return result;
	}
	
		/**
	 * Adds an integer to a Fraction.
	 *
	 * @param f
	 *            A Fraction
	 * @param n
	 *            An integer
	 * @return f + n as a Fraction
	 */
	public static Fraction add(Fraction f, int n) {
		int numerator = f.getNumerator();
		int denominator = f.getDenominator();

		Fraction result = new Fraction(numerator + n * denominator, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Adds two Fractions.
	 *
	 * @param f1
	 *            A Fraction
	 * @param f2
	 *            The Fraction to be added
	 * @return f1 + f2 as a Fraction
	 */
	public static Fraction add(Fraction f1, Fraction f2) {
		int f1n = f1.getNumerator();
		int f1d = f1.getDenominator();
		int f2n = f2.getNumerator();
		int f2d = f2.getDenominator();
		int gcd = gcd(f1d, f2d);

		int numerator = f1n * (f2d / gcd) + f2n * (f1d / gcd);
		int denominator = f1d * (f2d / gcd);

		Fraction result = new Fraction(numerator, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Adds multiple Fractions.
	 *
	 * @param fractions
	 *            An arbitrary amount of Fractions
	 * @return The sum of all Fractions as a Fraction
	 */
	public static Fraction add(Fraction... fractions) {
		Fraction result = new Fraction(0, 1);
		for (Fraction f : fractions) {
			result = MathOperation.add(result, f);
		}
		return result;
	}

	/**
	 * Adds a List of Fractions.
	 *
	 * @param fractions
	 *            A List of Fractions
	 * @return The sum of all Fractions as a Fraction
	 */
	public static Fraction add(List<Fraction> fractions) {
		Fraction result = new Fraction(0, 1);
		for (Fraction f : fractions) {
			result = MathOperation.add(result, f);
		}
		return result;
	}

	/**
	 * Subtracts an integer from a Fraction.
	 *
	 * @param f
	 *            A Fraction
	 * @param n
	 *            An integer
	 * @return f - n as a Fraction
	 */
	public static Fraction subtract(Fraction f, int n) {
		int numerator = f.getNumerator();
		int denominator = f.getDenominator();

		Fraction result = new Fraction(numerator - n * denominator, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Subtracts a Fraction from an integer.
	 *
	 * @param n
	 *            An integer
	 * @param f
	 *            A Fraction
	 * @return n - f as a Fraction
	 */
	public static Fraction subtract(int n, Fraction f) {
		int numerator = f.getNumerator();
		int denominator = f.getDenominator();

		Fraction result = new Fraction(n * denominator - numerator, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Subtracts two Fractions.
	 *
	 * @param f1
	 *            A Fraction
	 * @param f2
	 *            The fraction to be subtracted
	 * @return f1 - f2 as a Fraction
	 */
	public static Fraction subtract(Fraction f1, Fraction f2) {
		int f1n = f1.getNumerator();
		int f1d = f1.getDenominator();
		int f2n = f2.getNumerator();
		int f2d = f2.getDenominator();
		int gcd = gcd(f1d, f2d);

		int numerator = f1n * (f2d / gcd) - f2n * (f1d / gcd);
		int denominator = f1d * (f2d / gcd);

		Fraction result = new Fraction(numerator, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Subtracts multiple Fractions.
	 *
	 * @param fractions
	 *            An arbitrary amount of Fractions
	 * @return The difference of all Fractions as a Fraction
	 */
	public static Fraction subtract(Fraction... fractions) {
		Fraction result = new Fraction(0, 1);
		for (Fraction f : fractions) {
			result = subtract(result, f);
		}
		return result;
	}

	/**
	 * Subtracts a List of Fractions.
	 *
	 * @param fractions
	 *            A List of Fractions
	 * @return The difference of all Fractions as a Fraction
	 */
	public static Fraction subtract(List<Fraction> fractions) {
		Fraction result = new Fraction(0, 1);
		for (Fraction f : fractions) {
			result = subtract(result, f);
		}
		return result;
	}

	/**
	 * Multiplies a Fraction by an integer.
	 *
	 * @param f
	 *            A Fraction
	 * @param n
	 *            An integer
	 * @return f * n as a Fraction
	 */
	public static Fraction multiply(Fraction f, int n) {
		int numerator = f.getNumerator();
		int denominator = f.getDenominator();
		Fraction result = new Fraction(numerator * n, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Multiplies two Fractions.
	 *
	 * @param f1
	 *            A Fraction
	 * @param f2
	 *            Another Fraction
	 * @return f1 * f2 as a Fraction
	 */
	public static Fraction multiply(Fraction f1, Fraction f2) {
		int f1n = f1.getNumerator();
		int f1d = f1.getDenominator();
		int f2n = f2.getNumerator();
		int f2d = f2.getDenominator();

		int numerator = f1n * f2n;
		int denominator = f1d * f2d;

		Fraction result = new Fraction(numerator, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Multiplies multiple Fractions.
	 *
	 * @param fractions
	 *            An arbitrary amount of Fractions
	 * @return The product of all Fractions as a Fraction
	 */
	public static Fraction multiply(Fraction... fractions) {
		if (fractions.length == 0) {
			return new Fraction(0, 1);
		}

		Fraction result = new Fraction(1, 1);
		for (Fraction f : fractions) {
			result = multiply(f, result);
		}
		return result;
	}

	/**
	 * Multiplies a List of Fractions.
	 *
	 * @param fractions
	 *            A List of Fractions
	 * @return The product all Fractions as a Fraction.
	 */
	public static Fraction multiply(List<Fraction> fractions) {
		if (fractions.size() == 0) {
			return new Fraction(0, 1);
		}

		Fraction result = new Fraction(1, 1);
		for (Fraction f : fractions) {
			result = multiply(f, result);
		}
		return result;
	}

	/**
	 * Divides a Fraction by an integer.
	 *
	 * @param f
	 *            A Fraction
	 * @param n
	 *            An integer
	 * @return f / n as a Fraction
	 */
	public static Fraction divide(Fraction f, int n) {
		int numerator = f.getNumerator();
		int denominator = f.getDenominator();

		Fraction result = new Fraction(numerator, denominator * n);
		simplify(result);
		return result;
	}

	/**
	 * Divides two Fractions.
	 *
	 * @param f1
	 *            A Fraction
	 * @param f2
	 *            A Fraction
	 * @return f1 / f2 as a Fraction
	 */
	public static Fraction divide(Fraction f1, Fraction f2) {
		int f1n = f1.getNumerator();
		int f1d = f1.getDenominator();
		int f2n = f2.getNumerator();
		int f2d = f2.getDenominator();

		int numerator = f1n * f2d;
		int denominator = f1d * f2n;

		Fraction result = new Fraction(numerator, denominator);
		simplify(result);
		return result;
	}

	/**
	 * Divides multiple Fractions.
	 *
	 * @param fractions
	 *            An arbitrary amount of Fractions
	 * @return The quotient of all Fractions as a Fraction
	 */
	public static Fraction divide(Fraction... fractions) {
		if (fractions.length == 0) {
			return new Fraction(0, 1);
		}

		Fraction result = new Fraction(1, 1);
		for (Fraction f : fractions) {
			result = divide(f, result);
		}
		return result;
	}

	/**
	 * Divides a List of Fractions.
	 *
	 * @param fractions
	 *            A List of Fractions.
	 * @return The quotient of all Fractions as a Fraction
	 */
	public static Fraction divide(List<Fraction> fractions) {
		if (fractions.size() == 0) {
			return new Fraction(0, 1);
		}

		Fraction result = new Fraction(1, 1);
		for (Fraction f : fractions) {
			result = divide(f, result);
		}
		return result;
	}

	/**
	 * Raises a Fraction to the nth power.
	 *
	 * @param f
	 *            A fraction
	 * @param power
	 *            An integer
	 * @return f ** n
	 */
	public static Fraction pow(Fraction f, int power) {
		int numerator = (int) Math.pow(f.getNumerator(), power);
		int denominator = (int) Math.pow(f.getDenominator(), power);

		Fraction result = new Fraction(numerator, denominator);
		simplify(result);
		return result;
	}
}
