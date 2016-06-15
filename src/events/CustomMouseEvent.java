package events;

import java.util.EventObject;

public class CustomMouseEvent extends EventObject {

	private int x;
	private int y;
	private Object Source;
	
	
	public CustomMouseEvent(Object arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
