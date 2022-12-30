package components;

import java.time.LocalDate;

public class Credit extends Flow{
	//1.3.3 Creation of Credit class
	public Credit(String comment, String identifier, int amount, int targetAccountNumber, boolean effect,
			LocalDate dateOfFlowDate) {
		super(comment, identifier, amount, targetAccountNumber, effect, dateOfFlowDate);
	}
}
