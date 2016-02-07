package eddfish.crmsuccesspattern.dao;

public class EVandSD {
	double ev;
	double sd;

	public EVandSD(double ev, double sd) {
		super();
		this.ev = ev;
		this.sd = sd;
	}

	public double getEv() {
		return ev;
	}

	public void setEv(double ev) {
		this.ev = ev;
	}

	public double getSd() {
		return sd;
	}

	public void setSd(double sd) {
		this.sd = sd;
	}

	@Override
	public String toString() {
		return "EV and SD [ev=" + ev + ", sd=" + sd + "]";
	}

}
