package components;

public class Client {
	//1.1.1 Creation of Client class

	//Attributes	
	private String name;
	private String firstName;
	private int clientNumber;
	private static int nextClient=1;

	//Constructor
	public Client(String name, String firstName) {

		this.name = name;
		this.firstName = firstName;
		clientNumber=nextClient++;
	}

	//Accesors & Mutators
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getClientNumber() {
		return clientNumber;
	}

	//toString method
	public String toString() {
		return  "| Name= " + name + 
				"| FirstName= " + firstName + 
				"| ClientNumber= " + clientNumber + "\n";
	}
}
