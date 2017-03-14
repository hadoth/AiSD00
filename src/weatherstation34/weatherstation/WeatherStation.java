package weatherstation34.weatherstation;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.time.Month;

public class WeatherStation {
	
	public static boolean saveMeasurment(Measurment measurment, String filePath){
		File myFile = new File(filePath);
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		
		FileOutputStream fileOut = null;
		ObjectOutputStream objectOut = null;
		
		String tmpFilePath = filePath + "_tmp";
		File tmpMyFile = new File(tmpFilePath);
		
		try {
			myFile.createNewFile();
			tmpMyFile.createNewFile();
			
			fileOut = new FileOutputStream(tmpMyFile);
			objectOut = new ObjectOutputStream(fileOut);
			
			fileIn = new FileInputStream(myFile);
			objectIn = new ObjectInputStream(fileIn);
			
			Measurment readMeasurment;
			boolean notSaved = true;
			try {
				readMeasurment = (Measurment)objectIn.readObject();
				while (notSaved) {
					if (measurment.getTimeSince() < readMeasurment.getTimeSince()){
						objectOut.writeObject(measurment);
						notSaved = false;
					}
					objectOut.writeObject(readMeasurment);
					readMeasurment = (Measurment)objectIn.readObject();
				}
				while (!notSaved) {
					objectOut.writeObject(readMeasurment);
					readMeasurment = (Measurment)objectIn.readObject();
				}
			} catch (EOFException e){
				if (notSaved){
					objectOut.writeObject(measurment);
				}
			} catch (ClassNotFoundException e){
				if (tmpMyFile != null) tmpMyFile.delete();
			} finally {
				if (fileIn != null) fileIn.close();
				if (objectIn != null) objectIn.close();
				if (objectOut != null) objectOut.close();
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
				objectOut.writeObject(measurment);
				if (fileIn != null) fileIn.close();
				if (objectIn != null) objectIn.close();
				if (objectOut != null) objectOut.close();
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
	
	public static boolean saveMeasurmentLast(int temperature, String filePath){
		Measurment measurment = new Measurment(new Date(), temperature);
		File myFile = new File(filePath);
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		
		FileOutputStream fileOut = null;
		ObjectOutputStream objectOut = null;
		
		String tmpFilePath = filePath + "_tmp";
		File tmpMyFile = new File(tmpFilePath);
		
		try {
			myFile.createNewFile();
			tmpMyFile.createNewFile();
			
			fileOut = new FileOutputStream(tmpMyFile);
			objectOut = new ObjectOutputStream(fileOut);
			
			fileIn = new FileInputStream(myFile);
			objectIn = new ObjectInputStream(fileIn);
			
			Measurment readMeasurment;
			boolean notSaved = true;
			try {
				while (true) {
					readMeasurment = (Measurment)objectIn.readObject();
					objectOut.writeObject(readMeasurment);
				}
			} catch (EOFException e){
				if (notSaved){
					objectOut.writeObject(measurment);
				}
			} catch (ClassNotFoundException e){
				if (tmpMyFile != null) tmpMyFile.delete();
			} finally {
				if (fileIn != null) fileIn.close();
				if (objectIn != null) objectIn.close();
				if (objectOut != null) objectOut.close();
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
				objectOut.writeObject(measurment);
				if (fileIn != null) fileIn.close();
				if (objectIn != null) objectIn.close();
				if (objectOut != null) objectOut.close();
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
		ObjectInputStream objectIn = null;
		int i = 0;
		
		try {			
			fileIn = new FileInputStream(myFile);
			objectIn = new ObjectInputStream(fileIn);
			
			Measurment readMeasurment;
			try {
				while (true) {
					readMeasurment = (Measurment)objectIn.readObject();
					if (readMeasurment.getDate().getYear() == (year-1900) && readMeasurment.getDate().getMonth() == month.getValue()){
						System.out.println(readMeasurment + "\t(" + ++i + ")");
					}
				}
			} catch (EOFException e){

			} catch (ClassNotFoundException e){
				System.out.println("Error" + e.getMessage());
			}
		} catch (EOFException e){
			try{
				if (fileIn != null) fileIn.close();
				if (objectIn != null) objectIn.close();
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
		ObjectInputStream objectIn = null;

		try {
			myFile.createNewFile();
			
			fileIn = new FileInputStream(myFile);
			objectIn = new ObjectInputStream(fileIn);
			
			Measurment firstMeasurment;
			Measurment secondMeasurment;
			boolean seriesOn = false;
			int i = 0;
			
			try {
				firstMeasurment = (Measurment)objectIn.readObject();
				while (true) {
					secondMeasurment = (Measurment)objectIn.readObject();
					if (secondMeasurment.getTemperature() >= firstMeasurment.getTemperature()){
						if (i == 0) System.out.println(firstMeasurment + "\t(" + ++i + ")");
						System.out.println(secondMeasurment + "\t(" + ++i + ")");
					} else {
						i = 0;
					}
					firstMeasurment = secondMeasurment;
				}
			} catch (EOFException e){

			} catch (ClassNotFoundException e){
				System.out.println("Error " + e.getMessage());
			} finally {
				if (fileIn != null) fileIn.close();
				if (objectIn != null) objectIn.close();
			}
		} catch (EOFException e){
			try{
				if (fileIn != null) fileIn.close();
				if (objectIn != null) objectIn.close();
			} catch (IOException ex){
				System.out.println("Error " + e.getMessage());
			}
		} catch (IOException e){
			System.out.println("Error " + e.getMessage());
		}
	}
}