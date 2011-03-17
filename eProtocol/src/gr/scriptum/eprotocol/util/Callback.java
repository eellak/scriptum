package gr.scriptum.eprotocol.util;

import org.zkoss.zk.ui.Component;

public class Callback {

	private Component caller = null;

	private String event = null;

	public Callback(Component caller, String event) {
		super();
		this.caller = caller;
		this.event = event;
	}

	public Component getCaller() {
		return caller;
	}

	public void setCaller(Component caller) {
		this.caller = caller;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
