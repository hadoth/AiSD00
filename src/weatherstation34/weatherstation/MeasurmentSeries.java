package weatherstation34.weatherstation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Month;
import java.util.Date;

public class MeasurmentSeries implements Serializable {
	private Measurment[] measurmentList;
	
	public MeasurmentSeries() {
		this.measurmentList = new Measurment[0];
	}
	
	public MeasurmentSeries(Measurment[] measurmentList) {
		this.measurmentList = measurmentList;
	}
	public boolean setMeasurment(Measurment measurment){
		Measurment[] tempList = new Measurment[this.measurmentList.length+1];
		boolean notSaved = true;
		int j = 0;
		for (int i = 0; i < this.measurmentList.length; i++){
			if (notSaved && this.measurmentList[i].getDate().getTime() > measurment.getDate().getTime()){
				tempList[j] = measurment;
				tempList[++j] = this.measurmentList[i];
				notSaved = false;
			} else {
				tempList[j] = this.measurmentList[i];
			}
			j++;
		}
		if (notSaved) tempList[j] = measurment;
		this.measurmentList = tempList;
		return true;
	}
	
	public boolean setMeasurmentLast(int temperature){
		Measurment[] tempList = new Measurment[this.measurmentList.length+1];
		for (int i=0; i < this.measurmentList.length; i++) tempList[i] = this.measurmentList[i];
		tempList[this.measurmentList.length] = new Measurment(new Date(), temperature);
		this.measurmentList = tempList;
		return true;
	}
	
	public void readMonthlyReport(Month month, int year){
		int counter = 0;
		for (int i = 0; i < this.measurmentList.length; i++){
			if (this.measurmentList[i].getDate().getYear() == (year-1900) && this.measurmentList[i].getDate().getMonth() == month.getValue()){
				System.out.println(this.measurmentList[i] + "\t(" + ++counter + ")");
			}
		}
	}
	
	public void readTempIncrease(){
		Measurment firstMeasurment;
		Measurment secondMeasurment;
		int i = 0;
		for (int j = 1; j < this.measurmentList.length; j++){
			firstMeasurment = this.measurmentList[j-1];
			secondMeasurment = this.measurmentList[j];
			if (secondMeasurment.getTemperature() >= firstMeasurment.getTemperature()){
				if (i == 0) System.out.println(firstMeasurment + "\t(" + ++i + ")");
				System.out.println(secondMeasurment + "\t(" + ++i + ")");
			} else {
				i = 0;
			}
		}
	}
	
	public boolean measurmentsOut(String savePath){
		try{
			File myFile = new File(savePath);
			String tmpPath = "tmp_"+savePath;
			File myFileTmp = new File(tmpPath);
			myFileTmp.createNewFile();
			
			FileOutputStream fileOut = new FileOutputStream(myFileTmp);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			try{
				Files.move(myFileTmp.toPath(), myFile.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e){
				if (myFileTmp != null) myFileTmp.delete();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static MeasurmentSeries measurmentsIn(String loadPath){
		try {
			FileInputStream fileIn = new FileInputStream(loadPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			MeasurmentSeries result = (MeasurmentSeries) in.readObject();
			in.close();
			fileIn.close();
			return result;
		} catch (Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
}
