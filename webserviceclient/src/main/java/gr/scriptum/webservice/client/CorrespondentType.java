
package gr.scriptum.webservice.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for correspondentType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="correspondentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ACTIVE_MEMBER"/>
 *     &lt;enumeration value="INACTIVE_MEMBER"/>
 *     &lt;enumeration value="EMPLOYEE"/>
 *     &lt;enumeration value="DEPARTMENT"/>
 *     &lt;enumeration value="COMPANY"/>
 *     &lt;enumeration value="CONTACT"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "correspondentType")
@XmlEnum
public enum CorrespondentType {

    ACTIVE_MEMBER,
    INACTIVE_MEMBER,
    EMPLOYEE,
    DEPARTMENT,
    COMPANY,
    CONTACT,
    OTHER;

    public String value() {
        return name();
    }

    public static CorrespondentType fromValue(String v) {
        return valueOf(v);
    }

}
