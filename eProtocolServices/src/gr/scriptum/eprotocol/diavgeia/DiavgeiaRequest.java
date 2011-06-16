package gr.scriptum.eprotocol.diavgeia;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
Class that represents the Request sent to diavgeia.gov.gr
* @author mike
*
*/

public class DiavgeiaRequest {
	public String arithmosProtokolou;
	public String apofasiDate;  
	public String syntaktisEmail;
	public String level2Text;
	public String thema;
	public String eidosApofasis;
	public String monadesText;
	public String telikosYpografwn;
	public String thematikiEnotitaValuesText;
	public File   file; 
		
	
	public DiavgeiaRequest(){
	}
	
	public void setApofasiDate( Date date){
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		this.apofasiDate =  format.format(date);
	}

	@Override
	public String toString() {
		return "DiavgeiaRequest [arithmosProtokolou=" + arithmosProtokolou
				+ ", apofasiDate=" + apofasiDate + ", syntaktisEmail="
				+ syntaktisEmail + ", level2Text=" + level2Text + ", thema="
				+ thema + ", eidosApofasis=" + eidosApofasis + ", monadesText="
				+ monadesText + ", telikosYpografwn=" + telikosYpografwn
				+ ", thematikiEnotitaValuesText=" + thematikiEnotitaValuesText
				+ ", file=" + file + "]";
	}
   
	
   
}
