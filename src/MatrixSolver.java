import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixSolver {
    public static void main(String[] args) throws InterruptedException {
        /*
        String data = "1, 2, -3, 4|12" + "\n" +
                "2, 2, -2, 3|10" + "\n" +
                "0, 1, 1, 0|-1" + "\n" +
                "1, -1, 1, -2|-4";
        AugmentedMatrix am2 = AugmentedMatrix.parse(data);
        System.out.println(am2);
        am2 = am2.solve();
        System.out.println(am2);
        
        
        Fraction f1 = new Fraction(1, 1);
        Fraction f2 = new Fraction(2, 1);
        Fraction f3 = new Fraction(3, 1);
        List<Fraction> fractions = new ArrayList<>();
        fractions.add(f2);
        fractions.add(f3);
        
        System.out.println(f1.add(fractions));
        */
        List<Term> terms = new ArrayList<>();
        Map<String, Integer> term1 = new HashMap<>();
        term1.put("x", 2);
        Map<String, Integer> term2 = new HashMap<>();
        term2.put("x", 3);
        term2.put("y", 1);
        term2.put("z", 2);
        Map<String, Integer> term3 = new HashMap<>();
        term3.put("x", 2);
        term3.put("y", 2);
        term3.put("z", 2);
        Map<String, Integer> term4 = new HashMap<>();
        term4.put("(x-2)^2-1", 5);
        Map<String, Integer> term5 = new HashMap<>();
        term5.put("", 0);
        Map<String, Integer> term6 = new HashMap<>();
        term6.put("x^3", 1);
        Map<String, Integer> term7 = new HashMap<>();
        term7.put("", 0);
        
        terms.add(new Term(Fraction.valueOf(2), term3));
        terms.add(new Term(Fraction.valueOf(5), term2));
        terms.add(new Term(Fraction.valueOf(1), term1));
        terms.add(new Term(Fraction.ONE.multiply(2), term4));
        //System.out.println(new Polynomial(terms).toString());
        //System.out.println(new Polynomial(terms).simplify().toString());
    
        List<Term> t1 = new ArrayList<>();
        t1.add(new Term(Fraction.ONE, term1));
        Polynomial p1 = new Polynomial(t1);
        List<Term> t2 = new ArrayList<>();
        t2.add(new Term(Fraction.ONE.negate(), term1));
        Polynomial p2 = new Polynomial(t2);
        //System.out.println(p1.multiply(p2));
        List<Term> t3 = new ArrayList<>();
        t3.add(new Term(Fraction.ONE.multiply(1), term4));
        Polynomial p3 = new Polynomial(t3);
        System.out.println(p3.expand());
        //System.out.println(p3.expand());
        
        List<Term> t4 = new ArrayList<>();
        t4.add(new Term(Fraction.ONE.multiply(2), term6));
        t4.add(new Term(Fraction.ONE.multiply(3), term7));
        Polynomial p4 = new Polynomial(t4);
        //System.out.println("p4: " + p4);
        Polynomial p4Copy = p4.copy();
        Polynomial p5 = p4.power(2);
        //System.out.println("mult: " + p5);
        /*for (Term t : p5.getTerms()) {
            System.out.println("cof: " + t.getCoefficient());
            for (String var : t.getVariablePowerMap().keySet()) {
                System.out.println("var: " + var);
                System.out.println("pow: " + t.getVariablePowerMap().get(var));
            }
            System.out.println("");
        }*/
        
        // TODO fix normal multiplication
    }
}
