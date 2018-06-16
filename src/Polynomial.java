import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private List<Term> terms;
    
    public Polynomial(List<Term> terms) {
        this.terms = terms;
    }
    
    public static Polynomial parsePolynomial(String text) {
        List<Term> terms = new ArrayList<>();
        
        // TODO fix regex - x^2(x-1) - 2 is assumed to be a coefficient (quick fix in expand())
        // either safeguard dangling powers with brackets or enforce stricter notation with astrix to indicate multiplication
        Pattern polynomialFormatPattern = Pattern.compile("^((-?\\d+|-?\\d+/\\d+)?([a-z](\\^\\d+)?)*)([+-](\\d+|\\d+/\\d+)?([a-z](\\^\\d+)?)*)*$");
        Matcher polynomialFormatMatcher = polynomialFormatPattern.matcher(text);
        
        if (polynomialFormatMatcher.matches()) {
            // group 1 - sign (optional - default positive)
            // group 2 - coefficient (optional)
            // group 3 - variable (optional - default blank)
            // group 4 - power (optional - default 1)
            Pattern polynomialTermsPattern = Pattern.compile("(-)?(\\d+/\\d+|\\d+)?((?:[a-z](?:\\^\\d+)?)+)?");
            Matcher polynomialTermsMatcher = polynomialTermsPattern.matcher(text);
            
            // group 1 - indv
            // group 3 -
                // if group 2 is not null
                // add coefficient
            
            while (polynomialTermsMatcher.find()) {
                if (polynomialTermsMatcher.group(0) != null) {
                    if (polynomialTermsMatcher.group(0).equals("-")) {
                        throw new IllegalArgumentException("Invalid polynomial format");
                    }
                    
                    Fraction coefficient = Fraction.ZERO;
                    String variable = "";
                    int power = 0;
                    Map<String, Integer> variablePowerMap = new HashMap<>();
                    
                    if (polynomialTermsMatcher.group(2) != null) {
                        if (polynomialTermsMatcher.group(2).matches("\\d+")) {
                            coefficient = Fraction.valueOf(Integer.parseInt(polynomialTermsMatcher.group(2)));
                        } else if (polynomialTermsMatcher.group(2).matches("\\d+/\\d+")) {
                            coefficient = Fraction.parseFraction(polynomialTermsMatcher.group(2));
                        }
                    }
    
                    if (polynomialTermsMatcher.group(1) != null) {
                        if (polynomialTermsMatcher.group(1).equals("-")) {
                            coefficient = coefficient.negate();
                        }
                    }
                    
                    if (polynomialTermsMatcher.group(3) != null) {
                        Pattern variablePowerPattern = Pattern.compile("([a-z])(?:\\^(\\d+))?");
                        Matcher variablePowerMatcher = variablePowerPattern.matcher(polynomialTermsMatcher.group(3));
    
                        while (variablePowerMatcher.find()) {
                            if (variablePowerMatcher.group(2) != null) {
                                power = Integer.parseInt(variablePowerMatcher.group(2));
                            } else {
                                power = 1;
                            }
                            variable = variablePowerMatcher.group(1);
                            variablePowerMap.put(variable, power);
                        }
                        Term term = new Term(coefficient, variablePowerMap);
                        terms.add(term);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid polynomial format");
        }
        return new Polynomial(terms).simplify();
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
        Polynomial copy = this.copy();
        List<Term> newTerms = new ArrayList<>();
        for (int i = 0; i < copy.getTerms().size(); i++) {
            Term newTerm = new Term(copy.getTerms().get(i).getCoefficient().multiply(constant), copy.getTerms().get(i).getVariablePowerMap());
            newTerms.add(newTerm);
        }
        return new Polynomial(newTerms);
    }
    
    public Polynomial multiply(Fraction constant) {
        Polynomial copy = this.copy();
        List<Term> newTerms = new ArrayList<>();
        for (int i = 0; i < copy.getTerms().size(); i++) {
            Term newTerm = new Term(copy.getTerms().get(i).getCoefficient().multiply(constant), copy.getTerms().get(i).getVariablePowerMap());
            newTerms.add(newTerm);
        }
        return new Polynomial(newTerms);
    }
    
    public Polynomial multiply(Polynomial polynomial) {
        Polynomial copy = this.copy();
        List<Term> newTerms = new ArrayList<>();
        
        for (Term term1 : copy.terms) {
            for (Term term2 : polynomial.terms) {
                Fraction newCoefficient = term1.getCoefficient().multiply(term2.getCoefficient());
                Map<String, Integer> newVariablePowerMap = new HashMap<>(term1.getVariablePowerMap());
                term2.getVariablePowerMap().forEach((v, p) -> newVariablePowerMap.merge(v, p, (v1, v2) -> v1 + v2));
                newTerms.add(new Term(newCoefficient, newVariablePowerMap));
            }
        }
        return new Polynomial(newTerms).simplify();
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
        String polynomialString = this.copy().toString();
        
        // quick fix
        // TODO find a better fix (and test)
        Pattern danglingPowerPattern = Pattern.compile("(?:\\))?((?:\\d+(?:/\\d+)?)?[a-z]\\^\\d+)(?:\\()");
        Matcher danglingPowerMatcher = danglingPowerPattern.matcher(polynomialString);
        while (danglingPowerMatcher.find()) {
            polynomialString = polynomialString.substring(0, danglingPowerMatcher.start(1)) + "(" + danglingPowerMatcher.group(1) + ")" + polynomialString.substring(danglingPowerMatcher.end(1));
        }
        
        Pattern polynomialWithFractionalCoefficientPattern = Pattern.compile(".*((?:(\\d+/\\d+)\\(((?:[a-z]|[0-9]|\\+|-|\\^)+?)\\))(?:\\^(\\d+))?).*");
        Matcher polynomialWithFractionalCoefficientMatcher = polynomialWithFractionalCoefficientPattern.matcher(polynomialString);
        Pattern polynomialWithCoefficientPattern = Pattern.compile(".*((?:(\\d+)\\(((?:[a-z]|[0-9]|\\+|-|\\^)+?)\\))(?:\\^(\\d+))?).*");
        Matcher polynomialWithCoefficientMatcher = polynomialWithCoefficientPattern.matcher(polynomialString);
        Pattern polynomialWithoutCoefficientPattern = Pattern.compile(".*((?:\\(((?:[a-z]|[0-9]|\\+|-|\\^)+?)\\))(?:\\^(\\d+))?).*");
        Matcher polynomialWithoutCoefficientMatcher = polynomialWithoutCoefficientPattern.matcher(polynomialString);
        
        int matchStart;
        int matchEnd;
        Fraction polynomialCoefficient;
        int polynomialPower = 1;
        String termString;
        
        while (polynomialWithFractionalCoefficientMatcher.matches() ||
                polynomialWithCoefficientMatcher.matches() ||
                polynomialWithoutCoefficientMatcher.matches()) {
            
            if (polynomialWithFractionalCoefficientMatcher.matches()) {
                polynomialCoefficient = Fraction.parseFraction(polynomialWithFractionalCoefficientMatcher.group(2));
                termString = polynomialWithFractionalCoefficientMatcher.group(3);
                if (polynomialWithFractionalCoefficientMatcher.group(4) != null) {
                    polynomialPower = Integer.parseInt(polynomialWithFractionalCoefficientMatcher.group(4));
                }
                matchStart = polynomialWithFractionalCoefficientMatcher.start(1);
                matchEnd = polynomialWithFractionalCoefficientMatcher.end(1);
            } else if (polynomialWithCoefficientMatcher.matches()) {
                polynomialCoefficient = Fraction.valueOf(Integer.parseInt(polynomialWithCoefficientMatcher.group(2)));
                termString = polynomialWithCoefficientMatcher.group(3);
                if (polynomialWithCoefficientMatcher.group(4) != null) {
                    polynomialPower = Integer.parseInt(polynomialWithCoefficientMatcher.group(4));
                }
                matchStart = polynomialWithCoefficientMatcher.start(1);
                matchEnd = polynomialWithCoefficientMatcher.end(1);
            } else {
                polynomialCoefficient = Fraction.ONE;
                termString = polynomialWithoutCoefficientMatcher.group(2);
                if (polynomialWithoutCoefficientMatcher.group(3) != null) {
                    polynomialPower = Integer.parseInt(polynomialWithoutCoefficientMatcher.group(3));
                }
                matchStart = polynomialWithoutCoefficientMatcher.start(1);
                matchEnd = polynomialWithoutCoefficientMatcher.end(1);
            }
            
            List<Term> terms = new ArrayList<>();
            Pattern completeTermPattern = Pattern.compile("(-)?(\\d+/\\d+|\\d+)?((?:[a-z](?:\\^\\d+)?))?");
            Matcher completeTermMatcher = completeTermPattern.matcher(termString);
            while (completeTermMatcher.find()) {
                if (completeTermMatcher.group(0).length() > 0) {
                    Fraction termCoefficient = Fraction.ONE;
                    Map<String, Integer> termVariablePowerMap = new HashMap<>();
                    
                    if (completeTermMatcher.group(2) != null) {
                        if (completeTermMatcher.group(2).contains("/")) {
                            termCoefficient = Fraction.parseFraction(completeTermMatcher.group(2));
                        } else {
                            termCoefficient = Fraction.valueOf(Integer.parseInt(completeTermMatcher.group(2)));
                        }
                    }
                    
                    if (completeTermMatcher.group(3) != null) {
                        if (completeTermMatcher.group(3).matches("\\d+")) {
                            termVariablePowerMap.put("", 0);
                            termCoefficient = Fraction.valueOf(Integer.parseInt(completeTermMatcher.group(3)));
                        } else {
                            String variable;
                            int power = 1;
                            if (completeTermMatcher.group(3).contains("^")) {
                                variable = completeTermMatcher.group(3).split("\\^")[0];
                                power = Integer.parseInt(completeTermMatcher.group(3).split("\\^")[1]);
                            } else {
                                variable = completeTermMatcher.group(3);
                            }
                            termVariablePowerMap.put(variable, power);
                        }
                    }
                    
                    if (completeTermMatcher.group(1) != null) {
                        if (completeTermMatcher.group(1).equals("-")) {
                            termCoefficient = termCoefficient.multiply(-1);
                        }
                    }
                    terms.add(new Term(termCoefficient, termVariablePowerMap));
                }
            }
            
            Polynomial expansion = new Polynomial(terms)
                    .power(polynomialPower)
                    .multiply(polynomialCoefficient);
            String charBefore;
            String charAfter;
            if (matchStart - 1 < 0) {
                charBefore = null;
            } else {
                charBefore = Character.toString(polynomialString.charAt(matchStart - 1));
            }
            
            if (matchEnd > polynomialString.length() - 1) {
                charAfter = null;
            } else {
                charAfter = Character.toString(polynomialString.charAt(matchEnd));
            }
            
            if ((charBefore == null || charBefore.equals("+") || charBefore.equals("-") || charBefore.equals("(")) &&
                    (charAfter == null || charAfter.equals("+") || charAfter.equals("-") || charAfter.equals(")"))) {
                polynomialString = polynomialString.substring(0, matchStart) + expansion.toString() + polynomialString.substring(matchEnd);
            } else {
                polynomialString = polynomialString.substring(0, matchStart) + "[" + expansion.toString() + "]" + polynomialString.substring(matchEnd);
            }
            System.out.println("p str: " + polynomialString);
            Pattern multiplyPattern = Pattern.compile("(\\[(.+?)\\]\\[(.+?)\\])");
            Matcher multiplyMatcher = multiplyPattern.matcher(polynomialString);
            
            while (multiplyMatcher.find()) {
                System.out.println("g2: " + multiplyMatcher.group(2));
                System.out.println("g3: " + multiplyMatcher.group(3));
                Polynomial p1 = Polynomial.parsePolynomial(multiplyMatcher.group(2));
                Polynomial p2 = Polynomial.parsePolynomial(multiplyMatcher.group(3));

                Polynomial multiplied = p1.multiply(p2);
                
                matchStart = multiplyMatcher.start(1);
                matchEnd = multiplyMatcher.end(1);
                
                if (matchStart - 1 < 0) {
                    charBefore = null;
                } else {
                    charBefore = Character.toString(polynomialString.charAt(matchStart - 1));
                }
                
                if (matchEnd > polynomialString.length() - 1) {
                    charAfter = null;
                } else {
                    charAfter = Character.toString(polynomialString.charAt(matchEnd));
                }
                
                
                if ((charBefore == null || charBefore.equals("+") || charBefore.equals("-") || charBefore.equals("(") || charBefore.equals("[")) &&
                        (charAfter == null || charAfter.equals("+") || charAfter.equals("-") || charAfter.equals(")") || charAfter.equals("]"))) {
                    polynomialString = polynomialString.substring(0, matchStart) + multiplied.toString() + polynomialString.substring(matchEnd);
                } else {
                    polynomialString = polynomialString.substring(0, matchStart) + "[" + multiplied.toString() + "]" + polynomialString.substring(matchEnd);
                }
            }
            polynomialWithCoefficientMatcher.reset(polynomialString);
            polynomialWithFractionalCoefficientMatcher.reset(polynomialString);
            polynomialWithoutCoefficientMatcher.reset(polynomialString);
        }
        return Polynomial.parsePolynomial(polynomialString);
    }
    
    public Polynomial factor() {
        // TODO Figure out how to factor higher degree polynomials
        return null;
    }
    
    public Polynomial simplify() {
        Polynomial copy = this.copy();
        List<Term> newTerms = new ArrayList<>();
        
        for (Term term : copy.getTerms()) {
            if (term.getVariablePowerMap().size() > 1 && term.getVariablePowerMap().keySet().contains("")) {
                term.getVariablePowerMap().remove("");
            }
        }
        
        for (Term t1 : copy.getTerms()) {
            boolean termMatchesVariables = false;
            for (Term t2 : newTerms) {
                Map<String, Integer> t1VariablePowerMap = t1.getVariablePowerMap();
                Map<String, Integer> t2VariablePowerMap = t2.getVariablePowerMap();
                if (t1VariablePowerMap.equals(t2VariablePowerMap)) {
                    termMatchesVariables = true;
                    break;
                }
            }
            if (termMatchesVariables) {
                for (int i = 0; i < newTerms.size(); i++) {
                    if (t1.getVariablePowerMap().equals(newTerms.get(i).getVariablePowerMap())) {
                        newTerms.set(i, new Term(t1.getCoefficient().add(newTerms.get(i).getCoefficient()),
                                t1.getVariablePowerMap()));
                    }
                }
            } else {
                newTerms.add(t1);
            }
        }
        newTerms.removeIf(term -> term.getCoefficient().isZero());
        return new Polynomial(newTerms).sort();
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
