//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.23 at 05:11:44 PM GMT 
//


package org.openmrs.core.policy;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RuleCombinerParametersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RuleCombinerParametersType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:xacml:2.0:policy:schema:os}CombinerParametersType">
 *       &lt;attribute name="RuleIdRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RuleCombinerParametersType")
public class RuleCombinerParametersType
    extends CombinerParametersType
    implements Serializable
{

    private final static long serialVersionUID = 632768732L;
    @XmlAttribute(name = "RuleIdRef", required = true)
    protected String ruleIdRef;

    /**
     * Gets the value of the ruleIdRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuleIdRef() {
        return ruleIdRef;
    }

    /**
     * Sets the value of the ruleIdRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuleIdRef(String value) {
        this.ruleIdRef = value;
    }

}
