package filestreamer12.filestreamers;

import filestreamer12.utils.Matrix;

public interface MatrixStream {
    boolean saveFile(Matrix matrix, String savePath);

    Matrix loadFile(String loadPath);
}
