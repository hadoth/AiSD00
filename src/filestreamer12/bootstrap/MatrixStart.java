package filestreamer12.bootstrap;

import filestreamer12.utils.*;

public class MatrixStart {
	public static void main(String[] args){
		Matrix myMatrix = Matrix.builder().withSize(10, 10).withRange(1000).build();
		System.out.println(myMatrix);
	}
}