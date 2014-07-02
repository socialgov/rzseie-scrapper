package pl.socialgov.rzseie;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class Scrapper {
	public static String url = "http://www.rzseie.gios.gov.pl/szukaj_rzseie.php?nazwa_podmiotu=&state_id[]=0&nr_rej=&nr_nip=&literal_id[]=0&szukaj=Wyszukaj";
	public Elements detailLinks = null;
	public static Logger logger = LoggerFactory.getLogger(Scrapper.class);
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JAXBException 
	 */
	public static void main(String[] args) throws IOException, JAXBException {
		

        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        
        getDetailedRegistryRecordUrlIds(doc);
  
       List<RegistryPage>  pages = getPagesListNo(doc);
       List<RegistryRecord> records = getAllPages(pages);
       export(records);
     //  List<RegistryRecord> list = getRecordsInPage(doc);
       
      //  export(list);
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
    /*
     * Operacja pobiera listę identyfikatorów wskazujących na szczegóły rekordów w ramach analizowanej strony (id_pdm)
     */
    private static List<String> getDetailedRegistryRecordUrlIds(Document doc){
    	Elements links = doc.select("a[href]");
    	List <String> recordIds = new ArrayList<String>();
    	
    	for (Element link : links) {
    		
    		Boolean isDetailedRegistryRecordLink = link.attr("abs:href").contains("http://www.rzseie.gios.gov.pl/dod_info.php?id_pdm=");
    				
             		
    		if (isDetailedRegistryRecordLink){
    			String id = link.attr("abs:href");
    			
    			id = id.substring(id.lastIndexOf("=") + 1);
    			
    			recordIds.add(id);
    		
    		}
                
          
        }
    	return recordIds;
    }
    /*
     * Operacja pobiera liczbę stron zawierających dane rejestru
     */
 private static List<RegistryPage> getPagesListNo(Document doc){
	 List <RegistryPage> pages = PageParser.getRegistryPages(doc);
	 /*
	 
	 Elements links = doc.select("a[href]");
	 List <RegistryPage> recordIds = new ArrayList<RegistryPage>();
 	
 	for (Element link : links) {
 		logger.debug("getPagesListNo.link.attr(\"abs:href\")="+link.attr("abs:href"));
 		Boolean isDetailedRegistryRecordLink = link.attr("abs:href").contains("http://www.rzseie.gios.gov.pl/szukaj_rzseie.php");
 				
          		
 		if (isDetailedRegistryRecordLink){
 			String limit = link.text();
 			String row_id = link.attr("abs:href").toString();
 			int start = row_id.indexOf("row_nr=");
 			int stop = row_id.indexOf("&nr_nip");
 			try{
 			String sub= row_id.substring(start+7,stop);
 			
  			RegistryPage page = new RegistryPage(sub, limit);
 			recordIds.add(page);
 			}catch(Exception e)
 			{
 			//logger.info(e.getMessage()+" row_id: "+ row_id +" limit:" +limit);
 				int start2 = row_id.indexOf("row_nr=");
 	 			int stop2 = row_id.indexOf("&state_id");
 	 			
 	 			try{
 	 	 			String sub= row_id.substring(start+9,stop);
 	 	 			
 	 	  			RegistryPage page = new RegistryPage(sub, limit);
 	 	 			recordIds.add(page);
 	 	 			}catch(Exception e2)
 	 	 			{
 	 	 				logger.info(e2.getMessage()+" row_id: "+ row_id +" limit:" +limit);
 	 	 				
 	 	 			}
 			}
 		} 
 		          
     }
 	*/    
 	
     
 		return pages;
 	}
 /*
  * Operacja pobiera listę rekordów rejestru
  */
 
 private static List<RegistryRecord> getRecordsInPage( Document doc){
	 
	 Elements table = doc.select("#tab"); 
	// logger.info("tab"+table.text());
	 Elements rows = table.select("tr");
	 
	 List<RegistryRecord> recordRows = new ArrayList<RegistryRecord>();
	 
	 
	 for (Element row : rows) {
	 Boolean isRowRecordType1 = row.attr("abs:bgcolor").contains("#EEEECC");
	if(isRowRecordType1)	{	
	 logger.info("Found row record1 :"+row.text());
	 
	 Elements fields = row.select("td");
	 RegistryRecord rr = new RegistryRecord();
	 for (int i=0;i<fields.size();i++) {
		 if(i==0){
		 rr.setLp(new Integer(fields.get(i).text()));
	 	
		 }
		 
		 if(i==1){
			 String nrRejestrowy =fields.get(i).text();
			 rr.setNrRejestrowy(nrRejestrowy);
		 }
		 if(i==2){
			 String firma=fields.get(i).text();	 
			 rr.setFirma(firma);
		 }
		 if(i==3){
		 String siedziba=fields.get(i).text();
		 rr.setSiedziba(siedziba);
		 }
		 if(i==4){
		 String wojwodztwo=fields.get(i).text();
		 rr.setWojwodztwo(wojwodztwo);
		 }
		 if(i==5){
		 String nip=fields.get(i).text();
		 rr.setNip(nip);
		 }
		 if(i==6){
			 String regon=fields.get(i).text();
			 rr.setRegon(regon);
		
		 }
		 if(i==7){
			 String id=fields.get(i).select("a[href]").attr("href").toString();
			 id = id.substring(id.lastIndexOf("=") + 1);
			 rr.setId_pdm(id);
		
		 }
		 } 
	 recordRows.add(rr);
	 logger.info("rr added :"+rr.toString());
	}
	Boolean isRowRecordType2 = row.attr("abs:bgcolor").contains("#EEEEBB");
	if(isRowRecordType2)	{	
		 logger.info("Found row record2 :"+row.text());
		 Elements fields = row.select("td");
		 RegistryRecord rr = new RegistryRecord();
		 for (int i=0;i<fields.size();i++) {
			 if(i==0){
			 rr.setLp(new Integer(fields.get(i).text()));
		 	
			 }
			 
			 if(i==1){
				 String nrRejestrowy =fields.get(i).text();
				 rr.setNrRejestrowy(nrRejestrowy);
			 }
			 if(i==2){
				 String firma=fields.get(i).text();	 
				 rr.setFirma(firma);
			 }
			 if(i==3){
			 String siedziba=fields.get(i).text();
			 rr.setSiedziba(siedziba);
			 }
			 if(i==4){
			 String wojwodztwo=fields.get(i).text();
			 rr.setWojwodztwo(wojwodztwo);
			 }
			 if(i==5){
			 String nip=fields.get(i).text();
			 rr.setNip(nip);
			 }
			 if(i==6){
				 String regon=fields.get(i).text();
				 rr.setRegon(regon);
			
			 }
			 if(i==7){
				 String id=fields.get(i).select("a[href]").attr("href").toString();
				 id = id.substring(id.lastIndexOf("=") + 1);
				 rr.setId_pdm(id);
			 }
		 }
		 recordRows.add(rr);
		 logger.info("rr added :"+rr.toString());
		}
	 }
	 
 	return recordRows;
 }
 private static List<RegistryRecord>  getAllPages(List<RegistryPage> pages) throws IOException{
	
	 List<RegistryRecord> records = new ArrayList<RegistryRecord>();
	
	 for (int i=0;i<pages.size();i++){
	 String url="http://www.rzseie.gios.gov.pl/szukaj_rzseie.php?limit="+pages.get(i).getLimit()+"&szukaj=Wyszukaj&nazwa_podmiotu=&nr_rej=&row_nr="+ pages.get(i).getRow_nr()+"&nr_nip=";
	 Document doc = Jsoup.connect(url).get();
	 records.addAll(getRecordsInPage(doc));
	
	 }
	 return records;
 }
 
private static void export(List<RegistryRecord> rr) throws IOException{
	XStream xstream = new XStream();
	xstream.alias("rekord", RegistryRecord.class);
	String xml = xstream.toXML(rr);
	logger.debug(xml);
	FileUtils.writeStringToFile(new File("rzseie.xml"), xml);
}
}
