package filestreamer12.utils;

import filestreamer12.filestreamers.MatrixStream;

public class Matrix {
	private double[][] content;
	private MatrixStream streamer;
	
	public Matrix(double[][] content, MatrixStream streamer){
		this.content = content;
		this.streamer = streamer;
	}
	
	public boolean saveMatrix(String savePath){
		return this.streamer.saveFile(this.content, savePath);
	}
	
	public double[][] loadMatrix(String loadPath){
		return this.streamer.loadFile(loadPath);
	}
	
	public double[][] getMatrix(){
		double[][] result = new double[this.content.length][this.content[0].length];
		for (int i=0; i < this.content.length; i++) 
			for (int j=0; j < this.content[0].length; j++) 
				result[i][j] = this.content[i][j];
		return result;
	}
	
	public String toString(){
		String result = "";
		for (int i=0; i < this.content.length; i++){
			result += "[";
			for (int j=0; j < this.content[0].length; j++){
				result += " [" + this.content[i][j] + "] "; 
			}
			result += "]"+'\n';
		}
		return result;
	}
	
	public boolean equals(Matrix otherMatrix) {
		double[][] comparable = otherMatrix.getMatrix();
		
		if (this.content.length != comparable.length || this.content[0].length != comparable[0].length) return false;
		for (int i=0; i < this.content.length; i++)
			for (int j=0; j < this.content[0].length; j++)
				if ((this.content[i][j]-comparable[i][j]) > 1e-9)
					return false;
		return true;
	}
	
	public static MatrixBuilder builder() {
		return new MatrixBuilder();
	}
}
