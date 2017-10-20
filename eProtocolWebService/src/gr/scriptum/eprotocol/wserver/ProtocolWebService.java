package gr.scriptum.eprotocol.wserver;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import gr.scriptum.dao.ProtocolDAO;
import gr.scriptum.domain.Company;
import gr.scriptum.domain.Protocol;
import gr.scriptum.eprotocol.wserver.ProtocolWebServiceFaultBean.ErrorCode;

/**
 * The Web Service to implement the eProtocol subscription method.
 * 
 * @author Mike Mountrakis mountrakis@uit.gr
 * 
 */

@WebService(endpointInterface = "gr.scriptum.eprotocol.wserver.EProtocolWebService")
public class ProtocolWebService implements EProtocolWebService {

	private static Log logger = LogFactory.getLog(ProtocolWebService.class.getSimpleName());

	@Resource
	WebServiceContext wsContext;

	private Validator validator;

	@PostConstruct
	public void init() {
		logger.info("Post construct");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Override
	public ProtocolReceipt receiveIncomingProtocol(IncomingProtocolMessage message) throws ProtocolWebServiceFault {
		IncomingProtocolMessageParser parser = new IncomingProtocolMessageParser();
		try {
			return parser.receiveIncomingProtocol(message, wsContext, validator);
		} catch (ObjectOptimisticLockingFailureException e) {
			logger.warn("Could not get number for protocol");
			throw new ProtocolWebServiceFault(new ProtocolWebServiceFaultBean(ErrorCode.INSERTION_ERROR,
					"Temporary protocol number already allocated to different protocol, retry"), e);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new ProtocolWebServiceFault(
					new ProtocolWebServiceFaultBean(ErrorCode.INSERTION_ERROR, e.getMessage()), e);
		}
	}

	@Override
	public ProtocolReceipt receiveOutgoingProtocol(OutgoingProtocolMessage message) throws ProtocolWebServiceFault {
		OutgoingProtocolMessageParser parser = new OutgoingProtocolMessageParser();
		try {
			return parser.receiveOutgoingProtocol(message, wsContext, validator);
		}catch (ObjectOptimisticLockingFailureException e) {
			logger.warn("Could not get number for protocol");
			throw new ProtocolWebServiceFault(new ProtocolWebServiceFaultBean(ErrorCode.INSERTION_ERROR,
					"Temporary protocol number already allocated to different protocol, retry"), e);
		} catch (RuntimeException e) {
			throw new ProtocolWebServiceFault(
					new ProtocolWebServiceFaultBean(ErrorCode.INSERTION_ERROR, e.getMessage()), e);
		}
	}

	@Override
	public List<ProtocolInfo> inquireProtocols(ProtocolQuery query) throws Exception {
		ProtocolRetriever retriever = ContextLoader.getCurrentWebApplicationContext().getBean(ProtocolRetriever.class);
		return retriever.search(query, wsContext, validator);
	}

}
