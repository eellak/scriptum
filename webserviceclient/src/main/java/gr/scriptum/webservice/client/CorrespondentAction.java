
package gr.scriptum.webservice.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for correspondentAction.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="correspondentAction">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TO"/>
 *     &lt;enumeration value="CC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "correspondentAction")
@XmlEnum
public enum CorrespondentAction {

    TO,
    CC;

    public String value() {
        return name();
    }

    public static CorrespondentAction fromValue(String v) {
        return valueOf(v);
    }

}
