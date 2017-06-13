package filestreamer12.filestreamers;

public interface MatrixStream {
	boolean saveFile(double[][] matrix, String savePath);
	double[][] loadFile(String loadPath);
}
