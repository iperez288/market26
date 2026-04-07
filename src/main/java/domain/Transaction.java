package domain;

import java.util.Date;

public class Transaction {

	public enum TransactionType {
		extraction, income, sale, bought
	}
	
	private User usuario;

	private Integer id;
	private Double amount;
	private Date date;
	private TransactionType type;

	public Transaction() {
	}

	public Transaction(Integer id, Double amount, Date date, TransactionType type) {
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}
}