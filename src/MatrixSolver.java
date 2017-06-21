public class MatrixSolver {
    public static void main(String[] args) {
        String data =   "1, 3, 5|4" + "\n" +
                        "2, -3, -1|0" + "\n" +
                        "3, 2, 4|7";
        AugmentedMatrix am2 = AugmentedMatrix.parse(data);
        System.out.println(am2);
        am2 = am2.solve();
        System.out.println(am2);
        
        // TODO return solution steps
    }
}
