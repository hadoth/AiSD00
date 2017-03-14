package weatherstation34.bootstrap;

import java.time.Month;
import java.util.Date;

import weatherstation34.weatherstation.*;

public class appSatrt {

	public static void main(String[] args) {
		WeatherStation myStation = new WeatherStation();
		Date now = new Date();
		Date yesterday = new Date (now.getTime()-10000000);
		Date another = new Date (yesterday.getTime()-1000000000);
		Date yetAnother = new Date(Date.UTC(1988, 10, 10, 10, 10, 10));
		Measurment measurment1 = new Measurment(now, 10);
		Measurment measurment2 = new Measurment(yesterday, 10);
		Measurment measurment3 = new Measurment(another, 10);
		Measurment measurment4 = new Measurment(yetAnother, 10);
		
		System.out.println(myStation.saveMeasurment(measurment4, "new.txt"));
		
		myStation.readMonthlyReport(Month.of(10), 1988, "new.txt");
		
	}
}
