package gr.scriptum.eprotocol.diavgeia.parsers;

public class DiavgeiaEntry {
	String uid;
	String description;

	public DiavgeiaEntry() {

	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "DiavgeiaEntry [uid=" + uid + ", description=" + description
				+ "]";
	}
	
	
}
