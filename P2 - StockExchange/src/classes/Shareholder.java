package classes;


public class Shareholder {
	
	private int shareholderId;
	private String shareholderName;
	private boolean shareHolderActive;
	
	public Shareholder(int shareholderId, String shareholderName) {
		super();
		this.shareholderId = shareholderId;
		this.shareholderName = shareholderName;
		this.shareHolderActive = true;
	}

	public Shareholder(int shareholderId, String shareholderName, boolean shareHolderActive) {
		super();
		this.shareholderId = shareholderId;
		this.shareholderName = shareholderName;
		this.shareHolderActive = shareHolderActive;
	}

	public int getShareholderId() {
		return shareholderId;
	}

	public String getShareholderName() {
		return shareholderName;
	}

	public boolean isShareHolderActive() {
		return shareHolderActive;
	}

	public void setInactive() {
		this.shareHolderActive = false;
	}
	
	
	
	

}
