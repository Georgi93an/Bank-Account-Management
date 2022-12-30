package components;

import java.time.LocalDate;

public class Transfer extends Flow{
	//1.3.3 Creation of Transfer class

	private int issuingAccount;

	public Transfer(String comment, String identifier, int amount, int targetAccountNumber, boolean effect,
			int issuingAccount,LocalDate dateOfFlowDate) {
		super(comment, identifier, amount, targetAccountNumber, effect, dateOfFlowDate);
		this.issuingAccount=issuingAccount;
	}

	//Accessors & Mutators for issuingAccount
	public int getIssuingAccount() {
		return issuingAccount;
	}
	public void setIssuingAccount(int issuingAccount) {
		this.issuingAccount = issuingAccount;
	}
}
