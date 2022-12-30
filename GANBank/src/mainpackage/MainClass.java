package mainpackage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;
import java.util.Map.*;
import java.util.function.Predicate;

import components.*;

public class MainClass {

	//1.1.2 Creation of Main class and methods to load clients	

	//1.1.2	Declaring the collection for clients
	private static Collection<Client>clients= new ArrayList<>();	

	//1.2.3 Declaring the collection of accounts
	private static Collection<Account>accounts= new ArrayList<>();

	//1.3.1  Adaptation of the table of accounts, declaring hashtable
	private static Map<Integer, Account>orderedAccounts= new Hashtable<>(); 
	
	//1.3.4 Declaring the collection of flows
	private static Collection<Flow>flows=new ArrayList<>();

	public static void main(String[] args) {
		//1.1.2
		generateClients(3);
		displayClients(clients);

		//1.2.3
		generateAccounts(clients);
		displayAccounts(accounts);

		//1.3.1
		loadHastable(accounts);
		
		//1.3.4-1.3.5
		loadFlows();
		updateBalance(orderedAccounts, flows);
		//1.3.1
		orderHashTable();
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

	//1.3.1  Method to pass the collection of accounts to the HashTable
	private static void loadHastable(Collection<Account>accounts) {
		for (Account account : accounts) {
			orderedAccounts.put(account.getAccountNumber(), account);
		}
	}

	//1.3.1 Method to order the HashTable result by the balance in ascending order
	private static void orderHashTable() {
		Stream<Entry<Integer,Account>>orderStream=
				orderedAccounts.entrySet().stream().sorted((x,x2)
						->Double.compare(x.getValue().getBalance(), x2.getValue().getBalance()));

		String hashtableToString=orderStream.map(x->x.getValue().toString()).collect(Collectors.joining(""));
		System.out.println("Ordered Accounts"+"\n"+"**********");
		System.out.println(hashtableToString);
	}
	
	//1.3.4 Method to load flows
	private static void loadFlows() {
		LocalDate flowDate=LocalDate.now().plusDays(2);
	
		flows.add(new Debit("a debit of 50€ from account n°1", "Debit",50.0,
				((ArrayList<Account>) accounts).get(0).getAccountNumber(), false, flowDate));
		
		accounts.stream().filter(CurrentAccount.class::isInstance)
		.forEach(x->flows.add(new Credit("A credit of 100.50€ on all current accounts in the array of accounts ", 
					"Credit", 100.5, x.getAccountNumber(), false, flowDate)));
		
		accounts.stream()
		.filter(SavingsAccount.class::isInstance)
		.forEach(acc->
		flows.add(new Credit("Credit of 1500€ on all SavingsAccounts",
				"Credit", 1500.0, acc.getAccountNumber(), false, flowDate)));
		
		flows.add(new Transfer("Transfer of 50€ from Account 1 to Account 2", 
				"Transfer", 50.0, ((ArrayList<Account>) accounts).get(1).getAccountNumber()
				, false, ((ArrayList<Account>) accounts).get(0).getAccountNumber(), flowDate));
						
	}
	
	//1.3.5  Updating accounts
	private static void updateBalance(Map<Integer, Account> hashtable,Collection<Flow> flows) {
		for (Flow flow : flows) {
			Account targetAccount= hashtable.get(flow.getTargetAccountNumber());
			targetAccount.setBalance(flow);

			if (flow instanceof Transfer transfer) {
				Account issuingAcc=hashtable.get(transfer.getIssuingAccount());
				issuingAcc.setBalance(flow);
			}
		}

		Predicate<Account>negativeBalance=acc->acc.getBalance()<0;
		Optional<Account>accNegativeBalance=hashtable.values().stream().filter(negativeBalance).findAny();
		accNegativeBalance.ifPresentOrElse(x -> System.out.println(x.getAccountNumber() + " Has Negative Balance ")
				,() -> System.out.println("All accounts have Positive Balance"));	
	}
	
	
}
