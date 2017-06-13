package filestreamer12.filestreamers;

import java.io.*;

public class MatrixBinaryStream implements FileStreamer {

	@Override
	public boolean saveFile(double[][] matrix, String savePath) {
		FileOutputStream fileOut;
		DataOutputStream dataOut;

		try {
			if (matrix.length == 0 || matrix[0].length == 0)
				throw new IndexOutOfBoundsException("Matrix dimensions must be grater than zero");
			fileOut = new FileOutputStream(savePath);
			dataOut = new DataOutputStream(fileOut);
			
			dataOut.writeInt(matrix.length);
			dataOut.writeInt(matrix[0].length);

            for (double[] aMatrix : matrix)
                for (double value : aMatrix)
                    dataOut.writeDouble(value);
			
			fileOut.close();
			dataOut.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public double[][] loadFile(String loadPath) {
		FileInputStream fileIn;
		DataInputStream dataIn;
		double[][] result;
		
		try {
			fileIn = new FileInputStream(loadPath);
			dataIn = new DataInputStream(fileIn);
			
			int m = dataIn.readInt();
			int n = dataIn.readInt();
			
			result = new double[m][n];
			
			for (int i=0; i < m; i++)
				for (int j=0; j <n; j++)
					result[i][j] = dataIn.readDouble();
			if (dataIn.read() != -1) result = null;
		} catch (IOException e){
			result = null;
		}
		return result;
	}
}