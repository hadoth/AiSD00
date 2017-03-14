package filestreamer12.filestreamers;

public interface FileStreamer {
	boolean saveFile(double[][] matrix, String savePath);
	double[][] loadFile(String loadPath);
}
