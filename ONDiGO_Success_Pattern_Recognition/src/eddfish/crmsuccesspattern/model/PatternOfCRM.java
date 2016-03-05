package eddfish.crmsuccesspattern.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

import eddfish.crmsuccesspattern.dao.EVandSD;
import eddfish.crmsuccesspattern.dao.ONDiGO;
import eddfish.crmsuccesspattern.dao.Stages;

public class PatternOfCRM {
	SimpleDateFormat df;
	long mlSecByPeriod;
	private int nOfWons;
	EVandSD lifeTimeEVandSD;
	EVandSD nCommunicationsEVanSD;
	double[] modeOfLifeTime;
	double[] modenCommunications;
	double[] modeOfDensity;

	public PatternOfCRM(ONDiGO[] crm, String df, long period) throws ParseException {
		nOfWons = stateCounting(crm, "won");
		this.df = new SimpleDateFormat(df);
		this.mlSecByPeriod = period;
		double[] ltArray = createArrayOfLifeTime(crm, "won");
		double[] ncArray = createArrayOfCommunications(crm, "won");
		double[] densArray = createArrayOfDensityCommunications(ltArray, ncArray);
		this.modeOfLifeTime = StatUtils.mode(ltArray);
		this.modenCommunications = StatUtils.mode(ncArray);
		this.modeOfDensity = StatUtils.mode(densArray);
		this.lifeTimeEVandSD = calcEVandSD(ltArray, modeOfLifeTime.length);
		this.nCommunicationsEVanSD = calcEVandSD(ncArray, modenCommunications.length);

	}

	public double[] getModenCommunications() {
		return modenCommunications;
	}

	public double[] getModeOfLifeTime() {
		return modeOfLifeTime;
	}

	public double[] getModeOfDensity() {
		return modeOfDensity;
	}

	public EVandSD getLifeTimeEVandSD() {
		return lifeTimeEVandSD;
	}

	public EVandSD getnCommunicationsEVanSD() {
		return nCommunicationsEVanSD;
	}

	private EVandSD calcEVandSD(double[] arr, int modeLength) {
		double ev, sd;
		if (modeLength == 1 && arr.length > 30) {
			ev = StatUtils.mean(arr);
			sd = FastMath.sqrt(StatUtils.variance(arr));
		} else {
			ev = StatUtils.percentile(arr, 50);
			sd = (StatUtils.percentile(arr, 75) - StatUtils.percentile(arr, 25)) / 2;
		}

		return new EVandSD(ev, sd);

	}

	private double[] createArrayOfCommunications(ONDiGO[] crm, String state) {
		double[] res = new double[nOfWons];
		int k = 0;
		for (int i = 0; i < crm.length; i++) {
			if ((crm[i].getState()).equalsIgnoreCase(state)) {
				res[k] = crm[i].getMeetings().length + crm[i].getMessages().length;
				k++;
			}
		}
		// Arrays.sort(res);
		return res;
	}

	private double[] createArrayOfLifeTime(ONDiGO[] crm, String state) throws ParseException {
		double[] res = new double[nOfWons];
		int k = 0;
		for (int i = 0; i < crm.length; i++) {
			Stages[] tmpStage = crm[i].getStages();
			if ((crm[i].getState()).equalsIgnoreCase(state) && tmpStage.length != 0) {
				long min = getDateByString(tmpStage[0].getDate());
				long max = min;
				for (int j = 1; j < tmpStage.length; j++) {
					long currentDate = getDateByString(tmpStage[j].getDate());
					max = FastMath.max(max, currentDate);
					min = FastMath.min(min, currentDate);
				}
				res[k] = (getTimeInterval(min, max));
				k++;
			}
		}
		// Arrays.sort(res);
		return res;
	}

	private double[] createArrayOfDensityCommunications(double[] ltArray, double[] ncArray) {
		double[] res = new double[nOfWons];
		for (int i = 0; i < res.length; i++) {
			res[i] = ncArray[i] / ltArray[i];
			res[i] = Precision.round(res[i], 2, BigDecimal.ROUND_HALF_UP);
		}
		return res;
	}

	private double getTimeInterval(long begin, long end) {
		double res = ((double) (end - begin)) / mlSecByPeriod;
		res = Precision.round(res, 2, BigDecimal.ROUND_HALF_UP);
		return res;
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
