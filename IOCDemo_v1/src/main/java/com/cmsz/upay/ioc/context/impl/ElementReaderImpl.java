package com.cmsz.upay.ioc.context.impl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

public class ElementReaderImpl implements ElementReader {

	// 判断是否懒加载
	@Override
	public boolean isLazy(Element element) {
		// TODO Auto-generated method stub
		String lazy = getAttribute(element, "lazy-init");// 得到是否懒加载这个元素
		if (lazy == null) {
			lazy = "false";
		}
		Boolean lazyInit = new Boolean(lazy);
		return lazyInit;
	}

	// 判断是否多例
	@Override
	public boolean isSingleTon(Element element) {
		// TODO Auto-generated method stub
		String single = getAttribute(element, "scope");
		if (single == null) {
			single = "true";
		}
		Boolean singleTon = new Boolean(single);
		return singleTon;
	}

	// 判断是否依赖
	@Override
	public boolean isRef(Element element) {
		// TODO Auto-generated method stub
		List<Element> secondEle = element.elements();
		for (Element secondE : secondEle) {
			String ref = getAttribute(secondE, "ref");
			if (ref != null) {
				return true;
			}
		}
		return false;
	}

	// 获取bean元素的所有子元素
	@Override
	public List<Element> getPropertyElements(Element element) {
		// TODO Auto-generated method stub
		List<Element> elements = element.elements();
		return elements;
	}

	// 获取bean元素的某个值 比如lazy-init 或 scope
	@Override
	public String getAttribute(Element element, String name) {
		// TODO Auto-generated method stub
		String value = element.attributeValue(name);
		if (value == null) {
			return null;
		}
		if (value.equals("prototype")) {
			value = "false";
		}
		return value;
	}

	// 获取Property节点的值
	@Override
	public List<PropertyElement> getPropertyValue(Element element) {
		// TODO Auto-generated method stub
		List<Element> properties = getPropertyElements(element);
		List<PropertyElement> reslut = new ArrayList<PropertyElement>();
		for (Element e : properties) {
			PropertyElement dataElement = getPropertyDataElement(e);
			reslut.add(dataElement);
		}
		return reslut;
	}

	// 获取Constructor节点
	@Override
	public List<Element> getConstructorElements(Element element) {
		// TODO Auto-generated method stub
		List<Element> elements = element.elements("constructor-arg");
		return elements;
	}

	// 获取Constructor节点的值
	@Override
	public List<ConstructorElement> getConstructorValue(Element element) {
		// TODO Auto-generated method stub
		List<Element> cons = getConstructorElements(element);
		List<ConstructorElement> result = new ArrayList<ConstructorElement>();
		for (Element e : cons) {
			ConstructorElement dataElement = getConstructorDataElement(e);
			result.add(dataElement);
		}
		return result;
	}

	// 获取Property节点具体的值
	private PropertyElement getPropertyDataElement(Element element) {
		Object name = null;
		Object value = null;
		Object ref = null;
		for (int i = 0; i < element.attributeCount(); i++) {
			Attribute attr = element.attribute(i);
			if (attr.getName().equals("name")) {
				name = attr.getValue();
				value = element.getText();
				if (value.equals("")) {
					value = null;
				}
			}
			if (attr.getName().equals("value")) {
				value = element.attributeValue("value");
			}
			if (attr.getName().equals("ref")) {
				ref = element.attributeValue("ref");
			}
		}
		return new PropertyElement(name, value, ref);
	}

	// 获取Constructor节点具体的值
	private ConstructorElement getConstructorDataElement(Element element) {
		Object name = null;
		Object value = null;
		Object ref = null;
		for (int i = 0; i < element.attributeCount(); i++) {
			Attribute attr = element.attribute(i);
			if (attr.getName().equals("name")) {
				name = attr.getValue();
				value = element.getText();
				if (value.equals("")) {
					value = null;
				}
			}
			if (attr.getName().equals("value")) {
				value = element.attributeValue("value");
			}
			if (attr.getName().equals("ref")) {
				ref = element.attributeValue("ref");
			}
		}
		return new ConstructorElement(name, value, ref);
	}
}
