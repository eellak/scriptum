package gr.scriptum.eprotocol.pdf;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.Protocol;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode;
import gr.scriptum.domain.Users;



public abstract class AProtocolPrinter {
//	Map<Date, ProtocolNode> bookData = new TreeMap<Date, ProtocolNode>();
	protected List<ProtocolNode> bookData = null;
	ProtocolBookInfo bookInfo;
	

	public AProtocolPrinter(){}
	
	public AProtocolPrinter(ProtocolBookInfo bookInfo2) {
		this.bookInfo = bookInfo2;
	}

	public final List<ProtocolNode> getBookData() {
		return bookData;
	}
	public final void setBookData(List<ProtocolNode> bookData) {
		this.bookData = bookData;
	}
	public final ProtocolBookInfo getBookInfo() {
		return bookInfo;
	}
	public final void setBookInfo(ProtocolBookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	
	public void addProtocolNode(ProtocolNode node) {
		bookData.add(node);		
	}
	
	public  String formatDate(Date date ){
		  SimpleDateFormat formatter = new SimpleDateFormat(bookInfo.getDateFormat());
		  if( date == null )
			  return "";
		  else
			  return formatter.format(date);
	}
    
	
	
	private static String toCDATA( Contact contact){
		return "Name=" + contact.getName() +  ", middlename=" + contact.getMiddlename() + ", surname=" + contact.getSurname() 
        + ", organization=" + contact.getOrganization()
		+ ", department=" + contact.getDepartment() + ", job=" + contact.getJob() 
		+ ", address="+ contact.getAddress()
		+ ", street=" + contact.getStreet() + ", streetNo=" + contact.getStreetNo() + ", prefecture=" + contact.getPrefecture()
		+ ", postcode=" + contact.getPostcode() + ", city=" + contact.getCity()
		+ ", country=" + contact.getCountry() 
		+ ", email=" + contact.getEmail() + ", telephone=" + contact.getTelephone() + ", fax=" + contact.getFax() + ", mobile=" + contact.getMobile()		
		+ ", web=" + contact.getWeb();	
	}
	
	
	
	public static String getContact(Protocol inProto) {
		Contact contact = inProto.getSender().getContact();
		String contactDetails = "";
		if( contact != null ){
			contactDetails =  toCDATA( contact );
		}
		
		return contactDetails;
	}	
	
	public static ArrayList<String> getReceipients(Protocol outProto) {
		//TODO: return ProtocolCorrespondent isntances
		throw new UnsupportedOperationException("Not implemented yet");
	}


 	
	public static String getDistributionMethod( ProtocolNode protoNode ){
		DistributionMethod dmethod = protoNode.getDistributionMethod();
		String distribution = "";
		if( dmethod != null ){
			distribution = dmethod.getDescription();
		}		
		
		return distribution;
	}
	

	
	public static ArrayList<String> getAttachements( ProtocolNode protoNode ){
		Set<ProtocolDocument> docSet =  protoNode.getProtocolDocuments();
		ArrayList<String> documents = new ArrayList<String>();
		if( docSet != null ){
			for( ProtocolDocument doc : docSet ){
				if(doc != null )
					documents.add(doc.getDocumentName());
			}
		}
		return documents;
	}
	
	
	public static String getUserCreated( ProtocolNode protoNode ){
	
		Integer userCreatedId = protoNode.getCreateUserId();
		
		UsersDAO uDao = new UsersDAO();
		
		Users user = uDao.findById( userCreatedId, false);
		
		return user.getName() + " " + user.getSurname();
	}
		
		
	
	
	
	
	
    public abstract File produceProtocolBook() throws Exception;
    
}
