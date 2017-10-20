/**
 * 
 */
package gr.scriptum.eprotocol.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;

import gr.scriptum.dao.ProtocolBookDAO;
import gr.scriptum.domain.ProtocolBook;
import gr.scriptum.domain.Resource;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class ResourceStringConverter implements Converter<String, Resource, Component> {

	private static Log log = LogFactory.getLog(ResourceStringConverter.class);
	
	@Override
	public String coerceToUi(Resource resource, Component component, BindContext ctx) {
		// TODO Enhance by dynamically detecting entity types
		ProtocolBookDAO protocolBookDAO = SpringUtil.getApplicationContext().getBean(ProtocolBookDAO.class);
		ProtocolBook protocolBook = protocolBookDAO.findById(Integer.valueOf(resource.getValue()), false);
		return protocolBook.getProtocolSeries();
	}

	@Override
	public Resource coerceToBean(String compAttr, Component component, BindContext ctx) {
		return null;
	}

}
