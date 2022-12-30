package components;

import java.time.LocalDate;

public class Debit  extends Flow{
	//1.3.3 Creation of Debit class
	public Debit(String comment, String identifier, int amount, int targetAccountNumber, boolean effect,
			LocalDate dateOfFlowDate) {
		super(comment, identifier, amount, targetAccountNumber, effect, dateOfFlowDate);
	}
}
