package generictable.test;

import java.util.Date;

import generictable.utils.GenericTable;
import generictable.utils.Person;

public class GenericTableTest {
	public static void main(String[] args){
		Date now = new Date();
		Person[] personTable = new Person[5];
		personTable[0] = new Person("Adam", "Nowak", new Date((long)(Math.random()*now.getTime())));
		personTable[1] = new Person("Jan", "Kowalski", new Date((long)(Math.random()*now.getTime())));
		personTable[2] = new Person("Karol", "Pokomeda", new Date((long)(Math.random()*now.getTime())));
		personTable[3] = new Person("Kacper", "Rutkowski", new Date((long)(Math.random()*now.getTime())));
		personTable[4] = new Person("Anna", "Skomro", new Date((long)(Math.random()*now.getTime())));
		
		boolean personWorks = true;
		boolean pointWorks = true;
		
		GenericTable<Person> genericPersonTable = new GenericTable<Person>(personTable);
		genericPersonTable.tableOut("personTable.dat");
		GenericTable<Person> genericPersonTable2 = GenericTable.tableIn("personTable.dat");
		
		System.out.println(genericPersonTable2.get(1));
//		if (genericPersonTable.length() == genericPersonTable2.length()){
//			for (int i = 0; i < genericPersonTable.length(); i++){
//				personWorks = personWorks && (genericPersonTable.get(i).getName().equals(genericPersonTable2.get(i).getName()));
//			}
//		} else {
//			personWorks = false;
//		}
//		
//		System.out.println("GenericTable<Person>");
	}
}
