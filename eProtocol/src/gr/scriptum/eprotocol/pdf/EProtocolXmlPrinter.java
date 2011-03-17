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


public class EProtocolXmlPrinter extends AProtocolPrinter {


	public EProtocolXmlPrinter(ProtocolBookInfo bookInfo ){
		super(bookInfo);
	}

	@Override
	public File produceProtocolBook() throws Exception {

		BufferedWriter out = new BufferedWriter(new FileWriter(bookInfo.file));

		out.write("<bookInfo>\n");
		out.write("<company>" + bookInfo.getCompany() + "</company>");
		out.write("<creator>" + bookInfo.getCreator() + "</creator>");
		out.write("<keywords>"+ bookInfo.getCsvKeywords() + "</keywords>");
		out.write("<subject>" + bookInfo.getSubject() + "</subject>");
		out.write("<title>"   + bookInfo.getTitle()   + "</title>");
		out.write("</bookInfo>\n\n\n");
		
		out.write("<bookData>\n");
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
		
		
		String contactDetails = getContact( inProto);
		
		String distribution = getDistributionMethod(inProto);
		
		String attInfo = "";
		ArrayList<String>  attachements = getAttachements( inProto );
		for(String attachement : attachements )
				attInfo += "<attachment>" + clearString(attachement) + "</attachment>";
		
		
		String element = "<incoming  protocolNumber= \"" + inProto.getProtocolNumber() + "\"\n" +
		                            "protocolDate=   \"" + formatDate(inProto.getProtocolDate()) + "\"\n" + 
			                        "protocolSeries= \"" + inProto.getProtocolSeries() + "\"\n" +
			                        "protocolYear=   \"" + inProto.getProtocolYear()   + "\">\n";
		
		
		String sender   = "<sender>"        + clearString(contactDetails) + "</sender>\n";
		String subject  = "<subject>"       + clearString(inProto.getSubject())   +"</subject>\n";
		String comments = "<comments>"      + clearString(inProto.getComments())  +"</comments>\n";
		String distr    = "<distribution>"  + clearString(distribution) + "</distribution>\n"; 
		String place    = "<place>"         + clearString(inProto.getIncomingPlace()) + "</place>\n";
		String attachments = "<attachments>\n" + attInfo + "</attachments>";
		String sxetiko  = "<relative>"      + clearString(inProto.getIncomingProtocolNumber()) + "</relative>\n";
	
		
		return element + 
		       sender +
		       subject +
		       comments+
		       distr+
		       place+
		       attachments+
		       sxetiko+
		       "</incoming>\n\n";
	}
	
	
	private String outgoingProtocolToXml( OutgoingProtocol outProto ){
		
		ArrayList<String> receipients = getReceipients( outProto);
		String contactDetails = "";
		for(String receipient : receipients )
			contactDetails += "<recipient>" 
                              + clearString(receipient)
                              + "</recipient>\n";
		
		String distribution = getDistributionMethod( outProto );
		
		String attInfo = "";
		ArrayList<String>  attachements = getAttachements( outProto );
		for(String attachement : attachements )
				attInfo += "<attachment>" + clearString(attachement) + "</attachment>";
		
		String element = "<outgoing  protocolNumber= \"" + outProto.getProtocolNumber() + "\"\n" +
		                            "protocolDate=   \"" + formatDate(outProto.getProtocolDate()) + "\"\n" + 
			                        "protocolSeries= \"" + outProto.getProtocolSeries() + "\"\n" +
			                        "protocolYear=   \"" + outProto.getProtocolYear()   + "\">\n";
		
		
		String sender   = "<recipients>"    + contactDetails + "</recipients>\n";
		String subject  = "<subject>"       + clearString(outProto.getSubject())   +"</subject>\n";
		String comments = "<comments>"      + clearString(outProto.getComments())  +"</comments>\n";
		String distr    = "<distribution>"  + clearString(distribution) + "</distribution>\n"; 
		String place    = "<place>"         + clearString(outProto.getOutgoingPlace()) + "</place>\n";
		String attachments = "<attachments>\n" + attInfo + "</attachments>";
		String sxetiko  = "<relative>"      + clearString(outProto.getRelativeProtocol()) + "</relative>\n";
	
		
		return element + 
		       sender +
		       subject +
		       comments+
		       distr+
		       place+
		       attachments+
		       sxetiko+
		       "</outgoing>\n\n";
	}
	
	
	private String clearString(String s ){
		return s.replace('<', ' ').replace('>', ' ').replace('\"', ' ');
	}
	


}
