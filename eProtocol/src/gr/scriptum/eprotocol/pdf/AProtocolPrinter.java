package gr.scriptum.eprotocol.pdf;

import gr.scriptum.dao.UsersDAO;
import gr.scriptum.domain.Contact;
import gr.scriptum.domain.DistributionMethod;
import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.OutgoingRecipient;
import gr.scriptum.domain.OutgoingRecipientId;
import gr.scriptum.domain.ProtocolDocument;
import gr.scriptum.domain.ProtocolNode;
import gr.scriptum.domain.Users;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



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
	
	
	
	public static String getContact( IncomingProtocol inProto){
		Contact contact = inProto.getContact();
		String contactDetails = "";
		if( contact != null ){
			contactDetails =  toCDATA( contact );
		}
		
		return contactDetails;
	}	
	
	public static ArrayList<String> getReceipients( OutgoingProtocol outProto){
	    Set<OutgoingRecipient>  receipients =  outProto.getOutgoingRecipients();
	    ArrayList<String> receipientsStr = new ArrayList<String>();
	  
		if( receipients != null ){
			for(OutgoingRecipient recipient :  receipients){
				OutgoingRecipientId id = recipient.getId();
				if( id != null ){
					Contact recipientContact = id.getContact();
					if( recipientContact != null ){
						receipientsStr.add( toCDATA( recipientContact ) );
					}
				}
			}
		}		
		
		return receipientsStr;
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
