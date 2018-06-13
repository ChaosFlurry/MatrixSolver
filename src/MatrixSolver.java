import java.util.ArrayList;
import java.util.List;

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
        terms.add(new Term(Fraction.valueOf(2), "x", 2));
        terms.add(new Term(Fraction.valueOf(1), "x", 0));
        terms.add(new Term(Fraction.valueOf(-1), "x", 0));
        System.out.println(new Polynomial(terms).toString());
        
    }
}
