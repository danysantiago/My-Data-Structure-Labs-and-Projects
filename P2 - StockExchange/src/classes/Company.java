package classes;



public class Company {
	
	private String companyName;
	private String companySymbol;
	private int amountOfShares;
	private double sharePrice;
	private int sharesSold;
	
	public Company(String companyName, String companySymbol,
			int amountOfShares, double sharePrice) {
		super();
		this.companyName = companyName;
		this.companySymbol = companySymbol.toUpperCase();
		this.amountOfShares = amountOfShares;
		this.sharePrice = sharePrice;
		this.sharesSold = 0;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCompanySymbol() {
		return companySymbol;
	}

	public int getAmountOfShares() {
		return amountOfShares;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}

	public int getSharesSold() {
		return sharesSold;
	}

	public void addShares(int amountOfShares) {
		this.amountOfShares += amountOfShares;
	}
	
	public void addSharesSold(int amountOfShares){
		this.sharesSold += amountOfShares;
	}

	public void subtractSharesSold(int amountOfShares) {
		this.sharesSold -= amountOfShares;
	}
	
	public void split(){
		amountOfShares = amountOfShares*2;
		sharePrice = sharePrice/2;
	}
	
	
	
}
