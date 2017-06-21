import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Fraction {
	private int numerator;
	private int denominator;
	
	public static final Fraction ZERO = new Fraction(0, 1);
	public static final Fraction ONE = new Fraction(1, 1);
	
	public Fraction(int numerator, int denominator) {
		if (denominator == 0) {
			throw new IllegalArgumentException("Denominator is 0");
		}
		if (denominator < 0) {
			numerator *= -1;
			denominator *= -1;
		}
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public static Fraction valueOf(int n) {
		return new Fraction(n, 1);
	}

	@Override
	public String toString() {
		if (isUndefined()) {
			return "Undefined";
		}
		if (denominator == 1)
			return Integer.toString(numerator);
		return (numerator == 0) ? "0" : numerator + "/" + denominator;
	}

	public boolean equals(Fraction f) {
		return equals(this, f);
	}

	public static boolean equals(Fraction f1, Fraction f2) {
		int f1n = f1.simplify().getNumerator();
		int f2n = f2.simplify().getNumerator();
		int f1d = f1.simplify().getDenominator();
		int f2d = f2.simplify().getDenominator();
		
		//if numerators and denominators match
		return f1n == f2n && f1d == f2d;
	}

	public Fraction simplify() {
		return simplify(this);
	}

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
		int gcd = MathOperation.gcd(numerator, denominator);

		numerator = (numerator / gcd);
		denominator = (denominator / gcd);
		if (denominator < 0) {
			numerator *= -1;
			denominator *= -1;
		}
		return new Fraction(numerator, denominator);
	}

	public static Fraction parseFraction(String fraction) throws FractionFormatException {
		fraction = fraction.trim();
		Pattern numberPattern = Pattern.compile("^(-?\\d+)$");
		Pattern fractionPattern = Pattern.compile("^(-?\\d+)/(-?\\d+)$");
		Matcher numberMatcher = numberPattern.matcher(fraction);
		Matcher fractionMatcher = fractionPattern.matcher(fraction);

		if (numberMatcher.matches()) {
			return new Fraction(Integer.parseInt(numberMatcher.group(1)), 1);
		} else if (fractionMatcher.matches()) {
			return new Fraction(Integer.parseInt(fractionMatcher.group(1)), Integer.parseInt(fractionMatcher.group(2)));
		} else {
			throw new FractionFormatException("For input string: \"" + fraction + "\"");
		}
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}

	public boolean isUndefined() {
		return denominator == 0;
	}

	public double doubleValue() {
		return (double) numerator / denominator;
	}

	public Fraction add(int n) {
		return add(this, n);
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
		result = Fraction.simplify(result);
		return result;
	}

	public Fraction add(Fraction f) {
		return add(this, f);
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
		int gcd = MathOperation.gcd(f1d, f2d);

		int numerator = f1n * (f2d / gcd) + f2n * (f1d / gcd);
		int denominator = f1d * (f2d / gcd);

		Fraction result = new Fraction(numerator, denominator);
		result = Fraction.simplify(result);
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
			result = Fraction.add(result, f);
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
			result = Fraction.add(result, f);
		}
		return result;
	}

	public Fraction subtract(int n) {
		return subtract(this, n);
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
		result = Fraction.simplify(result);
		return result;
	}

	public Fraction subtract(Fraction f) {
		return subtract(this, f);
	}

	/**
	 * Subtracts a Fraction from another Fraction.
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
		int gcd = MathOperation.gcd(f1d, f2d);

		int numerator = f1n * (f2d / gcd) - f2n * (f1d / gcd);
		int denominator = f1d * (f2d / gcd);

		Fraction result = new Fraction(numerator, denominator);
		result = Fraction.simplify(result);
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

	public Fraction multiply(int n) {
		return multiply(this, n);
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
		result = Fraction.simplify(result);
		return result;
	}

	public Fraction multiply(Fraction f) {
		return multiply(this, f);
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
		result = Fraction.simplify(result);
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

	public Fraction divide(int n) {
		return divide(this, n);
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
		if (n == 0) {
			throw new ArithmeticException("Division by 0");
		}

		int numerator = f.getNumerator();
		int denominator = f.getDenominator();

		Fraction result = new Fraction(numerator, denominator * n);
		result = Fraction.simplify(result);
		return result;
	}

	public Fraction divide(Fraction f) {
		return divide(this, f);
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

		if (denominator == 0) {
			throw new ArithmeticException("Division by 0");
		}

		Fraction result = new Fraction(numerator, denominator);
		result = Fraction.simplify(result);
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
		for (int i = 1; i < fractions.length; i++) {
			if (fractions[i].getNumerator() == 0) {
				throw new ArithmeticException("Division by 0");
			}
		}

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
		for (int i = 1; i < fractions.size(); i++) {
			if (fractions.get(i).getNumerator() == 0) {
				throw new ArithmeticException("Division by 0");
			}
		}

		if (fractions.size() == 0) {
			return new Fraction(0, 1);
		}

		Fraction result = new Fraction(1, 1);
		for (Fraction f : fractions) {
			result = divide(f, result);
		}
		return result;
	}

	public Fraction pow(int n) {
		return pow(this, n);
	}

	/**
	 * Raises a Fraction to the nth power.
	 * 
	 * @param f
	 *            A fraction
	 * @param n
	 *            An integer
	 * @return f ** n
	 */
	public static Fraction pow(Fraction f, int n) {
		int numerator = (int) Math.pow(f.getNumerator(), n);
		int denominator = (int) Math.pow(f.getDenominator(), n);

		Fraction result = new Fraction(numerator, denominator);
		result = Fraction.simplify(result);
		return result;
	}
	
	public Fraction reciprocal() {
		return reciprocal(this);
	}
	
	public static Fraction reciprocal(Fraction f) {
		if (f.simplify().equals(Fraction.ZERO)) {
			throw new ArithmeticException("division by 0");
		}
		return new Fraction(f.simplify().getDenominator(), f.simplify().getNumerator());
	}
}