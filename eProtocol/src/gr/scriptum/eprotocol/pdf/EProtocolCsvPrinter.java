package gr.scriptum.eprotocol.pdf;

import gr.scriptum.domain.IncomingProtocol;
import gr.scriptum.domain.OutgoingProtocol;
import gr.scriptum.domain.ProtocolNode;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public class EProtocolCsvPrinter extends AProtocolPrinter{



	@Override
	public File produceProtocolBook() throws Exception {

		BufferedWriter out = new BufferedWriter(new FileWriter(bookInfo.file));
		
		out.write("Direction,ProtocolNumber,ProtocolDate,Subject,Contact,Comments\n");
	    String element = "";
	    for(ProtocolNode node: bookData) {
	        System.out.println( node.toString() );
	        if( node.getDirection() == ProtocolNode.Direction.INCOMING ){
	        	IncomingProtocol inProto = (IncomingProtocol)node;
	        	element=  incomingProtocolToXml(  inProto );
	        }else{
	        	OutgoingProtocol outProto = (OutgoingProtocol)node;
	        	element =  outgoingProtocolToXml(  outProto );
	        }
	        out.write(element);
	        out.flush();
	    }
	    out.write("</bookData>\n");
	    
	    
	    out.close();
	    
	    
	    return new File(bookInfo.file);
	}
	
	
	private String incomingProtocolToXml( IncomingProtocol inProto ){
		final String DIRECTION = "ΕΙΣΕΡΧΟΜΕΝΟ"; 
	
		String contactDetails = getContact( inProto);
		
		String distribution = getDistributionMethod(inProto);
		
		String attInfo = "";
		ArrayList<String>  attachements = getAttachements( inProto );
		for(String attachement : attachements )
				attInfo +=  clearString(attachement) + "|";		
		
		//Direction,ProtocolNumber,ProtocolDate,ProtocolSeries,ProtocolYear,Distribution,Subject,Contact,Comments\n
		String line = DIRECTION + ","  
		              + inProto.getProtocolNumber() + ","  
		              + formatDate(inProto.getProtocolDate()) + ","  
		              + distribution + "," 
		              + clearString(inProto.getSubject())  + ","
		              + clearString(contactDetails) + "," 
		              + clearString( inProto.getComments() );
		
		
		return line;
	}
	
	
	private String outgoingProtocolToXml( OutgoingProtocol outProto ){
		final String DIRECTION = "ΕΞΕΡΧΟΜΕΝΟ"; 		
		
		ArrayList<String> receipients = getReceipients( outProto);
		String contactDetails = "";
		for(String receipient : receipients )
			contactDetails += clearString(receipient) + "|";
		
		String distribution = getDistributionMethod( outProto );
		
		String attInfo = "";
		ArrayList<String>  attachements = getAttachements( outProto );
		for(String attachement : attachements )
				attInfo +=  clearString(attachement) + "|";	
		
		//Direction,ProtocolNumber,ProtocolDate,ProtocolSeries,ProtocolYear,Distribution,Subject,Contact,Comments\n
		String line = DIRECTION + ","  
		              + outProto.getProtocolNumber() + ","  
		              + formatDate(outProto.getProtocolDate()) + ","  
		              + distribution + "," 
		              + clearString(outProto.getSubject())  + ","
		              + clearString(contactDetails) + "," 
		              + clearString( outProto.getComments() );
		
		return line;
	}	
		
		
	
	private String clearString(String s ){
		return s.replace(',', ' ');
	}
	

}
