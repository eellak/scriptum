package gr.scriptum.eprotocol.diavgeia;

/**
 * Object reflected to SCRIPTUM Database that keeps information about EIDOS APOFASIS
 * @author Mike Mountrakis mountrakis@uit.gr
 */
public class DiavgeiaEidosApofasis {
	public Integer id;
	public String  uId;
	public String  description;

	public DiavgeiaEidosApofasis() {

	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
