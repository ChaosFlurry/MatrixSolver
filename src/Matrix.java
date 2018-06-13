
public class Matrix {
    private int rows;
    private int columns;
    private Fraction[][] elements;
    
    public Matrix(Fraction[][] elements) {
        this.rows = elements.length;
        this.columns = elements[0].length;
        this.elements = elements;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public Fraction[][] getElements() {
        return elements;
    }
    
    public Fraction[] getRow(int x) {
        return elements[x - 1];
    }
    
    public Fraction[] getColumn(int y) {
        Fraction[] result = new Fraction[columns];
        for (int i = 0; i < rows; i++) {
            result[i] = elements[i][y - 1];
        }
        return result;
    }
    
    public Fraction getPoint(int x, int y) {
        return elements[x - 1][y - 1];
    }
    
    public void setRow(int x, Fraction[] row) {
        elements[x - 1] = row;
    }
    
    public void setColumn(int y, Fraction[] column) {
        for (int i = 0; i < columns; i++) {
            elements[i][y - 1] = column[i];
        }
    }
    
    public void setPoint(int x, int y, Fraction value) {
        elements[x - 1][y - 1] = value;
    }
    
    public void swapRow(int x1, int x2) {
        Fraction[] original = getRow(x1);
        setRow(x1, getRow(x2));
        setRow(x2, original);
    }
    
    public void swapColumn(int y1, int y2) {
        Fraction[] original = getColumn(y1);
        setColumn(y1, getColumn(y2));
        setColumn(y2, original);
    }
    
    public void addRows(int row, int addend) {
        Fraction[] newRow = new Fraction[columns];
        for (int i = 0; i < newRow.length; i++) {
            //newRow[i] = Fraction.add(getRow(row)[i], getRow(addend)[i]);
        }
        setRow(row, newRow);
    }
    
    public void addColumns(int column, int addend) {
        Fraction[] newColumn = new Fraction[rows];
        for (int i = 0; i < newColumn.length; i++) {
            //newColumn[i] = Fraction.add(getColumn(column)[i], getColumn(addend)[i]);
        }
        setColumn(column, newColumn);
    }
    
    public void subtractRows(int row, int subtrahend) {
        Fraction[] newRow = new Fraction[columns];
        for (int i = 0; i < newRow.length; i++) {
            //newRow[i] = Fraction.subtract(getRow(row)[i], getRow(subtrahend)[i]);
        }
        setRow(row, newRow);
    }
    
    public void subtractColumns(int column, int subtrahend) {
        Fraction[] newColumn = new Fraction[rows];
        for (int i = 0; i < newColumn.length; i++) {
            //newColumn[i] = Fraction.subtract(getColumn(column)[i], getColumn(subtrahend)[i]);
        }
        setColumn(column, newColumn);
    }
    
    public void multiplyRow(int row, Fraction scalar) {
        Fraction[] newRow = getRow(row);
        for (int i = 0; i < newRow.length; i++) {
            //newRow[i] = Fraction.multiply(newRow[i], scalar);
        }
        setRow(row, newRow);
    }
    
    public void multiplyColumn(int column, Fraction scalar) {
        Fraction[] newColumn = getColumn(column);
        for (int i = 0; i < newColumn.length; i++) {
            //newColumn[i] = Fraction.multiply(newColumn[i], scalar);
        }
        setColumn(column, newColumn);
    }
    
    public void divideRow(int row, Fraction scalar) {
        multiplyRow(row, scalar.reciprocal());
    }
    
    public void divideColumn(int column, Fraction scalar) {
        multiplyColumn(column, scalar.reciprocal());
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                s += elements[i][j] + "\t";
            }
            s = s.trim();
            s += "\n";
        }
        return s;
    }
    
    public Matrix solve() {
        int currentRow;
        int currentColumn;
        
        for (int i = 1; i < columns + 1; i++) {
            currentRow = i;
            currentColumn = i;
            
            // Check if all elements in the pivot column are 0
            boolean empty = true;
            for (int row = currentRow; row < rows + 1; row++) {
                if (!getPoint(row, currentColumn).equals(Fraction.ZERO)) {
                    empty = false;
                }
            }
            // If the current column contains no non-zero values, continue to the next column
            if (empty) {
                continue;
            }
            
            // Check if the pivot is 0
            if (getPoint(currentRow, currentColumn).equals(Fraction.ZERO)) {
                // Set the pivot to a non-zero value
                for (int row = 1; row < rows + 1; row++) {
                    if (!getPoint(row, currentColumn).equals(Fraction.ZERO)) {
                        // SWAP
                        swapRow(currentRow, row);
                        System.out.println("R" + currentRow + "~" + "R" + row);
                        System.out.println(this);
                        break;
                    }
                }
            }
            
            // Set all values in pivot column to 1
            for (int row = currentRow; row < rows + 1; row++) {
                Fraction coefficient = getPoint(row, currentColumn);
                if (coefficient.equals(Fraction.ZERO) || coefficient.equals(Fraction.ONE)) {
                    continue;
                }
                // DIVIDE
                divideRow(row, coefficient);
                System.out.println(coefficient.reciprocal() + " R" + row);
                System.out.println(this);
            }
            
            // Subtract the pivot row from all non-zero rows
            for (int row = currentRow; row < rows + 1; row++) {
                // Checks if the current row is the pivot
                if (row == currentRow) {
                    continue;
                }
                // SUBTRACT
                subtractRows(row, currentRow);
                System.out.println("R" + row + " - " + "R" + currentRow);
                System.out.println(this);
            }
        }
        
        for (int i = 1; i < columns + 1; i++) {
            currentRow = i;
            currentColumn = i;
            
            // Sets all non-zero rows to 1 and subtracts the pivot row
            for (int row = 1; row < currentRow; row++) {
                Fraction coefficient = getPoint(row, currentColumn);
                if (coefficient.equals(Fraction.ZERO) || coefficient.equals(Fraction.ONE)) {
                    continue;
                }
                // DIVIDE
                divideRow(row, coefficient);
                System.out.println(coefficient.reciprocal() + " R" + row);
                System.out.println(this);
                // SUBTRACT
                subtractRows(row, currentRow);
                System.out.println("R" + row + " - " + "R" + currentRow);
                System.out.println(this);
            }
        }
        
        // Divide all elements on the main diagonal so that all coefficients are 1
        for (int i = 1; i < columns + 1; i++) {
            Fraction coefficient = getPoint(i, i);
            if (coefficient.equals(Fraction.ZERO) || coefficient.equals(Fraction.ONE)) {
                continue;
            }
            // DIVIDE
            divideRow(i, coefficient);
            System.out.println(coefficient.reciprocal() + " R" + i);
            System.out.println(this);
        }
        return this;
    }
}
