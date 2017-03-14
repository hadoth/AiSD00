package weatherstation34.weatherstation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Measurment implements Serializable {
	private Date date;
	private int temperature;
	
	public Measurment(long dateTime, int temperature){
		if (temperature < -273) throw new IllegalArgumentException("temperature must be greater than -273C");
		this.date = new Date(dateTime);
		this.temperature = temperature;
	}
	
	public Measurment(Date date, int temperature){
		this.date = date;
		this.temperature = temperature;
	}
	
	public Date getDate(){
		return new Date(this.date.getTime());
	}
	
	public long getTimeSince(){
		return date.getTime();
	}
	
	public int getTemperature(){
		return temperature;
	}
	
	public String toString(){
		return (this.getDate().toString() + " " + this.temperature + "C"); 
	}
	
	public void writeObject(ObjectOutputStream objectOut) throws IOException{
		objectOut.writeLong(this.date.getTime());
		objectOut.writeInt(this.temperature);
	}
	
	public void readObject(ObjectInputStream objectIn) throws IOException{
		this.date = new Date(objectIn.readLong());
		this.temperature = objectIn.readInt();
	}
}