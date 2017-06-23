public class AugmentedMatrix {
    private Matrix m1;
    private Matrix m2;
    
    public AugmentedMatrix(Matrix m1, Matrix m2) {
        this.m1 = m1;
        this.m2 = m2;
    }
    
    public Matrix getM1() {
        return m1;
    }
    
    public Matrix getM2() {
        return m2;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < m1.getRows(); i++) {
            for (int j = 0; j < m1.getColumns(); j++) {
                s += m1.getElements()[i][j] + "\t";
            }
            s += "|\t";
            for (int j = 0; j < m2.getColumns(); j++) {
                s += m2.getElements()[i][j] + "\t";
            }
            s = s.trim();
            s += "\n";
        }
        return s;
    }
    
    public AugmentedMatrix solve() {
        int currentRow;
        int currentColumn;
        
        for (int i = 1; i < m1.getColumns() + 1; i++) {
            currentRow = i;
            currentColumn = i;
            
            // Check if all elements in the pivot column are 0
            boolean empty = true;
            for (int row = currentRow; row < m1.getRows() + 1; row++) {
                if (!m1.getPoint(row, currentColumn).equals(Fraction.ZERO)) {
                    empty = false;
                }
            }
            // If the current column contains no non-zero values, continue to the next column
            if (empty) {
                continue;
            }
            
            // Check if the pivot is 0
            if (m1.getPoint(currentRow, currentColumn).equals(Fraction.ZERO)) {
                // Set the pivot to a non-zero value
                for (int row = 1; row < m1.getRows() + 1; row++) {
                    if (!m1.getPoint(row, currentColumn).equals(Fraction.ZERO)) {
                        m1.swapRow(currentRow, row);
                        m2.swapRow(currentRow, row);
                        System.out.println("R" + currentRow + "~" + "R" + row);
                        System.out.println(this);
                        break;
                    }
                }
            }
            
            // Set all values in pivot column to 1
            for (int row = currentRow; row < m1.getRows() + 1; row++) {
                Fraction coefficient = m1.getPoint(row, currentColumn);
                if (coefficient.equals(Fraction.ZERO) || coefficient.equals(Fraction.ONE)) {
                    continue;
                }
                m1.divideRow(row, coefficient);
                m2.divideRow(row, coefficient);
                System.out.println(coefficient.reciprocal() + " R" + row);
                System.out.println(this);
            }
            
            // Subtract the pivot row from all non-zero rows
            for (int row = currentRow; row < m1.getRows() + 1; row++) {
                // Checks if the current row is the pivot
                if (row == currentRow) {
                    continue;
                }
                if (m1.getPoint(row, currentColumn).equals(Fraction.ZERO)) {
                    continue;
                }
                m1.subtractRows(row, currentRow);
                m2.subtractRows(row, currentRow);
                System.out.println("R" + row + " - " + "R" + currentRow);
                System.out.println(this);
            }
        }
        
        for (int i = 1; i < m1.getColumns() + 1; i++) {
            currentRow = i;
            currentColumn = i;
            
            // Sets all non-zero rows to 1 and subtracts the pivot row
            for (int row = 1; row < currentRow; row++) {
                Fraction coefficient = m1.getPoint(row, currentColumn);
                if (coefficient.equals(Fraction.ZERO) || coefficient.equals(Fraction.ONE)) {
                    continue;
                }
                m1.divideRow(row, coefficient);
                m2.divideRow(row, coefficient);
                System.out.println(coefficient.reciprocal() + " R" + row);
                System.out.println(this);
                
                m1.subtractRows(row, currentRow);
                m2.subtractRows(row, currentRow);
                System.out.println("R" + row + " - " + "R" + currentRow);
                System.out.println(this);
            }
        }
        
        // Divide all elements on the main diagonal so that all coefficients are 1
        for (int i = 1; i < m1.getColumns() + 1; i++) {
            Fraction coefficient = m1.getPoint(i, i);
            if (coefficient.equals(Fraction.ZERO) || coefficient.equals(Fraction.ONE)) {
                continue;
            }
            m1.divideRow(i, coefficient);
            m2.divideRow(i, coefficient);
            System.out.println(coefficient.reciprocal() + " R" + i);
            System.out.println(this);
        }
        return this;
    }
    
    public static AugmentedMatrix parse(String data) {
        String[] rows = data.split("\n");
        Fraction[][] leftRows = new Fraction[rows.length][];
        Fraction[][] rightRows = new Fraction[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String currentRow = rows[i];
            String[] left = currentRow.split("\\|")[0].split(", ");
            String[] right = currentRow.split("\\|")[1].split(", ");
            
            Fraction[] leftParsed = new Fraction[left.length];
            Fraction[] rightParsed = new Fraction[right.length];
            for (int j = 0; j < left.length; j++) {
                leftParsed[j] = Fraction.valueOf(Integer.parseInt(left[j]));
            }
            leftRows[i] = leftParsed;
            for (int j = 0; j < right.length; j++) {
                rightParsed[j] = Fraction.valueOf(Integer.parseInt(right[j]));
            }
            rightRows[i] = rightParsed;
        }
        Matrix left = new Matrix(leftRows);
        Matrix right = new Matrix(rightRows);
        return new AugmentedMatrix(left, right);
    }
}
