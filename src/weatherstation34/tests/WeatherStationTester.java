package weatherstation34.tests;

import java.time.Month;
import java.util.Date;

import weatherstation34.weatherstation.*;

public class WeatherStationTester {
	public static void main(String[] args){
		
		int testNumber = 10;
		
		Date now = new Date();
		String filePath = "new.dat";
		String filePathBinary = "new2.dat";
		String objectPath = "new3.dat";
		WeatherStation myStation = new WeatherStation();
		WeatherStationBinary myBinaryStation = new WeatherStationBinary();
		MeasurmentSeries mySeries = new MeasurmentSeries();
		boolean saveResult = true;
		boolean lastResult = true;
		boolean loadSave = true;
		boolean saveResultList = true;
		boolean lastResultList = true;
		boolean saveResultBinary = true;
		boolean lastResultBinary = true;

		
		for (int i = 0; i < testNumber; i++){
			Date randomDate = new Date((long)(Math.random()*now.getTime()));
			Measurment tempMeasurment = new Measurment(randomDate, (int)(Math.random()*100));
			saveResult = saveResult && myStation.saveMeasurment(tempMeasurment,filePath);
			saveResultBinary = saveResultBinary && myBinaryStation.saveMeasurment(tempMeasurment,filePathBinary);
			saveResultList = saveResultList && mySeries.setMeasurment(tempMeasurment);
			loadSave = loadSave && mySeries.measurmentsOut(objectPath);
			mySeries = MeasurmentSeries.measurmentsIn(objectPath);
		}
		
		for (int i = 0; i < testNumber; i++){
			int randomTemp = (int)(Math.random()*100);
			lastResult = lastResult && myStation.saveMeasurmentLast(randomTemp,filePath);
			lastResultBinary = lastResultBinary && myBinaryStation.saveMeasurmentLast(randomTemp,filePathBinary);
			lastResultList = lastResultList && mySeries.setMeasurmentLast(randomTemp);
		}
		
		
		System.out.println("\nreadMonthlyReport - file\n");
		myStation.readMonthlyReport(Month.of(2), 2017,filePath);
		System.out.println("\nreadMonthlyReport - binary file\n");
		myBinaryStation.readMonthlyReport(Month.of(2), 2017,filePathBinary);
		System.out.println("\nreadMonthlyReport - object\n");
		mySeries.readMonthlyReport(Month.of(2), 2017);
		System.out.println("\nreadTempIncrease - file\n");
		myStation.readTempIncrease(filePath);
		System.out.println("\nreadTempIncrease - binary file\n");
		myBinaryStation.readTempIncrease(filePathBinary);
		System.out.println("\nreadTempIncrease - object\n");
		mySeries.readTempIncrease();
				
		System.out.println();
		
		System.out.println("saveMeasurmentLast tested; number of tests: " + testNumber + "; result: " + lastResult);
		System.out.println("saveMeasurmentLast tested for binary; number of tests: " + testNumber + "; result: " + lastResultBinary);
		System.out.println("setMeasurmentLast tested; number of tests: " + testNumber + "; result: " + lastResultList);
		System.out.println("saveMeasurment tested; number of tests: " + testNumber + "; result: " + saveResult);
		System.out.println("saveMeasurment tested for binary; number of tests: " + testNumber + "; result: " + saveResultBinary);
		System.out.println("setMeasurment tested; number of tests: " + testNumber + "; result: " + saveResultList);
	}
}
