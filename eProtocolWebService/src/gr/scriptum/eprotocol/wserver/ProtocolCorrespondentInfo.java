/**
 * 
 */
package gr.scriptum.eprotocol.wserver;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gr.scriptum.domain.ProtocolCorrespondent;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentAction;
import gr.scriptum.domain.ProtocolCorrespondent.CorrespondentType;

/**
 * @author <a href=
 *         "mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProtocolCorrespondentInfo {

	private static Log log = LogFactory.getLog(ProtocolCorrespondentInfo.class);

	private CorrespondentType type;

	private String description;

	private String code;

	private String vatNumber;

	private String ssn;

	private CorrespondentAction action;

	private String distributionMethod;
	
	private String distributionMethodDescription;

	private Date dispatchDate;

	private Date routingDate;

	private Date deliveryDate;

	private Date attachedDeliveryDate;

	private Date createDt;
	
	private Date updateTs;

	public ProtocolCorrespondentInfo(ProtocolCorrespondent pc) {
		super();
		this.type = pc.getType();
		this.description = pc.getDescription();
		this.code = pc.getCode();
		this.vatNumber = pc.getVatNumber();
		this.ssn = pc.getSsn();
		this.action = pc.getAction();
		this.distributionMethod = pc.getDistributionMethod()==null ? null : pc.getDistributionMethod().getCode();
		this.distributionMethodDescription = pc.getDistributionMethod()==null ? null : pc.getDistributionMethod().getDescription();
		this.dispatchDate = pc.getDispatchDate();
		this.routingDate = pc.getRoutingDate();
		this.deliveryDate = pc.getDeliveryDate();
		this.attachedDeliveryDate = pc.getAttachedDeliveryDate();
		this.createDt = pc.getCreateDt();
		this.updateTs = pc.getUpdateTs();
	}

	public ProtocolCorrespondentInfo(CorrespondentType type, String description, String code, String vatNumber,
			String ssn, CorrespondentAction action, String distributionMethod, Date dispatchDate, Date routingDate,
			Date deliveryDate, Date attachedDeliveryDate, Date createDt, Date updateTs) {
		super();
		this.type = type;
		this.description = description;
		this.code = code;
		this.vatNumber = vatNumber;
		this.ssn = ssn;
		this.action = action;
		this.distributionMethod = distributionMethod;
		this.dispatchDate = dispatchDate;
		this.routingDate = routingDate;
		this.deliveryDate = deliveryDate;
		this.attachedDeliveryDate = attachedDeliveryDate;
		this.createDt = createDt;
		this.updateTs = updateTs;
	}

	
}
