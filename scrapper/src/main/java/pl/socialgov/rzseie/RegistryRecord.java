package pl.socialgov.rzseie;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="rzseie")
public class RegistryRecord {
	@XmlElement(name="LiczbaPorzadkowa")
	private int lp;
	@XmlElement(name="nrRejestrowy")
	private String nrRejestrowy;
	@XmlElement(name="firma")
	private String firma;
	@XmlElement(name="siedziba")
	private String siedziba;
	@XmlElement(name="wojwodztwo")
	private String wojwodztwo;
	@XmlElement(name="nip")
	private String nip;
	@XmlElement(name="regon")
	private String regon;
	@XmlElement(name="id_pdm")
	private String id_pdm;
	
	public RegistryRecord(int lp, String nrRejestrowy, String firma,
			String siedziba, String wojwodztwo, String nip, String regon,
			String id_pdm) {
		super();
		this.lp = lp;
		this.nrRejestrowy = nrRejestrowy;
		this.firma = firma;
		this.siedziba = siedziba;
		this.wojwodztwo = wojwodztwo;
		this.nip = nip;
		this.regon = regon;
		this.id_pdm = id_pdm;
	}
	public RegistryRecord(){
		
	}
	public int getLp() {
		return lp;
	}
	public void setLp(int lp) {
		this.lp = lp;
	}
	public String getNrRejestrowy() {
		return nrRejestrowy;
	}
	public void setNrRejestrowy(String nrRejestrowy) {
		this.nrRejestrowy = nrRejestrowy;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getSiedziba() {
		return siedziba;
	}
	public void setSiedziba(String siedziba) {
		this.siedziba = siedziba;
	}
	public String getWojwodztwo() {
		return wojwodztwo;
	}
	public void setWojwodztwo(String wojwodztwo) {
		this.wojwodztwo = wojwodztwo;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getRegon() {
		return regon;
	}
	public void setRegon(String regon) {
		this.regon = regon;
	}
	public String getId_pdm() {
		return id_pdm;
	}
	public void setId_pdm(String id_pdm) {
		this.id_pdm = id_pdm;
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append("lp:"+this.lp);
		result.append("nrRejestrowy"+this.nrRejestrowy);
		result.append("firma"+this.firma);
		result.append("siedziba:"+this.siedziba);
		result.append("wojwodztwo:"+this.wojwodztwo);
		result.append("nip:"+this.nip);
		result.append("regon:"+this.regon);
		result.append("id_pdm:"+this.id_pdm);
		
		return result.toString();
		
	}

}
