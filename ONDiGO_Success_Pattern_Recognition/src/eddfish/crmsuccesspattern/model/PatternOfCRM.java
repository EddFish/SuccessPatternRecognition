package eddfish.crmsuccesspattern.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.util.FastMath;

import eddfish.crmsuccesspattern.dao.*;

public class PatternOfCRM {
	SimpleDateFormat df;
	long mlSecByPeriod;
	private int nOfWons;
	EVandSD lifeTimeEVandSD;
	EVandSD nCommunicationsEVanSD;

	public PatternOfCRM(ONDiGO[] crm, String df, long period) throws Exception {
		nOfWons = stateCounting(crm, "won");
		this.df = new SimpleDateFormat(df);
		this.mlSecByPeriod = period;
		// this.lifeTimeEVandSD =
		// calcEVandSD(listToArray(creatlistOfLifeTime(crm, "won")));
		this.lifeTimeEVandSD = calcEVandSD(creatArrayOfLifeTime(crm, "won"));
		this.nCommunicationsEVanSD = calcEVandSD(creatArrayOfCommunications(crm, "won"));

	}

	public EVandSD getLifeTimeEVandSD() {
		return lifeTimeEVandSD;
	}

	public EVandSD getnCommunicationsEVanSD() {
		return nCommunicationsEVanSD;
	}

	private EVandSD calcEVandSD(double[] arr) {
		double ev = StatUtils.mean(arr);
		StandardDeviation objSD = new StandardDeviation();
		double sd = objSD.evaluate(arr, ev);
		return new EVandSD(ev, sd);

	}

	private double[] listToArray(List<Integer> list) {
		double[] res = new double[list.size()];
		int i = 0;
		for (Integer arg : list) {
			res[i] = arg;
			i++;
		}
		return res;

	}

	private double[] creatArrayOfCommunications(ONDiGO[] crm, String state) {
		double[] res = new double[nOfWons];
		int k = 0;
		for (int i = 0; i < crm.length; i++) {
			if ((crm[i].getState()).equalsIgnoreCase(state)) {
				res[k] = crm[i].getAttachments().length + crm[i].getMeetings().length + crm[i].getMessages().length;
				k++;
			}
		}
		return res;
	}

	private double[] creatArrayOfLifeTime(ONDiGO[] crm, String state) throws Exception {
		double[] res = new double[nOfWons];
		int k = 0;
		for (int i = 0; i < crm.length; i++) {
			if ((crm[i].getState()).equalsIgnoreCase(state)) {
				Stages[] tmpStage = crm[i].getStages();
				long min = getDateByString(tmpStage[0].getDate());
				long max = min;
				for (int j = 1; j < tmpStage.length; j++) {
					max = FastMath.max(max, getDateByString(tmpStage[j].getDate()));
					min = FastMath.min(min, getDateByString(tmpStage[j].getDate()));
				}
				res[k] = (getTimeInterval(min, max));
				k++;
			}
		}
		return res;
	}

	private List<Integer> creatlistOfLifeTime(ONDiGO[] crm, String state) throws Exception {
		List<Integer> res = new LinkedList<Integer>();
		for (int i = 0; i < crm.length; i++) {
			if ((crm[i].getState()).equalsIgnoreCase(state)) {
				Stages[] tmpStage = crm[i].getStages();
				long min = getDateByString(tmpStage[0].getDate());
				long max = min;
				for (int j = 1; j < tmpStage.length; j++) {
					max = FastMath.max(max, getDateByString(tmpStage[j].getDate()));
					min = FastMath.min(min, getDateByString(tmpStage[j].getDate()));
				}
				res.add(getTimeInterval(min, max));
			}
		}
		return res;
	}

	private int getTimeInterval(long begin, long end) throws ParseException {
		return (int) ((end - begin) / mlSecByPeriod);
	}

	private long getDateByString(String date) throws ParseException {
		return df.parse(date).getTime();
	}

	private int stateCounting(ONDiGO[] crm, String state) {
		int count = 0;
		for (int i = 0; i < crm.length; i++)
			if ((crm[i].getState()).equalsIgnoreCase(state))
				count++;
		return count;
	}

}
