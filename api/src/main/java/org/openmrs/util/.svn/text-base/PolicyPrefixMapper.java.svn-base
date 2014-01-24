package org.openmrs.util;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class PolicyPrefixMapper extends NamespacePrefixMapper {
	
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		
		if ("urn:oasis:names:tc:xacml:2.0:policy:schema:os".equals(namespaceUri))
			return "xacml";
		
		if ("http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri))
			return "xsi";
		
		return suggestion;
	}
	
}
