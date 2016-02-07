package eddfish.crmsuccesspattern.controller;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import eddfish.crmsuccesspattern.dao.ONDiGO;
import eddfish.crmsuccesspattern.model.PatternOfCRM;

public class SuccessPatternAppl {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		/*
		 * RestTemplate rest = new RestTemplate(); ONDiGO[] crm =
		 * rest.getForObject("", ONDiGO[].class);
		 */
		ObjectMapper mapper = new ObjectMapper();
		ONDiGO[] crm = mapper.readValue(new File("crm.json"), ONDiGO[].class);
		/*
		 * for (int i = 0; i < crm.length; i++) { Stages[] stg =
		 * crm[i].getStages(); for (int j = 0; j < stg.length; j++) {
		 * System.out.print(stg[j].getName() + " ");
		 * System.out.println(stg[j].getDate()); } }
		 */
		try {
			PatternOfCRM pattern = new PatternOfCRM(crm, "yyy/MM/dd", 86400000);
			System.out.println("Life Time: "+pattern.getLifeTimeEVandSD());
			System.out.println("Number of Communications: "+pattern.getnCommunicationsEVanSD());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}