package eddfish.crmsuccesspattern.controller;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import eddfish.crmsuccesspattern.dao.ONDiGO;
import eddfish.crmsuccesspattern.model.PatternOfCRM;

public class SuccessPatternAppl {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		/*
		 * RestTemplate rest = new RestTemplate(); ONDiGO[] crm =
		 * rest.getForObject("http://localhost:63342/Hello/crm.html",
		 * ONDiGO[].class);
		 */

		ObjectMapper mapper = new ObjectMapper();
		ONDiGO[] crm = mapper.readValue(new File("opportunities.json"), ONDiGO[].class);

		try {
			PatternOfCRM pattern = new PatternOfCRM(crm, "yyyy-MM-dd'T'HH:mm:ss+hh:mm", 86400000);
			System.out.println("Life Time: " + pattern.getLifeTimeEVandSD());
			double[] modeLT = pattern.getModeOfLifeTime();
			System.out.println("Mode of Life Time:");
			for (int i = 0; i < modeLT.length; i++) {
				System.out.print(modeLT[i] + ", ");
			}
			System.out.println();
			System.out.println("Number of Communications: " + pattern.getnCommunicationsEVanSD());
			double[] modeNC = pattern.getModenCommunications();
			System.out.println("Mode of communications number:");
			for (int i = 0; i < modeNC.length; i++) {
				System.out.print(modeNC[i] + ", ");
			}
			System.out.println();
			double[] modeDensity = pattern.getModeOfDensity();
			System.out.println("Mode of communications density:");
			for (int i = 0; i < modeDensity.length; i++) {
				System.out.print(modeDensity[i] + ", ");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
