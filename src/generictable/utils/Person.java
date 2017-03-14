package generictable.utils;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable{
	private String name;
	private String surname;
	private Date birthDate;
	
	public Person(String name, String surname, Date birthDate){
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getSurname(){
		return this.surname;
	}
	
	public Date getBirthDate(){
		return this.birthDate;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public void setDate(Date birthDate){
		this.birthDate = birthDate;
	}
	
	public String toString(){
		return this.name + " " + this.surname + " " + this.birthDate;
	}
}
