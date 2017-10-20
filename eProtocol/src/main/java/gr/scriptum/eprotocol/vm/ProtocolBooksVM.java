/**
 * 
 */
package gr.scriptum.eprotocol.vm;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;

import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.ProtocolBook.ProtocolBookType;
import gr.scriptum.domain.ProtocolBook.ProtocolBookType;
import gr.scriptum.eprotocol.csv.ProtocolBookConverter;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@AfterCompose(superclass = true)
@Init(superclass = true)
public class ProtocolBooksVM extends GenericSearchVM<ProtocolBook, ProtocolBookDAO, ProtocolBookConverter> {

	private ProtocolBookType[] types;

	{
		ProtocolBookType[] protocolBookTypes = ProtocolBookType.values();
		types = new ProtocolBookType[protocolBookTypes.length+1];
		types[0] = null;
		for (int i = 0; i < protocolBookTypes.length; i++) {
			types[i + 1] = protocolBookTypes[i];
		}
	}

	@Override
	public String getEntityPage() {
		return ProtocolBookVM.PAGE;
	}

	public ProtocolBookType[] getTypes() {
		return types;
	}

	public void setTypes(ProtocolBookType[] types) {
		this.types = types;
	}

}
