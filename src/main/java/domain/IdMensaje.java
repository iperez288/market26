package domain;

import java.io.Serializable;
import java.util.Objects;

public class IdMensaje implements Serializable {
	
	int messageNumber;
	Conversacion conversacion;
	
	public IdMensaje() {}

	@Override
	public int hashCode() {
		return Objects.hash(messageNumber, conversacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdMensaje other = (IdMensaje) obj;
		return messageNumber == other.messageNumber && Objects.equals(conversacion, other.conversacion);
	}
	
	
}