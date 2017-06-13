package filestreamer12.utils;

import filestreamer12.filestreamers.MatrixStream;
import filestreamer12.filestreamers.NullStream;

public class MatrixBuilder{
	private double range;
	private MatrixStream streamer;
	private boolean randomized;
	private double[][] content;
	
	
	public MatrixBuilder(){
		this.range = 1;
		this.content = new double[3][3];
		this.randomized = true;
		this.streamer = new NullStream();
	}
	
	public MatrixBuilder isRandom(boolean randomized){
		this.randomized = randomized;
		return this;
	}
	
	public MatrixBuilder withContent(double[][] content){
		if (this.randomized) throw new IllegalArgumentException("Cannot set content when matrix is random");
		this.content = content;
		return this;
	}
	
	public MatrixBuilder withSize(int j, int k){
		if (!this.randomized) throw new IllegalArgumentException("Cannot set size when matrix is not random");
		if (j <= 0 || k <= 0) throw new IndexOutOfBoundsException("Matrix dimentsion must be grater than zero");
		this.content = new double[j][k];
		return this;
	}
	
	public MatrixBuilder withRange(double range){
		if (!this.randomized) throw new IllegalArgumentException("Cannot set range when matrix is not random");
		this.range = range;
		return this;
	}
	
	public MatrixBuilder withStreamer(MatrixStream streamer){
		this.streamer = streamer;
		return this;
	}
	
	public Matrix build(){
		if (this.randomized){
			for (int i=0; i < this.content.length; i++){
				for (int j=0; j < this.content[0].length; j++){
					this.content[i][j] = Math.random()*this.range;
				}
			}
		}
		return new Matrix(this.content, this.streamer);
	}
}