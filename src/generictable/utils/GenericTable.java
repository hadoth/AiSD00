package generictable.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GenericTable<T> implements Serializable{
	private T[] t;
	
	public GenericTable (T[] t){
		this.t = t;
	}
	
	public int length(){
		return this.t.length;
	}
	
	public T get(int index){
		return t[index];
	}
	
	public boolean set(T t, int index){
		if (index <0 || index >= this.t.length) return false;
		this.t[index] = t;
		return true;
	}
	
	public boolean tableOut(String savePath){
		File myFileTmp = null;
		try{
			File myFile = new File(savePath);
			String tmpPath = "tmp_"+savePath;
			myFileTmp = new File(tmpPath);
			myFileTmp.createNewFile();
			
			FileOutputStream fileOut = new FileOutputStream(myFileTmp);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			Files.move(myFileTmp.toPath(), myFile.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			if (myFileTmp != null) myFileTmp.delete();
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static <T> GenericTable <T> tableIn(String loadPath){
		try {
			FileInputStream fileIn = new FileInputStream(loadPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			GenericTable<T> result = (GenericTable<T>) in.readObject();
			in.close();
			fileIn.close();
			return result;
		} catch (Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
