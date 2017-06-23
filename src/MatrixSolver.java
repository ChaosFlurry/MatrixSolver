public class MatrixSolver {
    public static void main(String[] args) {
        String data =   "1, 2, -3, 4|12" + "\n" +
                        "2, 2, -2, 3|10" + "\n" +
                        "0, 1, 1, 0|-1" + "\n" +
                        "1, -1, 1, -2|-4";
        AugmentedMatrix am2 = AugmentedMatrix.parse(data);
        System.out.println(am2);
        am2 = am2.solve();
        System.out.println(am2);
        
        // TODO return solution steps
    }
}
