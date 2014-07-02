package pl.socialgov.rzseie;

public class RegistryRecord {

	private int lp;

	private String nrRejestrowy;

	private String firma;

	private String siedziba;

	private String wojwodztwo;

	private String nip;

	private String regon;

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

	public RegistryRecord() {

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

	@Override
	public String toString() {
		return "RegistryRecord [lp=" + lp + ", nrRejestrowy=" + nrRejestrowy
				+ ", firma=" + firma + ", siedziba=" + siedziba
				+ ", wojwodztwo=" + wojwodztwo + ", nip=" + nip + ", regon="
				+ regon + ", id_pdm=" + id_pdm + "]";
	}

}
