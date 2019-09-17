package com.cmsz.upay.ioc.context.impl;

public class ConstructorElement {
	private Object value;
	private Object name;
	private Object ref;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getRef() {
		return ref;
	}

	public void setRef(Object ref) {
		this.ref = ref;
	}

	public ConstructorElement(Object name, Object value, Object ref) {
		this.value = value;
		this.name = name;
		this.ref = ref;
	}
}
