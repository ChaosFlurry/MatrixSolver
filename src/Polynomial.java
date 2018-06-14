import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private List<Term> terms;
    
    public Polynomial(List<Term> terms) {
        this.terms = terms;
    }
    
    public List<Term> getTerms() {
        return terms;
    }
    
    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }
    
    public Polynomial copy() {
        List<Term> copy = new ArrayList<>();
        for (Term term : terms) {
            copy.add(term);
        }
        return new Polynomial(copy);
    }
    
    public Polynomial add(Polynomial polynomial) {
        List<Term> newTerms = new ArrayList<>(this.terms);
        newTerms.addAll(polynomial.getTerms());
        return new Polynomial(newTerms).simplify().sort();
    }
    
    public Polynomial subtract(Polynomial polynomial) {
        List<Term> newTerms = new ArrayList<>(this.terms);
        for (Term term : polynomial.getTerms()) {
            terms.add(term.negate());
        }
        return new Polynomial(newTerms).simplify().sort();
    }
    
    public Polynomial multiply(int constant) {
        Polynomial copy = this;
        for (int i = 0; i < copy.getTerms().size(); i++) {
            copy.getTerms().get(i).setCoefficient(copy.getTerms().get(i).getCoefficient().multiply(constant));
        }
        return copy;
    }
    
    public Polynomial multiply(Fraction constant) {
        Polynomial copy = this.copy();
        for (int i = 0; i < copy.getTerms().size(); i++) {
            copy.getTerms().get(i).setCoefficient(copy.getTerms().get(i).getCoefficient().multiply(constant));
        }
        return copy;
    }
    
    public Polynomial multiply(Polynomial polynomial) {
        Polynomial copy = this.copy();
        List<Term> newTerms = new ArrayList<>();
        Term newTerm;
        Map<String, Integer> newVariablePowerMap;
        for (Term term1 : this.terms) {
            for (Term term2 : polynomial.terms) {
                Fraction newCoefficient = term1.getCoefficient().multiply(term2.getCoefficient());
                
                newVariablePowerMap = new HashMap<>();
                for (String v : term1.getVariablePowerMap().keySet()) {
                    newVariablePowerMap.put(v, term1.getVariablePowerMap().get(v));
                }
                
                for (String v : term2.getVariablePowerMap().keySet()) {
                    if (!v.equals("")) {
                        if (term1.getVariablePowerMap().containsKey(v)) {
                            int newPower = newVariablePowerMap.get(v) + term2.getVariablePowerMap().get(v);
                            newVariablePowerMap.put(v, newPower);
                        } else {
                            newVariablePowerMap.put(v, term2.getVariablePowerMap().get(v));
                        }
                    }
                }
                newTerm = new Term(newCoefficient, newVariablePowerMap);
                newTerms.add(newTerm);
                
            }
        }
        return new Polynomial(newTerms).simplify().sort();
    }
    
    public Polynomial divide(Polynomial polynomial) {
        return null;
    }
    
    public Polynomial power(int power) {
        if (power == 0) {
            List<Term> newTerms = new ArrayList<>();
            // TODO check for undefined
            Map<String, Integer> newVariablePowerMap = new HashMap<>();
            newVariablePowerMap.put("x", 0);
            newTerms.add(new Term(Fraction.ONE, newVariablePowerMap));
            return new Polynomial(newTerms);
        } else if (power == 1) {
            return this;
        } else {
            Polynomial original = this.copy();
            Polynomial result = original.copy();
            for (int i = 0; i < power - 1; i++) {
                result = result.multiply(original);
            }
            return result.simplify().sort();
        }
    }
    
    public Polynomial expand() {
        Polynomial copy = this;
        Pattern polynomialWithFractionalCoefficientPattern = Pattern.compile(".*((?:(\\d+/\\d+)\\((.+?)\\))(?:\\^(\\d+))?).*");
        Matcher polynomialWithFractionalCoefficientMatcher = polynomialWithFractionalCoefficientPattern.matcher(copy.toString());
        Pattern polynomialWithCoefficientPattern = Pattern.compile(".*((?:(\\d+)\\((.+?)\\))(?:\\^(\\d+))?).*");
        Matcher polynomialWithCoefficientMatcher = polynomialWithCoefficientPattern.matcher(copy.toString());
        Pattern polynomialWithoutCoefficientPattern = Pattern.compile(".*((?:\\((.+?)\\))(?:\\^(\\d+))?).*");
        Matcher polynomialWithoutCoefficientMatcher = polynomialWithoutCoefficientPattern.matcher(copy.toString());
        
        while (polynomialWithFractionalCoefficientMatcher.matches() ||
                polynomialWithCoefficientMatcher.matches() ||
                polynomialWithoutCoefficientMatcher.matches()) {
            Fraction polynomialCoefficient;
            int polynomialPower = 1;
            String termString;
            if (polynomialWithFractionalCoefficientMatcher.matches()) {
                polynomialCoefficient = Fraction.parseFraction(polynomialWithFractionalCoefficientMatcher.group(2));
                termString = polynomialWithFractionalCoefficientMatcher.group(3);
                if (polynomialWithFractionalCoefficientMatcher.group(4) != null) {
                    polynomialPower = Integer.parseInt(polynomialWithFractionalCoefficientMatcher.group(4));
                }
            } else if (polynomialWithCoefficientMatcher.matches()) {
                polynomialCoefficient = Fraction.valueOf(Integer.parseInt(polynomialWithCoefficientMatcher.group(2)));
                termString = polynomialWithCoefficientMatcher.group(3);
                if (polynomialWithCoefficientMatcher.group(4) != null) {
                    polynomialPower = Integer.parseInt(polynomialWithCoefficientMatcher.group(4));
                }
            } else {
                polynomialCoefficient = Fraction.ONE;
                termString = polynomialWithoutCoefficientMatcher.group(2);
                if (polynomialWithoutCoefficientMatcher.group(3) != null) {
                    polynomialPower = Integer.parseInt(polynomialWithoutCoefficientMatcher.group(3));
                }
            }
            
            List<Term> terms = new ArrayList<>();
            Pattern completeTermPattern = Pattern.compile("(-)?(\\d+/\\d+|\\d+)?((?:[a-z](?:\\^\\d+)?|\\d+)+)");
            Matcher completeTermMatcher = completeTermPattern.matcher(termString);
            
            while (completeTermMatcher.find()) {
                Fraction termCoefficient = Fraction.ONE;
                Map<String, Integer> termVariablePowerMap = new HashMap<>();
                
                if (completeTermMatcher.group(2) != null) {
                    if (completeTermMatcher.group(2).contains("/")) {
                        termCoefficient = Fraction.parseFraction(completeTermMatcher.group(2));
                    } else {
                        termCoefficient = Fraction.valueOf(Integer.parseInt(completeTermMatcher.group(2)));
                    }
                }
                
                if (completeTermMatcher.group(1) != null) {
                    if (completeTermMatcher.group(1).equals("-")) {
                        termCoefficient = termCoefficient.negate();
                    }
                }
                
                Pattern termVariablePowerPattern = Pattern.compile("([a-z])(?:\\^(\\d+))?");
                Matcher termVariablePowerMatcher = termVariablePowerPattern.matcher(completeTermMatcher.group(3));
                
                boolean isConstant = true;
                
                String variable;
                int power;
                while (termVariablePowerMatcher.find()) {
                    isConstant = false;
                    variable = termVariablePowerMatcher.group(1);
                    power = 1;
                    if (termVariablePowerMatcher.group(2) != null) {
                        power = Integer.parseInt(termVariablePowerMatcher.group(2));
                    }
                    termVariablePowerMap.put(variable, power);
                    terms.add(new Term(termCoefficient, termVariablePowerMap));
                }
                
                if (isConstant) {
                    variable = "";
                    power = 0;
                    Map<String, Integer> constantPowerMap = new HashMap<>();
                    constantPowerMap.put(variable, power);
                    terms.add(new Term(termCoefficient, constantPowerMap));
                }
            }
            Polynomial expansion = new Polynomial(terms);
            System.out.println("expansion1: " + expansion);
            System.out.println("poly power: " + polynomialPower);
            System.out.println("poly cof: " + polynomialCoefficient);
            expansion = expansion.power(polynomialPower).multiply(polynomialCoefficient);
            String expansionString = expansion.toString();
            System.out.println("expansion: " + expansionString);
            break;
            // TODO replace original string with expanded polynomial
            // TODO fix multiplying constant in
        }
        // split by sign
        // separate powers
        // multiply
        // remove brackets (don't remove brackets)
        // replace
        
        /*
        (x^2+2)^2 ->
        x^2, 2; mult 2
        cof 1 var x power 2, cof 2 var "" pow 0
        multiply and add terms
        
        ...+((x+1)^0)^2+...
        simple case
        
        x((x+1)^0)^2
        x(1)^2
        x1
        
        ((x+1)^2+2)^2 ->
        (x^2+2x+1+2)^2 ->
        x^4... 4x+2+4
        
        (xy^2+1)^2 ->
        (x^2y^4+2xy^2+2)
         */
        
        return null;
    }
    
    public Polynomial factor() {
        // TODO Figure out how to factor higher degree polynomials
        return null;
    }
    
    public Polynomial simplify() {
        List<Term> copies = new ArrayList<>(terms);
        for (Term term : copies) {
            if (term.getVariablePowerMap().keySet().size() > 1) {
                term.getVariablePowerMap().remove("");
            }
        }
        
        for (int i = 0; i < copies.size(); i++) {
            Term term1 = copies.get(i);
            for (int j = i + 1; j < copies.size(); j++) {
                Term term2 = copies.get(j);
                if (term1.getVariablePowerMap().equals(term2.getVariablePowerMap())) {
                    Fraction newCoefficient = term1.getCoefficient().add(term2.getCoefficient());
                    copies.set(i, new Term(newCoefficient, term1.getVariablePowerMap()));
                    copies.set(j, new Term(Fraction.ZERO, term2.getVariablePowerMap()));
                } else if (term1.getDegree() == 0 && term2.getDegree() == 0) {
                    Fraction newCoefficient = term1.getCoefficient().add(term2.getCoefficient());
                    copies.set(i, new Term(newCoefficient, term1.getVariablePowerMap()));
                    copies.set(j, new Term(Fraction.ZERO, term2.getVariablePowerMap()));
                }
            }
        }
        copies.removeIf(copy -> copy.getCoefficient().isZero());
        return new Polynomial(copies);
    }
    
    public Polynomial sort() {
        List<Term> copies = terms;
        copies.sort(Comparator.comparing(Term::getDegree).reversed().thenComparing(Term::getNumberOfVariables));
        return new Polynomial(copies);
    }
    
    @Override
    public String toString() {
        Polynomial copy = this.sort();
        if (copy.getTerms().size() == 0) {
            return "0";
        } else if (copy.getTerms().size() == 1) {
            return copy.getTerms().get(0).toString();
        } else {
            StringBuilder polynomialStringBuilder = new StringBuilder();
            polynomialStringBuilder.append(copy.getTerms().get(0).toString());
            for (int i = 1; i < copy.getTerms().size(); i++) {
                if (!copy.getTerms().get(i).getCoefficient().isNegative()) {
                    polynomialStringBuilder.append("+");
                }
                polynomialStringBuilder.append(copy.getTerms().get(i).toString());
            }
            return polynomialStringBuilder.toString();
        }
    }
}
