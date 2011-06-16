package gr.scriptum.eprotocol.diavgeia;

/**
 * Class that keeps information for Thematikes Enotites from diavgeia.gov.gr
 * @author Mike Mountrakis mountrakis@uit.gr
 *
 */


public class DiavgeiaThematikiEnotita {
	public Integer id;
	public String  uId;
	public String  description;

	public DiavgeiaThematikiEnotita() {

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
