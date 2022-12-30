package mainpackage;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;
import org.json.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
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

		//2.2 Using the XML data
		Document xmlFile=loadXML();
		Element root = xmlFile.getDocumentElement();
		NodeList labels = root.getElementsByTagName("label");
		for (Client client : clients) {
			Element label1 = (Element) labels.item(0);
			Element label2 = (Element)labels.item(1);
			accounts.add(new CurrentAccount(label1.getTextContent(), client));
			accounts.add(new SavingsAccount(label2.getTextContent(), client));
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

		//2.1 Filling the flows with the JSON data
		List<JSONObject>jsonFile=loadJson();
		for (JSONObject jsonObject : jsonFile) {
			String comment=jsonObject.getString("Comment");
			String id= jsonObject.getString("Identifier");
			Double ammount=jsonObject.getDouble("Amount");
			Boolean effect=jsonObject.getBoolean("Effect");

			if(id.matches("Debit")) {
				int target=jsonObject.getInt("Target Account");
				flows.add(new Debit(comment, id,ammount,target, effect, flowDate));
			}
			else if (id.matches("Credit-C")) {
				accounts.stream().filter(CurrentAccount.class::isInstance).
				forEach(x->flows.add(new Credit(comment, id, ammount,x.getAccountNumber() , effect, flowDate)));
			}
			else if (id.matches("Credit-S")) {
				accounts.stream().filter(SavingsAccount.class::isInstance).forEach(x->
				flows.add(new Credit(comment,id, ammount, x.getAccountNumber(), effect, flowDate)));
			}
			else if (id.matches("Transfer")) {
				int target=jsonObject.getInt("Target Account");
				int issuing= jsonObject.getInt("Issuing Account");
				flows.add(new Transfer(comment,id,ammount, target, effect, issuing, flowDate));
			} 
		}

		/* I left this part commented as reference of 1.3.4 exercise
		 * 
		flows.add(new Debit("a debit of 50€ from account n°1", "Debit",50.0,
				((ArrayList<Account>) accounts).get(0).getAccountNumber(), false, flowDate));

		accounts.stream().filter(CurrentAccount.class::isInstance)
		.forEach(x->flows.add(new Credit("A credit of 100.50€ on all current accounts in the array of accounts ", 
					"Credit", 100.5, x.getAccountNumber(), false, flowDate)));

		accounts.stream()
		.filter(SavingsAccount.class::isInstance)
		.forEach(a->
		flows.add(new Credit("Credit of 1500€ on all SavingsAccounts",
				"Credit", 1500.0, x.getAccountNumber(), false, flowDate)));

		flows.add(new Transfer("Transfer of 50€ from Account 1 to Account 2", 
				"Transfer", 50.0, ((ArrayList<Account>) accounts).get(1).getAccountNumber()
				, false, ((ArrayList<Account>) accounts).get(0).getAccountNumber(), flowDate));
		 */			
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

		Predicate<Account>negativeBalance=acc->acc.getBalance()<=0;
		Optional<Account>accNegativeBalance=hashtable.values().stream().filter(negativeBalance).findAny();
		accNegativeBalance.ifPresentOrElse(x -> System.out.println(" Account " + x.getAccountNumber()+" has Negative Balance ")
				,() -> System.out.println("All accounts have Positive Balance"));	
	}

	//2.1 Method to load a JSON file
	private static List<JSONObject> loadJson() {
		List<JSONObject> jsonObjects = new ArrayList<>();
		try {
			Path path = Paths.get("src/resources/flows.json");
			String jsonString = (Files.readString(path));
			JSONArray jsonArray = new JSONArray(jsonString);

			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObjects.add(jsonArray.getJSONObject(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObjects;
	}

	//2.2 Method to load a XML file
	private static Document loadXML(){
		Document document = null;
		try {
			Path path=Paths.get("src/resources/accounts.xml");
			InputStream stream = new ByteArrayInputStream(Files.readAllBytes(path));
			DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			document=builder.parse(stream);

		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		return document;
	}
}
