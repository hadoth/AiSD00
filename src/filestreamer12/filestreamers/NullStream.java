package filestreamer12.filestreamers;

public class NullStream implements MatrixStream {
	public boolean saveFile(double[][] matrix, String savePath) {
		return false;
	}

	public double[][] loadFile(String loadPath) {
		return null;
	}

}
