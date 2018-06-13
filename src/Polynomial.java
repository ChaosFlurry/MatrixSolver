import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    
    public Polynomial add(Polynomial polynomial) {
        List<Term> newTerms = new ArrayList<>(this.terms);
        newTerms.addAll(polynomial.getTerms());
        return new Polynomial(newTerms);
    }
    
    public Polynomial subtract(Polynomial polynomial) {
        List<Term> newTerms = new ArrayList<>(this.terms);
        for (Term term : polynomial.getTerms()) {
            terms.add(term.negate());
        }
        return new Polynomial(newTerms);
    }
    
    public Polynomial multiply(Polynomial polynomial) {
        List<Term> newTerms = new ArrayList<>();
        for (int i = 0; i < this.terms.size(); i++) {
            for (int j = 0; j < polynomial.getTerms().size(); j++) {
                newTerms.add(new Term(
                        this.terms.get(i).getCoefficient(),
                        this.terms.get(i).getVariable(),
                        this.terms.get(i).getPower() + polynomial.getTerms().get(j).getPower()));
                // TODO Remove variable field from Term (making everything single variabled)
                // TODO Combine sort and simplify
            }
        }
        return new Polynomial(newTerms);
    }
    
    public Polynomial divide(Polynomial polynomial) {
    
    }
    
    public Polynomial expand() {
    
    }
    
    public Polynomial factor() {
        // TODO Figure out how to factor higher degree polynomials
        return null;
    }
    
    public Polynomial simplify() {
        List<Term> copies = new ArrayList<>();
        for (Term term : terms) {
            boolean containedInOriginal = false;
            for (int i = 0; i < copies.size(); i++) {
                if (copies.get(i).getVariable().equals(term.getVariable()) &&
                        copies.get(i).getPower() == term.getPower()) {
                    containedInOriginal = true;
                    copies.set(i, new Term(copies.get(i).getCoefficient().add(term.getCoefficient()),
                            copies.get(i).getVariable(), copies.get(i).getPower() + term.getPower()));
                }
            }
            if (!containedInOriginal) {
                copies.add(term);
            }
        }
        copies.removeIf(copy -> copy.getCoefficient().isZero());
        return new Polynomial(copies);
    }
    
    public void sort() {
        terms.sort(Comparator.comparing(Term::getPower).reversed().thenComparing(Term::getVariable));
    }
    
    @Override
    public String toString() {
        setTerms(simplify().getTerms());
        sort();
        
        if (terms.size() == 0) {
            return "0";
        } else if (terms.size() == 1) {
            return terms.get(0).toString();
        } else {
            StringBuilder polynomialStringBuilder = new StringBuilder();
            polynomialStringBuilder.append(terms.get(0).toString());
            for (int i = 1; i < terms.size(); i++) {
                if (!terms.get(i).getCoefficient().isNegative()) {
                    polynomialStringBuilder.append("+");
                }
                polynomialStringBuilder.append(terms.get(i).toString());
            }
            return polynomialStringBuilder.toString();
        }
    }
}
