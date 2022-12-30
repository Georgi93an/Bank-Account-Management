package mainpackage;
import java.util.*;
import java.util.stream.*;

import components.*;

public class MainClass {

	//1.1.2 Creation of Main class and methods to load clients	

		//1.1.2	
		private static Collection<Client>clients= new ArrayList<>();	
	
	public static void main(String[] args) {

	}

	//1.1.2 Method to generate clients
	private static void generateClients(int numClients) {
		for(int i=0;i<numClients;i++) {
			clients.add(new Client("Name "+(i+1), "First Name "+(i+1)));
		}
	}
	
	//1.1.2 Method to display clients
	private static void displayClients(Collection<Client> clients) {	
		String clientsToString= clients.stream().map(Client::toString).collect(Collectors.joining(""));
		System.out.println("Clients"+"\n"+"**********");
		System.out.println(clientsToString);
	}
}
