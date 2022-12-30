package components;

public abstract class Account {
	//1.2.1 Creation of Account class	
	protected String label;
	protected double balance;
	protected int accountNumber;
	protected static int nextAccount=1;
	protected Client client;
	
	//Constructor
	protected Account(String label, Client client) {
		this.label = label;
		this.client = client;
		accountNumber=nextAccount++;
	}

	//Accessors & Mutators
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public Client getClient() {
		return client;
	}

	//toString method
	public String toString() {
		return  "| Account |"+
				"| Label= " + label + 
				"| Balance= " + balance + 
				"| Account Number= " + accountNumber + 
				"| Client= " + client ;
	}
	
	
	
	
}