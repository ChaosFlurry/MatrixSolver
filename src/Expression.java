import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private String text;
    
    public Expression(String text) {
        this.text = text;
    }
    
    public Expression evaluate() {
        return Expression.evaluate(this);
    }
    
    public static Expression evaluate(Expression text) {
        /*
        int numberOfTerms = expression.terms.size();
        // assuming cos(a+b)
        if (numberOfTerms == 2) {
            System.out.println("cos" + expression.terms.get(0) + "cos" + expression.terms.get(1) + "+" +
                    "sin" + expression.terms.get(0) + "sin" + expression.terms.get(1));
            return expression;
        } else if (numberOfTerms > 2) {
        
        } else {
            return null;
        }
        */
        
        
        
        
        
        Pattern termsPattern = Pattern.compile("(sin|cos)\\((a[+-]b[+-]?c?[+-]?d?)\\)");
        Matcher termsMatcher = termsPattern.matcher(text.toString());
        
        String expression = text.toString();
        if (termsMatcher.matches()) {
            List<String> signOrder = new ArrayList<>();
            for (int i = 0; i < expression.length(); i++) {
                if (Character.toString(expression.charAt(i)).equals("+") || Character.toString(expression.charAt(i)).equals("-")) {
                    signOrder.add(Character.toString(expression.charAt(i)));
                }
            }
            
            List<String> terms = new ArrayList<>(Arrays.asList(termsMatcher.group(1).split("[+-]")));
            for (String t : terms) {
                System.out.println(t);
            }
        } else {
            return new Expression(expression);
        }
        // TODO Replace
        return null;
    }
}
