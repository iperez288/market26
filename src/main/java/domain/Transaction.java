package domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum TransactionType {
		extraction, income, sale, bought
	}
	
	@ManyToOne
	private Buyer usuario;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Float amount;
	private Date date;
	private TransactionType type;

	public Transaction() {
		super();
	}

	public Transaction(Float amount, Date date, TransactionType type, Buyer usuario) {
		this.amount = amount;
		this.date = date;
		this.type = type;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
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

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(Buyer usuario) {
		this.usuario = usuario;
	}
}