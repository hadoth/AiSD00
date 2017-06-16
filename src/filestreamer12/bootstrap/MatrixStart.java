package filestreamer12.bootstrap;

import filestreamer12.utils.MatrixImpl;

public class MatrixStart {
	public static void main(String[] args){
		MatrixImpl myMatrix = MatrixImpl.builder().withSize(10, 10).withRange(1000).build();
		System.out.println(myMatrix);
	}
}