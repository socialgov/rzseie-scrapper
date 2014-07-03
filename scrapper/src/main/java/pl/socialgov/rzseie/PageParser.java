package pl.socialgov.rzseie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageParser {
	public static Logger logger = LoggerFactory.getLogger(PageParser.class);
	public static String URL = "http://www.rzseie.gios.gov.pl/szukaj_rzseie.php?nazwa_podmiotu=&state_id[]=0&nr_rej=&nr_nip=&literal_id[]=0&szukaj=Wyszukaj";

	/*
	 * Operacja pobiera wszystkie strony rejestru.
	 */
	public List<RegistryPage> getRegistryPages() throws IOException {
		// połączenie do strony głównej
		Connection conn =Jsoup.connect(URL);
		conn.timeout(120000);
		Document doc = conn.get();
		// wybranie elementów typu href
		Elements links = doc.select("a[href]");
		// lista stron rejestru
		List<RegistryPage> recordIds = new ArrayList<RegistryPage>();

		for (Element link : links) {
			logger.debug("getPagesListNo.link.attr(\"abs:href\")="
					+ link.attr("abs:href"));
			// czy link dotyczy szczegółów wpisu do rejestru
			Boolean isDetailedRegistryRecordLink = link
					.attr("abs:href")
					.contains("http://www.rzseie.gios.gov.pl/szukaj_rzseie.php");

			if (isDetailedRegistryRecordLink) {
				// atrybut stronicowania -limit
				String limit = link.text();

				String url = link.attr("abs:href").toString();

				// atrybut stronicowania - nr wiersza. atrybut now_nr może być w
				// różnych miejscach url
				String row_nr = getSubString("row_nr=", "&nr_nip", url);
				// jeśli się nie znalazł, to może być tu
				if (row_nr.equalsIgnoreCase("0")) {
					row_nr = getSubString("row_nr=", "&state_id", url);

				}

				else {
					// zapis metadatych strony
					RegistryPage page = new RegistryPage(row_nr, limit);
					recordIds.add(page);
				}

			}
		}

		return recordIds;
	}
	public List<RegistryPage> getAllRegistryPages() throws IOException{
		int nr_row=0;
		int limit=0;
		
		Connection conn =Jsoup.connect(URL);
		conn.timeout(120000);
		Document doc = conn.get();
		// wybranie elementów typu href
		Elements links = doc.select("a[href]");
		// lista stron rejestru
		List<Integer> recordIds = new ArrayList<Integer>();
		List<RegistryPage> pages = new ArrayList<RegistryPage>();
		for (Element link : links) {
			logger.debug("getPagesListNo.link.attr(\"abs:href\")="
					+ link.attr("abs:href"));
			// czy link dotyczy szczegółów wpisu do rejestru
			Boolean isDetailedRegistryRecordLink = link
					.attr("abs:href")
					.contains("http://www.rzseie.gios.gov.pl/szukaj_rzseie.php");

			if (isDetailedRegistryRecordLink) {
				// atrybut stronicowania -limit
				int id = new Integer( link.text());
				recordIds.add(id);	
				
				}
		}
		 int lastValue = recordIds.get(recordIds.size()-1);

		 for(int i=0;i<lastValue;i++){
			 limit = i+1;
			 if(i==0){
			 nr_row = i+1;
			 }
			 else{
			 nr_row=(30*i)+1;
			 }
			 RegistryPage page= new RegistryPage(nr_row+"",limit+"");
			 pages.add(page);
			 logger.debug("Page:" +page.toString());
		 }

		logger.debug("Last value:" +lastValue);
		
		return pages;
		
	}

	/*
	 * Operacja pobiera listę rekordów na stronie
	 */

	private List<RegistryRecord> getRecordsInPage(Document doc) {

		Elements table = doc.select("#tab");
		// logger.info("tab"+table.text());
		Elements rows = table.select("tr");

		List<RegistryRecord> recordRows = new ArrayList<RegistryRecord>();

		for (Element row : rows) {
			Boolean isRowRecordType1 = row.attr("abs:bgcolor").contains(
					"#EEEECC");
			if (isRowRecordType1) {
				logger.debug("Parsowany jest rekord:" + row.text());

				Elements fields = row.select("td");
				RegistryRecord rr = new RegistryRecord();
				for (int i = 0; i < fields.size(); i++) {
					if (i == 0) {
						rr.setLp(new Integer(fields.get(i).text()));

					}

					if (i == 1) {
						String nrRejestrowy = fields.get(i).text();
						rr.setNrRejestrowy(nrRejestrowy);
					}
					if (i == 2) {
						String firma = fields.get(i).text();
						rr.setFirma(firma);
					}
					if (i == 3) {
						String siedziba = fields.get(i).text();
						rr.setSiedziba(siedziba);
					}
					if (i == 4) {
						String wojwodztwo = fields.get(i).text();
						rr.setWojwodztwo(wojwodztwo);
					}
					if (i == 5) {
						String nip = fields.get(i).text();
						rr.setNip(nip);
					}
					if (i == 6) {
						String regon = fields.get(i).text();
						rr.setRegon(regon);

					}
					if (i == 7) {
						String id = fields.get(i).select("a[href]")
								.attr("href").toString();
						id = id.substring(id.lastIndexOf("=") + 1);
						rr.setId_pdm(id);

					}
				}
				recordRows.add(rr);
				logger.info("Dodano rekord: " + rr.toString());
			}
			Boolean isRowRecordType2 = row.attr("abs:bgcolor").contains(
					"#EEEEBB");
			if (isRowRecordType2) {
				logger.debug("Parsowany jest rekord: " + row.text());
				Elements fields = row.select("td");
				RegistryRecord rr = new RegistryRecord();
				for (int i = 0; i < fields.size(); i++) {
					if (i == 0) {
						rr.setLp(new Integer(fields.get(i).text()));

					}

					if (i == 1) {
						String nrRejestrowy = fields.get(i).text();
						rr.setNrRejestrowy(nrRejestrowy);
					}
					if (i == 2) {
						String firma = fields.get(i).text();
						rr.setFirma(firma);
					}
					if (i == 3) {
						String siedziba = fields.get(i).text();
						rr.setSiedziba(siedziba);
					}
					if (i == 4) {
						String wojwodztwo = fields.get(i).text();
						rr.setWojwodztwo(wojwodztwo);
					}
					if (i == 5) {
						String nip = fields.get(i).text();
						rr.setNip(nip);
					}
					if (i == 6) {
						String regon = fields.get(i).text();
						rr.setRegon(regon);

					}
					if (i == 7) {
						String id = fields.get(i).select("a[href]")
								.attr("href").toString();
						id = id.substring(id.lastIndexOf("=") + 1);
						rr.setId_pdm(id);
					}
				}
				recordRows.add(rr);
				logger.info("Dodano rekord: " + rr.toString());
			}
		}

		return recordRows;
	}

	/*
	 * Operacja zwraca zawartość rejestru dla podanych metadanych stron
	 */
	public List<RegistryRecord> getPageContents(List<RegistryPage> pages)
			throws IOException {

		List<RegistryRecord> records = new ArrayList<RegistryRecord>();

		for (int i = 0; i < pages.size(); i++) {
			String url = "http://www.rzseie.gios.gov.pl/szukaj_rzseie.php?limit="
					+ pages.get(i).getLimit()
					+ "&szukaj=Wyszukaj&nazwa_podmiotu=&nr_rej=&row_nr="
					+ pages.get(i).getRow_nr() + "&nr_nip=";
			logger.debug("url: "+url);
			Connection conn =Jsoup.connect(url);
			conn.timeout(120000);
			Document doc = conn.get();
			records.addAll(getRecordsInPage(doc));

		}
		return records;
	}

	private String getSubString(String left, String right, String string) {
		int start = string.indexOf(left);
		int stop = string.indexOf(right);

		String sub = "0";
		try {
			sub = string.substring(start + right.length(), stop);

		} catch (Exception e) {
			logger.debug(e.getMessage() + " start: " + start + " stop:" + stop
					+ " string: " + string);

		}

		return sub;

	}
}
