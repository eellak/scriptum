package gr.scriptum.eprotocol.diavgeia.parsers;

/**
 * General class representing any reference data with a UID and a description.
 * The class is used to get reference data from DIAVGEIA.
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */

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
