package components;

import java.time.LocalDate;

public abstract class Flow {
	//1.3.2 Creation of Flow Class
	private String comment;
	private String identifier;
	private int amount;
	private int targetAccountNumber;
	private boolean effect;
	private LocalDate dateOfFlowDate;

	//Constructor
	protected Flow(String comment, String identifier, int amount, int targetAccountNumber, boolean effect,LocalDate dateOfFlowDate) {
		this.comment = comment;
		this.identifier = identifier;
		this.amount = amount;
		this.targetAccountNumber = targetAccountNumber;
		this.effect = effect;
		this.dateOfFlowDate = dateOfFlowDate;
	}
	
	//Accessors & Mutators
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getTargetAccountNumber() {
		return targetAccountNumber;
	}
	public void setTargetAccountNumber(int targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}
	public boolean isEffect() {
		return effect;
	}
	public void setEffect(boolean effect) {
		this.effect = effect;
	}
	public LocalDate getDateOfFlowDate() {
		return dateOfFlowDate;
	}
	public void setDateOfFlowDate(LocalDate dateOfFlowDate) {
		this.dateOfFlowDate = dateOfFlowDate;
	}

}
