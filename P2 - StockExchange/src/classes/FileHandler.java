package classes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;

public class FileHandler {

	private static RandomAccessFile companies;
	private static RandomAccessFile shareholders;
	private static RandomAccessFile shares;
	private static RandomAccessFile transactions;


	public static void writeAllData() throws IOException{
		companies = new RandomAccessFile("companies", "rw");
		shareholders= new RandomAccessFile("shareholders", "rw");
		shares = new RandomAccessFile("shares", "rw");
		transactions= new RandomAccessFile("transactions", "rw");
		
		writeCompaniesFile(companies, DataHandler.getCompanies());
		writeShareholdersFile(shareholders, DataHandler.getShareholders());
		writeSharesFile(shares, DataHandler.getShares());
		writeTransactionsFile(transactions, DataHandler.getTransactions());
		
		closeAllFiles();
	}

	private static void writeTransactionsFile(RandomAccessFile out,
			ArrayList<Transaction> list) throws IOException {
		out.seek(0);
		for(int i=0; i < list.size(); i++){
			Transaction t = list.get(i);
			out.write((""+t.getBuyOrSell()+"\n").getBytes());
			out.write((""+t.getShareholderId()+"\n").getBytes());
			out.write((t.getCompanySymbol()+"\n").getBytes());
			out.write((""+t.getAmount()+"\n").getBytes());
			out.write((""+t.getPrice()+"\n").getBytes());
			out.write((DataHandler.dateFormat.format(t.getDate())+"\n").getBytes());
		}
	}

	private static void writeSharesFile(RandomAccessFile out,
			ArrayList<Share> list) throws IOException {
		out.seek(0);
		for(int i=0; i < list.size(); i++){
			Share s = list.get(i);
			out.write((""+s.getShareholderId()+"\n").getBytes());
			out.write((s.getCompanySymbol()+"\n").getBytes());
			out.write((""+s.getAmountOfShares()+"\n").getBytes());
		}	
	}

	private static void writeShareholdersFile(RandomAccessFile out,
			ArrayList<Shareholder> list) throws IOException {
		out.seek(0);
		for(int i=0; i < list.size(); i++){
			Shareholder s = list.get(i);
			out.write((""+s.getShareholderId()+"\n").getBytes());
			out.write((s.getShareholderName()+"\n").getBytes());
			out.write((""+s.isShareHolderActive()+"\n").getBytes());
		}
	}

	private static void writeCompaniesFile(RandomAccessFile out,
			ArrayList<Company> list) throws IOException {
		out.seek(0);
		for(int i=0; i < list.size(); i++){
			Company c = list.get(i);
			out.write((c.getCompanyName()+"\n").getBytes());
			out.write((c.getCompanySymbol()+"\n").getBytes());
			out.write((""+c.getAmountOfShares()+"\n").getBytes());
			out.write((""+c.getSharePrice()+"\n").getBytes());
		}	
	}

	public static void readAllData() throws IOException, ClassNotFoundException, NumberFormatException, ParseException{
		companies = new RandomAccessFile("companies", "rw");
		shareholders= new RandomAccessFile("shareholders", "rw");
		shares = new RandomAccessFile("shares", "rw");
		transactions = new RandomAccessFile("transactions", "rw");
		
		DataHandler.setCompanies(readCompaniesFile(companies));
		DataHandler.setShareholders(readShareholdersFile(shareholders));
		DataHandler.setShares(readSharesFile(shares));
		DataHandler.setTransactions(readTransactionsFile(transactions));
		
		closeAllFiles();
	}

	private static ArrayList<Company> readCompaniesFile(
			RandomAccessFile in) throws NumberFormatException, IOException {
		ArrayList<Company> list = new ArrayList<Company>();
		in.seek(0);
		while(in.getFilePointer() < in.length()){
			list.add(
					new Company(
							in.readLine(), 
							in.readLine(), 
							Integer.parseInt(in.readLine()),
							Double.parseDouble(in.readLine())
							)
					);
		}
		return list;
	}
	
	private static ArrayList<Shareholder> readShareholdersFile(
			RandomAccessFile in) throws NumberFormatException, IOException {
		ArrayList<Shareholder> list = new ArrayList<Shareholder>();
		in.seek(0);
		while(in.getFilePointer() < in.length()){
			list.add(
					new Shareholder(
							Integer.parseInt(in.readLine()), 
							in.readLine(),
							Boolean.parseBoolean(in.readLine())
							)
					);
		}
		return list;
	}
	
	private static ArrayList<Share> readSharesFile(
			RandomAccessFile in) throws NumberFormatException, IOException {
		ArrayList<Share> list = new ArrayList<Share>();
		in.seek(0);
		while(in.getFilePointer() < in.length()){
			list.add(
					new Share(
							Integer.parseInt(in.readLine()), 
							in.readLine(),
							Integer.parseInt(in.readLine())
							)
					);
		}
		return list;
	}
	
	private static ArrayList<Transaction> readTransactionsFile(
			RandomAccessFile in) throws NumberFormatException, IOException, ParseException {
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		in.seek(0);
		while(in.getFilePointer() < in.length()){
			list.add(
					new Transaction(
							Integer.parseInt(in.readLine()), 
							Integer.parseInt(in.readLine()),
							in.readLine(),
							Integer.parseInt(in.readLine()),
							Double.parseDouble(in.readLine()),
							DataHandler.dateFormat.parse(in.readLine())
							)
					);
		}
		return list;
	}
	
	public static void formatAllFiles(){
		try {
			companies = new RandomAccessFile("companies", "rw");
			shareholders= new RandomAccessFile("shareholders", "rw");
			shares = new RandomAccessFile("shares", "rw");
			transactions= new RandomAccessFile("transactions", "rw");

			companies.setLength(0);
			shareholders.setLength(0);
			shares.setLength(0);
			transactions.setLength(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void closeAllFiles() throws IOException{
		companies.close();
		shareholders.close();
		shares.close();
		transactions.close();
	}

}
