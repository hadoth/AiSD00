package filestreamer12.filestreamers;

import filestreamer12.utils.Matrix;
import filestreamer12.utils.MatrixImpl;

import java.io.*;

public class MatrixBinaryStream implements MatrixStream {

    @Override
    public boolean saveFile(Matrix matrix, String savePath) {
        FileOutputStream fileOut;
        DataOutputStream dataOut;

        try {
            if (matrix.rows() == 0 || matrix.columns() == 0)
                throw new IndexOutOfBoundsException("MatrixImpl dimensions must be grater than zero");
            fileOut = new FileOutputStream(savePath);
            dataOut = new DataOutputStream(fileOut);

            dataOut.writeInt(matrix.rows());
            dataOut.writeInt(matrix.columns());

            for (int i =0; i < matrix.rows(); i++)
                for (int j = 0; j < matrix.columns(); j++)
                    dataOut.writeDouble(matrix.getValue(i, j));

            fileOut.close();
            dataOut.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public Matrix loadFile(String loadPath) {
        FileInputStream fileIn;
        DataInputStream dataIn;
        double[][] result;

        try {
            fileIn = new FileInputStream(loadPath);
            dataIn = new DataInputStream(fileIn);

            int m = dataIn.readInt();
            int n = dataIn.readInt();

            result = new double[m][n];

            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    result[i][j] = dataIn.readDouble();
            if (dataIn.read() != -1) result = null;
        } catch (IOException e) {
            result = null;
        }
        return new MatrixImpl(result);
    }
}