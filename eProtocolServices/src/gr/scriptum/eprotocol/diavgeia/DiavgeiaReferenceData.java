package gr.scriptum.eprotocol.diavgeia;

/**
 * Class that represents the additional Information a diavgeia.gov.gr request has to have
 * @author Mike Mountrakis mountrakis@uit.gr
 */
public class DiavgeiaReferenceData {
	String apofasiUid;
	String thematikiUid;

	public DiavgeiaReferenceData() {
	}

	public String getApofasiUid() {
		return apofasiUid;
	}

	public void setApofasiUid(String apofasiUid) {
		this.apofasiUid = apofasiUid;
	}

	public String getThematikiUid() {
		return thematikiUid;
	}

	public void setThematikiUid(String thematikiUid) {
		this.thematikiUid = thematikiUid;
	}

	@Override
	public String toString() {
		return "DiavgeiaReferenceData [apofasiUid=" + apofasiUid
				+ ", thematikiUid=" + thematikiUid + "]";
	}

}
