package com.cmsz.upay.ioc.context.impl;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

public interface ElementLoader {
	void addElements(Document doc);// 添加元素

	Element getElement(String id);// 获取元素

	Map<String, Element> getAllElement();// 获取所有的元素
}
