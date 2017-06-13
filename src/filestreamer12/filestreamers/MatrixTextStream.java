package filestreamer12.filestreamers;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class MatrixTextStream implements MatrixStream {

	@Override
	public boolean saveFile(double[][] matrix, String savePath) {
		boolean result = true;
		FileWriter fileOut = null;
		PrintWriter dataOut =null;
		try {
			if (matrix.length == 0 || matrix[0].length == 0)
				throw new IndexOutOfBoundsException("Matrix dimensions must be grater than zero");
			fileOut = new FileWriter(savePath);
			dataOut = new PrintWriter(fileOut);
			
			dataOut.println("Macierz");
			dataOut.println(String.valueOf(matrix.length));
			dataOut.println(String.valueOf(matrix[0].length));
			for (int i = 0; i < matrix.length; i++){
				for (int j = 0; j < matrix[0].length; j++){
					dataOut.print(matrix[i][j]+"\t");
				}
				dataOut.println();
			}
			
			if (fileOut != null) fileOut.close();
			if (dataOut != null) dataOut.close();
		} catch (IOException e){
			result = false;
		}
		return result;
	}

	@Override
	public double[][] loadFile(String loadPath) {
		FileReader fileIn;
		BufferedReader dataIn;
		Scanner myScanner = null;
		double[][] result = null;
		try {
			fileIn = new FileReader(loadPath);
			dataIn = new BufferedReader(fileIn);
			
			myScanner = new Scanner(dataIn);
			while (!myScanner.hasNextInt()) {
				myScanner.nextLine();
			}
			int m = myScanner.nextInt();
			while(!myScanner.hasNextInt()) {
				myScanner.nextLine();
			}
			int n = myScanner.nextInt();
			
			result = new double[m][n];
			
			myScanner.nextLine();
			int i = 0;
			int j = 0;
			while (myScanner.hasNextLine()){
				String line = myScanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useLocale(Locale.ENGLISH);
				while(lineScanner.hasNext()){
					result[j][i] = lineScanner.nextDouble();
					i++;
				}
				lineScanner.close();
				j++;
				i=0;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			result = null;
		} catch (IndexOutOfBoundsException e){
			System.out.println(e.getMessage());
			result = null;
		} finally {
			myScanner.close();
		}
		return result;
	}

}
