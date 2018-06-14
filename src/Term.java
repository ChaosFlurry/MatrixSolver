import java.util.Map;

public class Term {
    private Fraction coefficient;
    private Map<String, Integer> variablePowerMap;
    // TODO implement logarithms and trig functions
    
    public Term(Fraction coefficient, Map<String, Integer> variablePowerMap) {
        this.coefficient = coefficient;
        this.variablePowerMap = variablePowerMap;
    }
    
    public Fraction getCoefficient() {
        return coefficient;
    }
    
    public void setCoefficient(Fraction coefficient) {
        this.coefficient = coefficient;
    }
    
    public Map<String, Integer> getVariablePowerMap() {
        return variablePowerMap;
    }
    
    public void setVariablePowerMap(Map<String, Integer> variablePowerMap) {
        this.variablePowerMap = variablePowerMap;
    }
    
    public int getNumberOfVariables() {
        return variablePowerMap.keySet().size();
    }
    
    public int getDegree() {
        int degree = 0;
        for (String variable : variablePowerMap.keySet()) {
            degree += variablePowerMap.get(variable);
        }
        return degree;
    }
    
    @Override
    public String toString() {
        String coefficientString = "";
        String variablePowerMapString = "";
        
        if (coefficient.isZero()) {
            return "0";
        }
        
        for (String variable : variablePowerMap.keySet()) {
            int power = variablePowerMap.get(variable);
            if (power == 1) {
                if (variable.contains("+") || variable.contains("-")) {
                    variablePowerMapString = "(" + variable + ")";
                } else {
                    variablePowerMapString += variable;
                }
            } else if (power != 0) {
                if (variable.contains("+") || variable.contains("-")) {
                    variablePowerMapString = "(" + variable + ")"+ "^" + power;
                } else {
                    variablePowerMapString += variable + "^" + power;
                }
            }
        }
        
        if (variablePowerMapString.equals("")) {
            coefficientString += coefficient;
        } else {
            if (coefficient.isEqualTo(Fraction.ONE.negate())) {
                coefficientString += "-";
            } else if (!coefficient.isEqualTo(Fraction.ONE)) {
                coefficientString += coefficient;
            }
        }
        return coefficientString + variablePowerMapString;
    }
    
    public Term negate() {
        return new Term(coefficient.negate(), variablePowerMap);
    }
}
