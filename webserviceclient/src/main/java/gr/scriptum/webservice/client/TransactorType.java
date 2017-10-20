
package gr.scriptum.webservice.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transactorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="transactorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACTIVE_MEMBER"/>
 *     &lt;enumeration value="INACTIVE_MEMBER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "transactorType")
@XmlEnum
public enum TransactorType {

    ACTIVE_MEMBER,
    INACTIVE_MEMBER;

    public String value() {
        return name();
    }

    public static TransactorType fromValue(String v) {
        return valueOf(v);
    }

}
