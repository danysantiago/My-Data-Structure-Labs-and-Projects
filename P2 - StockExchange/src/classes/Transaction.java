package classes;

import java.util.Date;

public class Transaction {

	public static final int BUY = 1;
	public static final int SELL = -1;
	
	private int buyOrSell;
	private int shareholderId;
	private String companySymbol;
	private int amount;
	private double price;
	Date date;
	
	public Transaction(int buyOrSell, int shareholderId, String companySymbol,
			int amount, double price, Date date) {
		super();
		this.buyOrSell = buyOrSell;
		this.shareholderId = shareholderId;
		this.companySymbol = companySymbol;
		this.amount = amount;
		this.price = price;
		this.date = date;
	}

	public int getBuyOrSell() {
		return buyOrSell;
	}

	public int getShareholderId() {
		return shareholderId;
	}

	public String getCompanySymbol() {
		return companySymbol;
	}

	public int getAmount() {
		return amount;
	}

	public double getPrice() {
		return price;
	}

	public Date getDate() {
		return date;
	}
	
	public void split(){
		amount = amount*2;
		price = price/2;
	}
	

	@Override
	public String toString() {
		return "["+buyOrSell+","+shareholderId+","+companySymbol+","+amount+","+price+","+DataHandler.dateFormat.format(date)+"]";
	}
}
