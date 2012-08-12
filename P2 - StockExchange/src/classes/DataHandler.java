package classes;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import exceptions.DuplicateCompanyException;
import exceptions.InsufficientSharesToBuyException;
import exceptions.InvalidCompanyException;
import exceptions.InvalidShareholderException;
import exceptions.NoSuchShareException;
import exceptions.UnsoldSharesOfException;

public class DataHandler {
	
	public static Scanner in = new Scanner(System.in);

	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final DecimalFormat twoDigit = new DecimalFormat("#,##0.00");

	private static ArrayList<Company> companies = new ArrayList<Company>();
	private static ArrayList<Shareholder> shareholders = new ArrayList<Shareholder>();
	private static ArrayList<Share> shares = new ArrayList<Share>();
	private static ArrayList<Transaction> transactions = new ArrayList<Transaction>();

	public static ArrayList<Company> getCompanies() {
		return companies;
	}

	public static void setCompanies(ArrayList<Company> companies) {
		DataHandler.companies = companies;
	}

	public static void setShareholders(ArrayList<Shareholder> shareholders) {
		DataHandler.shareholders = shareholders;
	}

	public static void setShares(ArrayList<Share> shares) {
		DataHandler.shares = shares;
	}

	public static void setTransactions(ArrayList<Transaction> transactions) {
		DataHandler.transactions = transactions;
	}

	public static ArrayList<Shareholder> getShareholders() {
		return shareholders;
	}

	public static ArrayList<Share> getShares() {
		return shares;
	}

	public static ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public static void addCompany(Company c) throws DuplicateCompanyException{
		for(int i=0; i < companies.size(); i++){
			if(c.getCompanySymbol().equalsIgnoreCase(companies.get(i).getCompanySymbol()))
				throw new DuplicateCompanyException();
		}

		companies.add(c);
	}

	public static int addShareholder(String shareholderName){
		Shareholder s = new Shareholder(shareholders.size() + 1, shareholderName);
		shareholders.add(s);
		return s.getShareholderId();
	}

	public static int addShareToCompany(String companySymbol, int amountOfShares) throws InvalidCompanyException{
		for(int i=0; i < companies.size(); i++){
			if(companySymbol.equalsIgnoreCase(companies.get(i).getCompanySymbol())){
				Company c = companies.get(i);
				c.addShares(amountOfShares);
				companies.set(i, c);
				return c.getAmountOfShares();
			}
		}

		throw new InvalidCompanyException();
	}

	public static String buyShares(int shareholderId, String companySymbol, int amountOfShares) throws InvalidShareholderException, InvalidCompanyException, InsufficientSharesToBuyException{
		for(int i=0; i < shareholders.size(); i++){
			if(shareholderId == (shareholders.get(i).getShareholderId()) && shareholders.get(i).isShareHolderActive()){
				for(int j=0; j < companies.size(); j++){
					if(companySymbol.equalsIgnoreCase(companies.get(j).getCompanySymbol())){
						Shareholder s = shareholders.get(i);
						Company c = companies.get(j);
						Share share;
						if(amountOfShares > (c.getAmountOfShares() - c.getSharesSold())) 
							throw new InsufficientSharesToBuyException();
						try {
							int shareIndex = searchShare(s.getShareholderId(), c.getCompanySymbol());
							share = shares.get(shareIndex);
							share.addAmount(amountOfShares);
							shares.set(shareIndex, share);
						} catch (NoSuchShareException e) {
							share = new Share(shareholderId, companySymbol, amountOfShares);
							shares.add(share);
						}
						
						c.addSharesSold(amountOfShares);
						companies.set(j, c);
						transactions.add(new Transaction(Transaction.BUY, shareholderId, c.getCompanySymbol(), amountOfShares, c.getSharePrice(), new Date()));

						String resume = "Transaction sucessful:\n" +
								s.getShareholderName() + " now owns " + share.getAmountOfShares() + " of shares from " + c.getCompanyName();

						return resume;
					}
				}
				throw new InvalidCompanyException();
			}
		}
		throw new InvalidShareholderException();

	}

	public static void removeCompany(String companySymbol) throws InvalidCompanyException, UnsoldSharesOfException{
		for(int i=0; i < shares.size(); i++){
			if(companySymbol.equalsIgnoreCase(shares.get(i).getCompanySymbol()))
				throw new UnsoldSharesOfException();
		}

		for(int i=0; i < companies.size(); i++){
			if(companySymbol.equalsIgnoreCase(companies.get(i).getCompanySymbol())){
				companies.remove(i);
				return;
			}
		}

		throw new InvalidCompanyException();
	}

	public static void removeShareholder(int shareholderId) throws InvalidShareholderException, UnsoldSharesOfException{
		for(int i=0; i < shares.size(); i++){
			if(shareholderId == (shares.get(i).getShareholderId()))
				throw new UnsoldSharesOfException();
		}

		for(int i=0; i < shareholders.size(); i++){
			if(shareholderId == (shareholders.get(i).getShareholderId()) && shareholders.get(i).isShareHolderActive()){
				Shareholder s = shareholders.get(i);
				s.setInactive();
				shareholders.set(i, s);
				return;
			}
		}

		throw new InvalidShareholderException();
	}
	
	public static String sellShare(int shareholderId, String companySymbol, int amountOfShares) 
			throws InvalidCompanyException, InvalidShareholderException, NoSuchShareException, InsufficientSharesToBuyException{
		int cIndex = searchCompany(companySymbol);
		int sIndex = searchShareholder(shareholderId);
		
		if(cIndex == -1)
			throw new InvalidCompanyException();
		if(sIndex == -1)
			throw new InvalidShareholderException();
		
		Company c = companies.get(cIndex);
		//Shareholder s = shareholders.get(sIndex);
		int shareIndex = searchShare(shareholderId, companySymbol);
		Share share = shares.get(shareIndex);
		
		if(amountOfShares > share.getAmountOfShares())
			throw new InsufficientSharesToBuyException();
		
		ArrayList<Transaction> transactionList = getTransactions(shareholderId, companySymbol);
		LLQueue<SShare> sharesQueue = getSharesQueue(transactionList);
		
		int amountToSell = amountOfShares;
		double capitalGain = 0;
		
		while(amountToSell > 0){
			if(amountToSell > sharesQueue.front().amount){
				SShare shareDequeued = sharesQueue.dequeue();
				amountToSell -= shareDequeued.amount;
				capitalGain += shareDequeued.amount*(c.getSharePrice() - shareDequeued.price);
			} else {
				sharesQueue.front().subtractAmount(amountToSell);
				capitalGain += amountToSell*(c.getSharePrice() - sharesQueue.front().price);
				amountToSell = 0;
			}
		}
		
		c.subtractSharesSold(amountOfShares);
		share.substractAmount(amountOfShares);
		
		companies.set(cIndex, c);
		if(share.getAmountOfShares() > 0)
			shares.set(shareIndex, share);
		else
			shares.remove(shareIndex);
		
		transactions.add(new Transaction(Transaction.SELL, shareholderId, c.getCompanySymbol(), amountOfShares, c.getSharePrice(), new Date()));
		return twoDigit.format(capitalGain);
	}

	public static String showReportOfCompany(String companySymbol) throws InvalidCompanyException{
		for(int i=0; i < companies.size(); i++){
			if(companySymbol.equalsIgnoreCase(companies.get(i).getCompanySymbol())){
				Company c = companies.get(i);
				String report = c.getCompanyName() + " - " + c.getCompanySymbol() + "\n" +
								"has " + c.getAmountOfShares() + " of shares at $" + c.getSharePrice() + "\n" +
								"with " + c.getSharesSold() + " sold shares.";
				return report;				
			}
		}

		throw new InvalidCompanyException();
	}
	
	public static String showReportOfShareholder(int shareholderId) throws InvalidShareholderException{
		for(int i=0; i < shareholders.size(); i++){
			if(shareholderId == (shareholders.get(i).getShareholderId()) && shareholders.get(i).isShareHolderActive()){
				Shareholder s = shareholders.get(i);
				String report = s.getShareholderId() + " - " + s.getShareholderName() + "\n" +
								"Shares owned: \n";
				for(int j=0; j < shares.size(); j++){
					if(shareholderId == shares.get(j).getShareholderId()){
						Share share = shares.get(j);
						Company c = companies.get(searchCompany(share.getCompanySymbol()));
						report = report.concat(c.getCompanySymbol() + " - " + share.getAmountOfShares() + " at $" + c.getSharePrice() + "\n");
					}
				}
				return report;
			}
		}
		
		throw new InvalidShareholderException();
	}
	
	public static String showReportOfShare(String companySymbol) throws InvalidCompanyException{
		for(int i=0; i < companies.size(); i++){
			if(companySymbol.equalsIgnoreCase(companies.get(i).getCompanySymbol())){
				Company c = companies.get(i);
				String report = c.getCompanyName() + " - " + c.getCompanySymbol() + "\nShare:\n";
				for(int j=0; j < shares.size(); j++){
					if(companySymbol.equalsIgnoreCase(shares.get(j).getCompanySymbol())){
						Share share = shares.get(j);
						report = report.concat(share.getShareholderId() + "-" + shareholders.get(searchShareholder(share.getShareholderId())).getShareholderName() + " owns " + share.getAmountOfShares()) + "\n";
					}
				}
				
				return report;
			}
		}
		
		throw new InvalidCompanyException();
	}
	
	public static String splitCompany(String companySymbol) throws InvalidCompanyException{
		int cIndex = searchCompany(companySymbol);
		if(cIndex == -1)
			throw new InvalidCompanyException();
		Company c = companies.get(cIndex);
		c.split();
		for(int i=0; i < transactions.size(); i++){
			if(transactions.get(i).getCompanySymbol().equalsIgnoreCase(companySymbol)){
				Transaction t = transactions.get(i);
				t.split();
				transactions.set(i, t);
			}
		}
		for(int i=0; i < shares.size(); i++){
			if(shares.get(i).getCompanySymbol().equalsIgnoreCase(companySymbol)){
				Share share = shares.get(i);
				share.split();
				shares.set(i, share);
			}
		}
		
		return "Split sucessful\nCompany now has " + c.getAmountOfShares() + " of shares at $" + c.getSharePrice();
	}
	
	public static void updateShareValue(String companySymbol, double price) throws InvalidCompanyException{
		for(int i=0; i < companies.size(); i++){
			if(companySymbol.equalsIgnoreCase(companies.get(i).getCompanySymbol())){
				Company c = companies.get(i);
				c.setSharePrice(price);
				companies.set(i, c);
				return;
			}
		}
		
		throw new InvalidCompanyException();
	}

	private static int searchShare(int shareholderID, String companySymbol) throws NoSuchShareException{
		for(int i=0; i < shares.size(); i++){
			Share s = shares.get(i);
			if(s.getShareholderId() == shareholderID && s.getCompanySymbol().equalsIgnoreCase(companySymbol))
				return i;	
		}

		throw new NoSuchShareException();
	}
	
	private static int searchCompany(String companySymbol){
		for(int i=0; i < companies.size(); i++){
			if(companySymbol.equalsIgnoreCase(companies.get(i).getCompanySymbol())){
				return i;
			}
		}
		return -1;
	}
	
	private static int searchShareholder(int shareholderId){
		for(int i=0; i < shareholders.size(); i++){
			if(shareholderId == shareholders.get(i).getShareholderId() && shareholders.get(i).isShareHolderActive()){
				return i;
			}
		}
		return -1;
	}

	private static ArrayList<Transaction> getTransactions(int shareholderId, String companySymbol){
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		for(int i=0; i < transactions.size(); i++){
			Transaction t = transactions.get(i);
			if(t.getShareholderId() == shareholderId && t.getCompanySymbol().equalsIgnoreCase(companySymbol))
				list.add(t);
		}
		
		return list;
	}
	
	private static LLQueue<SShare> getSharesQueue(ArrayList<Transaction> transactionList){
		LLQueue<SShare> sharesQueue = new LLQueue<SShare>();
		for(int i=0; i < transactionList.size(); i++){
			Transaction t = transactionList.get(i);
			if(t.getBuyOrSell() == Transaction.BUY){
				sharesQueue.enqueue(new SShare(t.getAmount(), t.getPrice()));
			} else {
				int tsamount = t.getAmount();
				while(tsamount > 0) {
					if(tsamount > sharesQueue.front().amount){
						tsamount -= sharesQueue.dequeue().amount;
					} else {
						sharesQueue.front().subtractAmount(tsamount);
						tsamount = 0;
					}
				}
			}
		}
		
		return sharesQueue;
	}
	
	private static class SShare {
		int amount;
		double price;
		
		public SShare(int amount, double price) {
			super();
			this.amount = amount;
			this.price = price;
		}
		
		public void subtractAmount(int amount){
			this.amount -= amount;
		}
		
	}

}
