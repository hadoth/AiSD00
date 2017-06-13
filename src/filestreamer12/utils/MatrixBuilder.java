package filestreamer12.utils;

public class MatrixBuilder {
    private double range;
    private boolean randomized;
    private double[][] content;


    public MatrixBuilder() {
        this.range = 1;
        this.content = new double[3][3];
        this.randomized = true;
    }

    public MatrixBuilder isRandom(boolean randomized) {
        this.randomized = randomized;
        return this;
    }

    public MatrixBuilder withContent(double[][] content) {
        if (this.randomized) throw new IllegalArgumentException("Cannot set content when matrix is random");
        this.content = content;
        return this;
    }

    public MatrixBuilder withSize(int j, int k) {
        if (!this.randomized) throw new IllegalArgumentException("Cannot set size when matrix is not random");
        if (j <= 0 || k <= 0) throw new IndexOutOfBoundsException("MatrixImpl dimentsion must be grater than zero");
        this.content = new double[j][k];
        return this;
    }

    public MatrixBuilder withRange(double range) {
        if (!this.randomized) throw new IllegalArgumentException("Cannot set range when matrix is not random");
        this.range = range;
        return this;
    }

    public MatrixImpl build() {
        if (this.randomized) {
            for (int i = 0; i < this.content.length; i++) {
                for (int j = 0; j < this.content[0].length; j++) {
                    this.content[i][j] = Math.random() * this.range;
                }
            }
        }
        return new MatrixImpl(this.content);
    }
}