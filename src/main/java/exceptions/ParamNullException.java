package exceptions;

public class ParamNullException extends IllegalArgumentException{

	private static final long serialVersionUID = 1L;
	
	public ParamNullException() {
		super();
	}
	
	public ParamNullException(String s)
	{
	    super(s);
	}

}
