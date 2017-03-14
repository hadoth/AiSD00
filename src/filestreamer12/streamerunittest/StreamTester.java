package filestreamer12.streamerunittest;

import filestreamer12.filestreamers.*;
import filestreamer12.utils.*;

public class StreamTester {
	private static int testCounter;
	private FileStreamer streamer;
	private String filePath;
	private Matrix matrix;
	
	public static void main(String[] args){
		StreamTester myTSTest = StreamTester.generateTest(new TextStreamer());
		System.out.println("result " + myTSTest.test(1000));
		
		StreamTester myBSTest = StreamTester.generateTest(new BinaryStreamer());
		System.out.println("result " + myBSTest.test(1000));
	}
	
	public static StreamTester generateTest(FileStreamer streamer) {
		testCounter++;
		String filePath = "streamTest"+testCounter+".dat";
		return new StreamTester(streamer, filePath);
	}
	
	private StreamTester(FileStreamer streamer, String filePath){
		this.streamer = streamer;
		this.filePath = filePath;
	}
	
	public boolean test(int number) {
		boolean result = true;
		for (int i=0; i < number; i++){
			this.matrix = Matrix.builder().withSize((int)(Math.random()*10)+1, (int)(Math.random()*10)+1).withRange(1000).build();
			result = this.streamer.saveFile(this.matrix.getMatrix(), this.filePath);
			result = (this.matrix.equals(Matrix.builder().isRandom(false).withContent(this.streamer.loadFile(this.filePath)).build())); 
		}
		return result;
	}
}