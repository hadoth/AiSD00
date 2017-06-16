package filestreamer12.filestreamers;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class MatrixTextStream implements MatrixStream {
    public static final String MATRIX_DIMENSIONS_EXCEPTION = "MatrixImpl dimensions must be grater than zero";
    public static final String MATRIX_NAME_PL = "Macierz";

    @Override
    public boolean saveFile(double[][] matrix, String savePath) {
        boolean result = true;
        FileWriter fileOut;
        PrintWriter dataOut;

        try {
            if (matrix.length == 0 || matrix[0].length == 0)
                throw new IndexOutOfBoundsException(MatrixTextStream.MATRIX_DIMENSIONS_EXCEPTION);
            fileOut = new FileWriter(savePath);
            dataOut = new PrintWriter(fileOut);

            dataOut.println(MatrixTextStream.MATRIX_NAME_PL);
            dataOut.println(String.valueOf(matrix.length));
            dataOut.println(String.valueOf(matrix[0].length));
            for (double[] aMatrix : matrix) {
                for (double value : aMatrix) {
                    dataOut.print(value + "\t");
                }
                dataOut.println();
            }

            fileOut.close();
            dataOut.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    @Override
    public double[][] loadFile(String loadPath) {
        FileReader fileIn;
        BufferedReader dataIn;
        Scanner myScanner = null;
        double[][] result;

        try {
            fileIn = new FileReader(loadPath);
            dataIn = new BufferedReader(fileIn);
            myScanner = new Scanner(dataIn);

            while (!myScanner.hasNextInt()) {
                myScanner.nextLine();
            }
            int m = myScanner.nextInt();

            while (!myScanner.hasNextInt()) {
                myScanner.nextLine();
            }
            int n = myScanner.nextInt();

            result = new double[m][n];
            int i = 0;
            int j = 0;

            myScanner.nextLine();
            while (myScanner.hasNextLine()) {
                String line = myScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useLocale(Locale.ENGLISH);
                while (lineScanner.hasNext()) {
                    result[j][i] = lineScanner.nextDouble();
                    i++;
                }
                lineScanner.close();
                j++;
                i = 0;
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            result = null;
        } finally {
            assert myScanner != null;
            myScanner.close();
        }

        return result;
    }
}