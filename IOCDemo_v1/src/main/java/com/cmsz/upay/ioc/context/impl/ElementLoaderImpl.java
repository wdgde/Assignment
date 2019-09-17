package com.cmsz.upay.ioc.context.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public class ElementLoaderImpl implements ElementLoader {
	private Map<String, Element> elements = new HashMap<String, Element>();

	@Override
	public void addElements(Document doc) {
		// TODO Auto-generated method stub
		List<Element> eles = doc.getRootElement().elements();
		for (Element e : eles) {
			String id = e.attributeValue("id");
			elements.put(id, e);
		}
	}

	@Override
	public Element getElement(String id) {
		// TODO Auto-generated method stub
		return elements.get(id);
	}

	@Override
	public Map<String, Element> getAllElement() {
		return elements;
	}

}
