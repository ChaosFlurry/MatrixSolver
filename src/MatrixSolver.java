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
        term1.put("x", 6);
        Map<String, Integer> term2 = new HashMap<>();
        term2.put("x", 3);
        term2.put("y", 1);
        term2.put("z", 2);
        Map<String, Integer> term3 = new HashMap<>();
        term3.put("x", 2);
        term3.put("y", 2);
        term3.put("z", 2);
        Map<String, Integer> term4 = new HashMap<>();
        term4.put("x", 7);
        terms.add(new Term(Fraction.valueOf(2), term3));
        terms.add(new Term(Fraction.valueOf(5), term2));
        terms.add(new Term(Fraction.valueOf(-1), term1));
        terms.add(new Term(Fraction.ONE, term4));
        System.out.println(new Polynomial(terms).toString());
        
        
    }
}
