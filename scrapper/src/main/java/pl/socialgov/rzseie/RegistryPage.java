package pl.socialgov.rzseie;

public class RegistryPage {
	private String row_nr;
	private String limit;
	public RegistryPage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegistryPage(String row_nr, String limit) {
		super();
		this.row_nr = row_nr;
		this.limit = limit;
	}
	public String getRow_nr() {
		return row_nr;
	}
	public void setRow_nr(String row_nr) {
		this.row_nr = row_nr;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "RegistryPage [row_nr=" + row_nr + ", limit=" + limit + "]";
	}

}
