package classes;


public class Share {
	
	private int shareholderId;
	private String companySymbol;
	private int amountOfShares;
	
	public Share(int shareholderId, String companySymbol, int amountOfShares) {
		super();
		this.shareholderId = shareholderId;
		this.companySymbol = companySymbol;
		this.amountOfShares = amountOfShares;
	}

	public int getShareholderId() {
		return shareholderId;
	}

	public String getCompanySymbol() {
		return companySymbol;
	}

	public int getAmountOfShares() {
		return amountOfShares;
	}

	public void addAmount(int amountOfShares) {
		this.amountOfShares += amountOfShares;
	}
	
	public void substractAmount(int amountOfShares) {
		this.amountOfShares -= amountOfShares;
	}
	
	public void split(){
		amountOfShares = amountOfShares*2;
	}

}
