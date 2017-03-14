package weatherstation34.weatherstation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.time.Month;

public class WeatherStationBinary {
	
	public static boolean saveMeasurment(Measurment measurment, String filePath){
		File myFile = new File(filePath);
		FileInputStream fileIn = null;
		DataInputStream dataIn = null;
		
		FileOutputStream fileOut = null;
		DataOutputStream dataOut = null;
		
		String tmpFilePath = filePath + "_tmp";
		File tmpMyFile = new File(tmpFilePath);
		
		try {
			myFile.createNewFile();
			tmpMyFile.createNewFile();
			
			fileOut = new FileOutputStream(tmpMyFile);
			dataOut = new DataOutputStream(fileOut);
			
			fileIn = new FileInputStream(myFile);
			dataIn = new DataInputStream(fileIn);
			
			long time;
			int temperature;
			boolean notSaved = true;
			try {
				time = dataIn.readLong();
				temperature = dataIn.readInt();
				while (notSaved) {
					if (measurment.getTimeSince() < time){
						dataOut.writeLong(measurment.getTimeSince());
						dataOut.writeInt(measurment.getTemperature());
						notSaved = false;
					}
					dataOut.writeLong(time);
					dataOut.writeInt(temperature);
					time = dataIn.readLong();
					temperature = dataIn.readInt();
				}
				while (!notSaved) {
					dataOut.writeLong(time);
					dataOut.writeInt(temperature);
					time = dataIn.readLong();
					temperature = dataIn.readInt();
				}
			} catch (EOFException e){
				if (notSaved){
					dataOut.writeLong(measurment.getTimeSince());
					dataOut.writeInt(measurment.getTemperature());
				}
			} finally {
				if (fileIn != null) fileIn.close();
				if (dataIn != null) dataIn.close();
				if (dataOut != null) dataOut.close();
				if (fileOut != null) fileOut.close();
				try{
					Files.move(tmpMyFile.toPath(), myFile.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e){
					if (tmpMyFile != null) tmpMyFile.delete();
					return false;
				}
			}
		} catch (EOFException e){
			try{
				dataOut.writeLong(measurment.getTimeSince());
				dataOut.writeInt(measurment.getTemperature());
				if (fileIn != null) fileIn.close();
				if (dataIn != null) dataIn.close();
				if (dataOut != null) dataOut.close();
				if (fileOut != null) fileOut.close();
				Files.move(tmpMyFile.toPath(), myFile.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex){
				if (tmpMyFile != null) tmpMyFile.delete();
				return false;
			}
		} catch (IOException e){
			if (tmpMyFile != null) tmpMyFile.delete();
			return false;
		}
		return true;
	}
	
	public static boolean saveMeasurmentLast(int temperature, String filePath){
		Date now = new Date();
		File myFile = new File(filePath);
		FileInputStream fileIn = null;
		DataInputStream dataIn = null;
		
		FileOutputStream fileOut = null;
		DataOutputStream dataOut = null;
		
		String tmpFilePath = filePath + "_tmp";
		File tmpMyFile = new File(tmpFilePath);
		
		try {
			myFile.createNewFile();
			tmpMyFile.createNewFile();
			
			fileOut = new FileOutputStream(tmpMyFile);
			dataOut = new DataOutputStream(fileOut);
			
			fileIn = new FileInputStream(myFile);
			dataIn = new DataInputStream(fileIn);
			
			long timeRead;
			int temperatureRead;
			try {
				while (true) {
					timeRead = dataIn.readLong();
					temperatureRead = dataIn.readInt();
					dataOut.writeLong(timeRead);
					dataOut.writeInt(temperatureRead);
				}
			} catch (EOFException e){
				dataOut.writeLong(now.getTime());
				dataOut.writeInt(temperature);
			} finally {
				if (fileIn != null) fileIn.close();
				if (dataIn != null) dataIn.close();
				if (dataOut != null) dataOut.close();
				if (fileOut != null) fileOut.close();
				try{
					Files.move(tmpMyFile.toPath(), myFile.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e){
					if (tmpMyFile != null) tmpMyFile.delete();
					return false;
				}
			}
		} catch (EOFException e){
			try{
				dataOut.writeLong(now.getTime());
				dataOut.writeInt(temperature);
				if (fileIn != null) fileIn.close();
				if (dataIn != null) dataIn.close();
				if (dataOut != null) dataOut.close();
				if (fileOut != null) fileOut.close();
				Files.move(tmpMyFile.toPath(), myFile.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex){
				if (tmpMyFile != null) tmpMyFile.delete();
				return false;
			}
		} catch (IOException e){
			if (tmpMyFile != null) tmpMyFile.delete();
			return false;
		}
		System.gc();
		return true;
	}
	
	public static void readMonthlyReport(Month month, int year, String filePath){		
		File myFile = new File(filePath);
		FileInputStream fileIn = null;
		DataInputStream dataIn = null;
		int i = 0;
		
		try {			
			fileIn = new FileInputStream(myFile);
			dataIn = new DataInputStream(fileIn);
			
			long time1;
			int temp1;
			try {
				while (true) {
					time1 = dataIn.readLong();
					temp1 = dataIn.readInt();
					Date date = new Date(time1);
					if (date.getYear() == (year-1900) && date.getMonth() == month.getValue()){
						System.out.println(date + " " + temp1 + "C " + "\t(" + ++i + ")");
					}
				}
			} catch (EOFException e){

			}
		} catch (EOFException e){
			try{
				if (fileIn != null) fileIn.close();
				if (dataIn != null) dataIn.close();
			} catch (IOException ex){
				System.out.println("Error " + e.getMessage());
			}
		} catch (IOException e){
			System.out.println("Error " + e.getMessage());
		}
	}
	
	public static void readTempIncrease(String filePath){
		File myFile = new File(filePath);
		FileInputStream fileIn = null;
		DataInputStream dataIn = null;

		try {
			myFile.createNewFile();
			
			fileIn = new FileInputStream(myFile);
			dataIn = new DataInputStream(fileIn);
			
			long time1;
			int temp1;
			long time2;
			int temp2;
			int i = 0;
			
			try {
				time1 = dataIn.readLong();
				temp1 = dataIn.readInt();
				while (true) {
					time2 = dataIn.readLong();
					temp2 = dataIn.readInt();
					if (temp2 >= temp1){
						if (i == 0) System.out.println(new Date(time1) + " " + temp1 + "C " + "\t(" + ++i + ")");
						System.out.println(new Date(time2) + " " + temp2 + "C " + "\t(" + ++i + ")");
					} else {
						i = 0;
					}
					time1 = time2;
					temp1 = temp2;
				}
			} catch (EOFException e){
			}finally {
				if (fileIn != null) fileIn.close();
				if (dataIn != null) dataIn.close();
			}
		} catch (EOFException e){
			try{
				if (fileIn != null) fileIn.close();
				if (dataIn != null) dataIn.close();
			} catch (IOException ex){
				System.out.println("Error " + e.getMessage());
			}
		} catch (IOException e){
			System.out.println("Error " + e.getMessage());
		}
	}
}