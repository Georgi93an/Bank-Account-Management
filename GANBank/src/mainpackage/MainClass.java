package mainpackage;
import java.util.*;
import java.util.stream.*;

import components.*;

public class MainClass {

	//1.1.2 Creation of Main class and methods to load clients	

	//1.1.2	Declaring the collection for clients
	private static Collection<Client>clients= new ArrayList<>();	
	
	//1.2.3 Declaring the collection of accounts
	private static Collection<Account>accounts= new ArrayList<>();
	
	public static void main(String[] args) {
		//1.1.2
		generateClients(3);
		displayClients(clients);
		
		//1.2.3
		generateAccounts(clients);
		displayAccounts(accounts);
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
	
	//1.2.3 Method to generate accounts
	private static void generateAccounts(Collection<Client>clients) {
		for (Client client : clients) {
			accounts.add(new CurrentAccount("Label for Current Accounts", client));
			accounts.add(new SavingsAccount("Label for Savings Accounts", client));
		}
	}
	
	//1.2.3 Method to display accounts
	private static void displayAccounts(Collection<Account>accounts) {
		String accountsToString=accounts.stream().map(Account::toString).collect(Collectors.joining(""));
		System.out.println("Accounts"+"\n"+"**********");
		System.out.println(accountsToString);	
	}
	
	
}
