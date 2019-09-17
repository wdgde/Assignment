package com.cmsz.upay.ioc.context.impl;

import java.util.List;

import org.dom4j.Element;

public interface ElementReader {
	boolean isLazy(Element element);

	List<Element> getConstructorElements(Element element);

	String getAttribute(Element element, String name);

	boolean isSingleTon(Element element);

	boolean isRef(Element element);

	List<Element> getPropertyElements(Element element);

	List<ConstructorElement> getConstructorValue(Element element);

	List<PropertyElement> getPropertyValue(Element element);
}
