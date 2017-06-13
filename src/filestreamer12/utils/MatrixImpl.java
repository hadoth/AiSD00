package filestreamer12.utils;

public class MatrixImpl implements Matrix {
    private double[][] content;

    public MatrixImpl(double[][] content) {
        this.content = content;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (double[] row : this.content) {
            result.append("[");
            for (double value : row) {
                result.append(" [").append(value).append("] ");
            }
            result.append("]").append('\n');
        }
        return result.toString();
    }

    public boolean equals(MatrixImpl otherMatrix) {
        double[][] comparable = otherMatrix.toArray();

        if (this.content.length != comparable.length || this.content[0].length != comparable[0].length) return false;
        for (int i = 0; i < this.content.length; i++)
            for (int j = 0; j < this.content[0].length; j++)
                if ((this.content[i][j] - comparable[i][j]) > 1e-9)
                    return false;
        return true;
    }

    public static MatrixBuilder builder() {
        return new MatrixBuilder();
    }

    @Override
    public boolean setValue(int rowIndex, int columnIndex, double value) {
        if (columnIndex >= this.content.length || rowIndex >= this.content[columnIndex].length) return false;
        this.content[columnIndex][rowIndex] = value;
        return true;
    }

    @Override
    public double getValue(int rowIndex, int columnIndex) {
        return this.content[columnIndex][rowIndex];
    }

    @Override
    public double[][] toArray() {
        double[][] result = new double[this.content.length][this.content[0].length];
        for (int i = 0; i < this.content.length; i++) {
            System.arraycopy(this.content[i], 0, result[i], 0, this.content[0].length);
        }
        return result;
    }
}
