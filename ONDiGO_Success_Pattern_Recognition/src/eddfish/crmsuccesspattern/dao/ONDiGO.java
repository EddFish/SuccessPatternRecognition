package eddfish.crmsuccesspattern.dao;

public class ONDiGO {
	String id;
	String state;
	Float amount;
	String closeDate;
	Stages[] stages;
	int contacts;
	String[] messages;
	String[] attachments;
	String[] meetings;

	public String getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public Float getAmount() {
		return amount;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public Stages[] getStages() {
		return stages;
	}

	public int getContacts() {
		return contacts;
	}

	public String[] getMessages() {
		return messages;
	}

	public String[] getAttachments() {
		return attachments;
	}

	public String[] getMeetings() {
		return meetings;
	}

}
