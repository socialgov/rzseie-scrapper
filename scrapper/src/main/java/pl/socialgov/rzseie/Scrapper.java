package pl.socialgov.rzseie;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class Scrapper {

	public static Logger logger = LoggerFactory.getLogger(Scrapper.class);

	/**
	 * @param args
	 * @throws IOException
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws IOException {

		try {
			PageParser parser = new PageParser();

			List<RegistryPage> pages = parser.getRegistryPages();
			List<RegistryRecord> records = parser.getPageContents(pages);

			toXml(records);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private static void toXml(List<RegistryRecord> rr) throws IOException {
		XStream xstream = new XStream();
		xstream.alias("rekord", RegistryRecord.class);
		String xml = xstream.toXML(rr);
		File output = new File("rzseie.xml");
		logger.debug(xml);
		logger.info("Liczba pobranych rekordów: "+ rr.size());
		logger.info("Utworzono plik "+output.getName()+" z eksportem rejestru:"+ output.getAbsolutePath());
		FileUtils.writeStringToFile(output , xml);
	}
}
