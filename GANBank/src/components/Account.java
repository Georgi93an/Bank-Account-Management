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

	//1.3.5 Updating accounts
	public void setBalance(Flow flow) {
		if(flow instanceof Credit) {
			this.balance+=flow.getAmount();
		}
		else if (flow instanceof Debit) {
			this.balance-=flow.getAmount();
		}
		else if (flow instanceof Transfer transfer) {
			if (this.getAccountNumber()==transfer.getTargetAccountNumber()) {
				this.balance+=transfer.getAmount();
			}
			if (this.getAccountNumber()==transfer.getIssuingAccount()) {
				this.balance-=transfer.getAmount();
			}
		}
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
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
