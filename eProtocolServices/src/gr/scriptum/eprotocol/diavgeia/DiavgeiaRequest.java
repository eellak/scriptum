package gr.scriptum.eprotocol.diavgeia;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * - Στη φόρμα καταχώρησης των στοιχείων μιας απόφασης περιλαμβάνονται τα παρακάτω πεδία:
name                     Περιγραφή                                                Τύπος        Υποχρεωτικό
arithmosProtokolou       Ο Αριθμός Πρωτοκόλλου της Απόφασης                       string       ΝΑΙ
apofasiDate              Η ημ/νια της απόφασης YYYY-MM-DD                                      NAI
syntaktisEmail           Το email που θα λάβει ενημέρωση για την ολοκλήρωση της ανάρτησης  string  ΝΑΙ
level2Text                Το ID του φορέα                                         integer      NAI 
thema                    Το θέμα της απόφασης                                     string       ΝΑΙ
eidosApofasis            Το ID του τύπου απόφασης                                 integer      ΝΑΙ
monadesText              Το ID της μονάδας του φορέα                              integer      ΝΑΙ
telikosYpografwn         Το ID του τελικού υπογράφοντα                            integer      ΝΑΙ
thematikiEnotitaValuesText Τα IDs των θεματικών ενοτήτων                          #id1#,#id2#,   ΟΧΙ
file                     Το αρχείο της απόφασης σε μορφή PDF                      HTTP file    ΝΑΙ

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
