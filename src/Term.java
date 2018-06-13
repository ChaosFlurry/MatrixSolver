public class Term {
    private Fraction coefficient;
    private String variable;
    private int power;
    // TODO implement logarithms and trig functions
    
    public Term(Fraction coefficient, String variable, int power) {
        this.coefficient = coefficient;
        this.variable = variable;
        this.power = power;
    }
    
    public Fraction getCoefficient() {
        return coefficient;
    }
    
    public void setCoefficient(Fraction coefficient) {
        this.coefficient = coefficient;
    }
    
    public String getVariable() {
        return variable;
    }
    
    public void setVariable(String variable) {
        this.variable = variable;
    }
    
    public int getPower() {
        return power;
    }
    
    public void setPower(int power) {
        this.power = power;
    }
    
    @Override
    public String toString() {
        String string = "";
        if (coefficient.isZero()) {
            return "0";
        } else if (coefficient.isEqualTo(Fraction.ONE.negate())) {
            string += "-";
            if (power == 0) {
                string += "1";
            } else if (power == 1) {
                string += variable;
            } else {
                string += variable + "^" + power;
            }
        } else if (coefficient.isEqualTo(Fraction.ONE)) {
            if (power == 0) {
                string += "1";
            } else if (power == 1) {
                string += variable;
            } else {
                string += variable + "^" + power;
            }
        } else {
            string += coefficient;
            if (power == 1) {
                string += variable;
            } else if (power != 0) {
                string += variable + "^" + power;
            }
        }
        return string;
    }
}
