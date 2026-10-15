package domain;

public class SaleParameter {
	private String title;
	private String description;
	private int status;
	private float price;

	public SaleParameter(String title, String description, int status, float price) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}