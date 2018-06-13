import java.util.List;

public class Fraction {
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    private int numerator;
    private int denominator;
    
    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be 0");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public static Fraction valueOf(int n) {
        return new Fraction(n, 1);
    }
    
    public static Fraction parseFraction(String fraction) throws FractionFormatException {
        fraction = fraction.trim();
        if (fraction.matches("^(-?\\d+)/(-?\\d+)$")) {
            int numerator = Integer.parseInt(fraction.split("/")[0]);
            int denominator = Integer.parseInt(fraction.split("/")[1]);
            if (denominator == 0) {
                throw new IllegalArgumentException("Denominator cannot be 0");
            }
            return new Fraction(numerator, denominator);
        } else {
            throw new FractionFormatException("For input string: \"" + fraction + "\"");
        }
    }
    
    @Override
    public String toString() {
        if (denominator == 1) {
            return Integer.toString(numerator);
        }
        if (numerator == 0) {
            return "0";
        } else {
            return numerator + "/" + denominator;
        }
    }
    
    public int compareTo(Fraction fraction) {
        int gcd = Math.gcd(this.simplify().denominator, fraction.simplify().denominator);
        int comparison = this.simplify().numerator * fraction.simplify().denominator / gcd -
                fraction.simplify().numerator * this.simplify().denominator / gcd;
        return Math.compare(comparison, 0);
    }
    
    public boolean isGreaterThan(Fraction fraction) {
        return this.compareTo(fraction) == 1;
    }
    
    public boolean isLessThan(Fraction fraction) {
        return this.compareTo(fraction) == -1;
    }
    
    public boolean isEqualTo(Fraction fraction) {
        return this.compareTo(fraction) == 0;
    }
    
    public boolean isPositive() {
        return this.compareTo(Fraction.ZERO) == 1;
    }
    
    public boolean isNegative() {
        return this.compareTo(Fraction.ZERO) == -1;
    }
    
    public boolean isZero() {
        return this.compareTo(Fraction.ZERO) == 0;
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
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be 0");
        }
        this.denominator = denominator;
    }
    
    public double doubleValue() {
        return (double) numerator / (double) denominator;
    }
    
    public Fraction simplify() {
        int gcd = Math.gcd(this.numerator, this.denominator);
        int numerator = this.numerator / gcd;
        int denominator = this.denominator / gcd;
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        return new Fraction(numerator, denominator);
    }
    
    public Fraction abs() {
        return new Fraction(Math.abs(numerator), Math.abs(denominator)).simplify();
    }
    
    public Fraction add(int n) {
        return new Fraction(numerator + n * denominator, denominator).simplify();
    }
    
    public Fraction add(Fraction fraction) {
        int gcd = Math.gcd(this.denominator, fraction.denominator);
        return new Fraction(this.numerator * fraction.denominator / gcd + fraction.numerator * this.denominator / gcd,
                this.denominator * fraction.denominator / gcd).simplify();
    }
    
    public Fraction add(Fraction... fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.add(fraction);
        }
        return result.simplify();
    }
    
    public Fraction add(List<Fraction> fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.add(fraction);
        }
        return result.simplify();
    }
    
    public Fraction subtract(int n) {
        return new Fraction(numerator - n * denominator, denominator).simplify();
    }
    
    public Fraction subtract(Fraction fraction) {
        int gcd = Math.gcd(this.denominator, fraction.denominator);
        return new Fraction(this.numerator * fraction.denominator / gcd - fraction.numerator * this.denominator / gcd,
                this.denominator * fraction.denominator / gcd).simplify();
    }
    
    public Fraction subtract(Fraction... fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.subtract(fraction);
        }
        return result.simplify();
    }
    
    public Fraction subtract(List<Fraction> fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.subtract(fraction);
        }
        return result.simplify();
    }
    
    public Fraction multiply(int n) {
        return new Fraction(numerator * n, denominator).simplify();
    }
    
    public Fraction multiply(Fraction fraction) {
        return new Fraction(this.numerator * fraction.numerator,
                this.denominator * fraction.denominator).simplify();
    }
    
    public Fraction multiply(Fraction... fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.multiply(fraction);
        }
        return result.simplify();
    }
    
    public Fraction multiply(List<Fraction> fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.multiply(fraction);
        }
        return result.simplify();
    }
    
    public Fraction divide(int n) {
        if (n == 0) {
            throw new FractionArithmeticException("Division by 0");
        }
        return new Fraction(numerator, denominator * n).simplify();
    }
    
    public Fraction divide(Fraction fraction) {
        if (fraction.numerator == 0) {
            throw new FractionArithmeticException("Division by 0");
        }
        return new Fraction(this.numerator * fraction.denominator,
                this.denominator * fraction.numerator).simplify();
    }
    
    public Fraction divide(Fraction... fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.divide(fraction);
        }
        return result.simplify();
    }
    
    public Fraction divide(List<Fraction> fractions) {
        Fraction result = this;
        for (Fraction fraction : fractions) {
            result = result.divide(fraction);
        }
        return result.simplify();
    }
    
    public Fraction negate() {
        return new Fraction(-numerator, denominator).simplify();
    }
    
    public Fraction pow(int n) {
        return new Fraction(Math.pow(numerator, n), Math.pow(denominator, n)).simplify();
    }
    
    public Fraction reciprocal() {
        if (numerator == 0) {
            throw new FractionArithmeticException("Reciprocal is undefined");
        }
        return new Fraction(denominator, numerator).simplify();
    }
}