package gr.scriptum.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import gr.scriptum.domain.ProtocolCorrespondent;


/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table(name="company")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String address;
	private String city;
	private Date createDt;
	private String createUser;
	private String description;
	private String email;
	private String fax;
	private String irs;
	private Boolean isGoverment;
	private String mobile;
	private String name;
	private String postcode;
	private String prefecture;
	private String street;
	private String streetNo;
	private String telephone;
	private Date updateTs;
	private Integer updateUser;
	private String vatNo;
	private String web;
	private String webService;
	private String code;
	private CompanyType companyType;
	private Set<Contact> contacts;
	private Set<ProtocolCorrespondent> protocolCorrespondents;

	public Company() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(length=512)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Column(length=100)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_dt")
	public Date getCreateDt() {
		return this.createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}


	@Column(name="create_user", length=16)
	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	@Column(length=512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(length=256)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(length=32)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}


	@Column(length=45)
	public String getIrs() {
		return this.irs;
	}

	public void setIrs(String irs) {
		this.irs = irs;
	}


	@Column(name="is_goverment")
	public Boolean getIsGoverment() {
		return this.isGoverment;
	}

	public void setIsGoverment(Boolean isGoverment) {
		this.isGoverment = isGoverment;
	}


	@Column(length=32)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	@Column(length=256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(length=16)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	@Column(length=100)
	public String getPrefecture() {
		return this.prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}


	@Column(length=100)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}


	@Column(length=45)
	public String getStreetNo() {
		return this.streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}


	@Column(length=32)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_ts")
	public Date getUpdateTs() {
		return this.updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}


	@Column(name="update_user")
	public Integer getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}


	@Column(length=9)
	public String getVatNo() {
		return this.vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}


	@Column(length=256)
	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}


	@Column(name="web_service", length=256)
	public String getWebService() {
		return this.webService;
	}

	public void setWebService(String webService) {
		this.webService = webService;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	//bi-directional many-to-one association to CompanyType
	@ManyToOne
	@JoinColumn(name="company_type_id")
	public CompanyType getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}


	//bi-directional many-to-one association to Contact
	@OneToMany(mappedBy="company")
	public Set<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setCompany(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setCompany(null);

		return contact;
	}


	@OneToMany(mappedBy = "company")
	public Set<ProtocolCorrespondent> getProtocolCorrespondents() {
	    return protocolCorrespondents;
	}


	public void setProtocolCorrespondents(Set<ProtocolCorrespondent> param) {
	    this.protocolCorrespondents = param;
	}

}