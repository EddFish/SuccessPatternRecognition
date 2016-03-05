package eddfish.crmsuccesspattern.dao;

public class InitialData {
	String dateFormat;
	String jsonURL;
	long mlSecPerDay;

	public InitialData(String dateFormat, String jsonURL, long mls) {
		super();
		this.dateFormat = dateFormat;
		this.jsonURL = jsonURL;
		this.mlSecPerDay = mls;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public String getJsonURL() {
		return jsonURL;
	}

	public long getMlSecPerDay() {
		return mlSecPerDay;
	}

}
