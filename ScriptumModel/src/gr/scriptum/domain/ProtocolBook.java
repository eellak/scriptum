package gr.scriptum.domain;

import java.util.Date;

/**
 * Mike
 * 
 * @author mike 3/2/2011
 */

public class ProtocolBook {
	private Integer id;
	private String protocolSeries = "";
	private Integer isIncoming;
	private Integer protocolNumber;
	private Integer protocolYear;
	private String protocolPath;
	private Integer isActive;
	private String createUser;
	private Integer updateUser;
	private Date createDt;
	private Date updateTs;

	public ProtocolBook() {
	}

	public ProtocolBook(Integer id, String protocolSeries, Integer isIncoming,
			Integer protocolNumber, Integer protocolYear, String protocolPath,
			Integer isActive, String createUser, Integer updateUser,
			Date createDt, Date updateTs) {
		this.id = id;
		this.protocolSeries = protocolSeries;
		this.isIncoming = isIncoming;
		this.protocolNumber = protocolNumber;
		this.protocolYear = protocolYear;
		this.protocolPath = protocolPath;
		this.isActive = isActive;
		this.createUser = createUser;
		this.updateUser = updateUser;
		this.createDt = createDt;
		this.updateTs = updateTs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProtocolSeries() {
		return protocolSeries;
	}

	public void setProtocolSeries(String protocolSeries) {
		this.protocolSeries = protocolSeries;
	}

	public Integer getIsIncoming() {
		return isIncoming;
	}

	public void setIsIncoming(Integer isIncoming) {
		this.isIncoming = isIncoming;
	}

	public Integer getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(Integer protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Integer getProtocolYear() {
		return protocolYear;
	}

	public void setProtocolYear(Integer protocolYear) {
		this.protocolYear = protocolYear;
	}

	public String getProtocolPath() {
		return protocolPath;
	}

	public void setProtocolPath(String protocolPath) {
		this.protocolPath = protocolPath;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

}
