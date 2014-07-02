package pl.socialgov.rzseie;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageParser {
	public static Logger logger = LoggerFactory.getLogger(PageParser.class);
	
	public static List <RegistryPage> getRegistryPages(Document doc){
		 Elements links = doc.select("a[href]");
		 List <RegistryPage> recordIds = new ArrayList<RegistryPage>();
	 	
	 	for (Element link : links) {
	 		logger.debug("getPagesListNo.link.attr(\"abs:href\")="+link.attr("abs:href"));
	 		Boolean isDetailedRegistryRecordLink = link.attr("abs:href").contains("http://www.rzseie.gios.gov.pl/szukaj_rzseie.php");
	 				
	          		
	 		if (isDetailedRegistryRecordLink){
	 			String limit = link.text();
	 			String url = link.attr("abs:href").toString();
				String row_nr = getSubString("row_nr=","&nr_nip",url);
				
				if(row_nr.equalsIgnoreCase("0")){
					row_nr = getSubString("row_nr=","&state_id",url);
					
				}
				else if(row_nr.equalsIgnoreCase("0")){
					//row_nr = getSubString("row_nr=","&state_id",url);
					
				}
				else{
					RegistryPage page = new RegistryPage(row_nr, limit);
 	 	 			recordIds.add(page);
				}
				
	 		}               
	     }
	 	
	 	
	     
	 		return recordIds;
	 	}
	
	private static String getSubString(String left, String right, String string){
		int start = string.indexOf(left);
		int stop = string.indexOf(right);
		
		String sub="0";
		try{
			 sub= string.substring(start+right.length(),stop);
			
	
			}catch(Exception e)
			{
			logger.info(e.getMessage()+" start: "+ start +" stop:" +stop +" string: " +string);
				
	 			
			}
		
		return sub;
		
	}
}
