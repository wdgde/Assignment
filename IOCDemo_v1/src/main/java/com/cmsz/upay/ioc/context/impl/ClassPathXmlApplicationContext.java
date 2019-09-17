package com.cmsz.upay.ioc.context.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cmsz.upay.ioc.beans.exception.BeansException;
import com.cmsz.upay.ioc.context.ApplicationContext;

/**
 * <b>实现类</b><br>
 * 参考spring-framework，加载并解析配置文件，完成对象实例化，实现依赖注入<br>
 * 完成具体的功能，可自己添加需要的属性和方法。<br>
 * 需要完成的功能点大致如下：<br>
 * <ol>
 * <li>配置文件加载、解析</li>
 * <li>Bean实例化</li>
 * <li>属性注入/构造器注入</li>
 * <li>依赖、循环依赖</li>
 * <li>懒加载</li>
 * <li>Bean实例的作用域</li>
 * </ol>
 * 
 * @author
 * 
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
	// 存放bean的map
	Map<String, Object> objectResult = new HashMap<String, Object>();
	// 存放class的map
	Map<String, Class> classResult = new HashMap<String, Class>();
	// 存放懒加载class的map
	Map<String, Class> lazyClassResult = new HashMap<String, Class>();
	// 存放xml节点信息的map
	Map<String, Element> elements = new HashMap<String, Element>();
	// 加载节点
	private ElementLoader elementLoader = new ElementLoaderImpl();
	// 解析节点
	private ElementReader elementReader = new ElementReaderImpl();
	// 创建bean的类
	private BeanCreator beanCreator = new BeanCreatorImpl();

	public ClassPathXmlApplicationContext() throws SecurityException, Exception {
		this("applicationContext.xml");// 默认加载applicationContext.xml
	}

	public ClassPathXmlApplicationContext(String configLocation) throws DocumentException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, Exception, SecurityException {
		// You fill this in //
		String LOCAL_LIST_PATH = this.getClass().getClassLoader().getResource(configLocation).getPath();
		// 1.读取
		SAXReader reader = new SAXReader();
		Document document = reader.read(LOCAL_LIST_PATH);
		// 把节点添加
		elementLoader.addElements(document);
		// 获取全部节点
		elements = elementLoader.getAllElement();
		Iterator<String> hashmapIter = elements.keySet().iterator();
		while (hashmapIter.hasNext()) {
			// 取出key值
			String keyName = hashmapIter.next();
			Element ele = elementLoader.getElement(keyName);
			// 获取Property节点
			List<PropertyElement> reslut = elementReader.getPropertyValue(ele);
			// 判断是否懒加载
			if (elementReader.isLazy(ele)) {
				// 得到class的名
				Class clazz = Class.forName(elementReader.getAttribute(ele, "class"));
				lazyClassResult.put(keyName, clazz);
				continue;
			}
			// 开始注入
			injection(ele, reslut, keyName);
		}
	}

	private void injection(Element ele, List<PropertyElement> reslut, String id) {
		// 获取类的路径
		String className = elementReader.getAttribute(ele, "class");
		Object obj = null;
		// 获取constructor元素，判断是否存在
		List<Element> constructorElements = elementReader.getConstructorElements(ele);
		// 如果不存在constructor节点 则是属性注入
		if (constructorElements.size() == 0) {
			// 获得bean的实例
			obj = beanCreator.createBeanUseDefaultConstruct(className);
			// 获取该bean的set方法
			Map<String, Method> resultMethod = beanCreator.getSetterMethodsMap(obj);
			// 获取Property节点的属性，即获取参数
			Map<String, Object> resultArgs = getPropertyArgs(reslut, ele);
			Iterator<String> hashmapIter = resultMethod.keySet().iterator();
			while (hashmapIter.hasNext()) {
				// 取出key值
				String keyName = hashmapIter.next();
				// 当对应的方法和参数不为空时，执行该set方法
				if (resultMethod.get(keyName) != null && resultArgs.get(keyName) != null) {
					beanCreator.executeMethod(obj, resultArgs.get(keyName), resultMethod.get(keyName));
				}
			}
			// 放入map中
			objectResult.put(id, obj);
		} else {
			// 构造器注入
			// 获取参数
			List<Object> args = getConstructArgs(ele);
			obj = beanCreator.createBeanUseDefineConstruct(className, args);
			objectResult.put(id, obj);
		}
		// 如果不是单例模式
		if (!elementReader.isSingleTon(ele)) {
			objectResult.put(id, null);
			classResult.put(id, obj.getClass());
		}
	}

	@Override
	public Object getBean(String name) throws BeansException {
		// You fill this in //
		Object bean = objectResult.get(name);
		return bean;
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		// You fill this in //
		if (objectResult.get(name) == null && lazyClassResult.get(name) == null) {
			Class cla = classResult.get(name);
			try {
				return (T) cla.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 根据name获取懒加载的bean
		Object obj = getLazyBeanById(requiredType, name);
		return (T) obj;
	}

	private Object getLazyBeanById(Class requiredType, String name) {
		Iterator<String> classHashmapIter = lazyClassResult.keySet().iterator();
		String keyName = null;
		while (classHashmapIter.hasNext()) {
			// 取出key值
			keyName = classHashmapIter.next();
			// 如果是懒加载
			if (keyName.equals(name) && lazyClassResult.get(keyName) != null
					&& requiredType.getName().equals(lazyClassResult.get(keyName).getName())) {
				Element ele = elementLoader.getElement(keyName);
				List<PropertyElement> reslut = elementReader.getPropertyValue(ele);
				// 懒加载，再创建一个bean
				injection(ele, reslut, keyName);
				if (objectResult.get(keyName) == null) {
					Class cla = classResult.get(keyName);
					try {
						return cla.newInstance();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return objectResult.get(keyName);
			}
		}
		return objectResult.get(name);
	}

	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		// You fill this in //
		if (getUnknowBean(requiredType)) {
			throw new BeansException("");
		}
		Iterator<String> objectHashmapIter = objectResult.keySet().iterator();
		while (objectHashmapIter.hasNext()) {
			// 取出key值
			String keyName = objectHashmapIter.next();
			if (objectResult.get(keyName) != null
					&& requiredType.getName().equals(objectResult.get(keyName).getClass().getName())) {
				return (T) objectResult.get(keyName);
			}
		}
		Object obj = getLazyBean(requiredType);
		return (T) obj;
	}

	// 根据requiredType获取懒加载的bean
	private Object getLazyBean(Class requiredType) {
		Iterator<String> classHashmapIter = lazyClassResult.keySet().iterator();
		String keyName = null;
		while (classHashmapIter.hasNext()) {
			// 取出key值
			keyName = classHashmapIter.next();
			if (lazyClassResult.get(keyName) != null
					&& requiredType.getName().equals(lazyClassResult.get(keyName).getName())) {
				Element ele = elementLoader.getElement(keyName);
				List<PropertyElement> reslut = elementReader.getPropertyValue(ele);
				injection(ele, reslut, keyName);
				if (objectResult.get(keyName) == null) {
					Class cla = classResult.get(keyName);
					try {
						return cla.newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return objectResult.get(keyName);
	}

	// 获取不明确对象
	private boolean getUnknowBean(Class requiredType) {
		int i = 0;
		Iterator<String> hashmapIter = elements.keySet().iterator();
		while (hashmapIter.hasNext()) {
			// 取出key值
			String keyName = hashmapIter.next();
			if (elements.get(keyName).attributeValue("class").equals(requiredType.getName())) {
				i++;
			}
		}
		if (i > 1) {
			return true;
		}
		return false;
	}

	// 获取Construct节点参数
	private List<Object> getConstructArgs(Element e) {
		List<ConstructorElement> datas = elementReader.getConstructorValue(e);
		List<Object> result = new ArrayList<Object>();
		for (ConstructorElement d : datas) {
			if (d.getValue() != null) {
				result.add(d.getValue());
			}
			// 如果是依赖注入
			if (d.getRef() != null) {
				String ref = (String) d.getRef();
				Element eleRef = elementLoader.getElement(ref);
				List<PropertyElement> refReslut = elementReader.getPropertyValue(eleRef);
				refInjection(e, eleRef, refReslut, ref);
				result.add(objectResult.get(ref));
			}
		}
		return result;
	}

	//// 获取Property节点参数
	private Map<String, Object> getPropertyArgs(List<PropertyElement> properties, Element ele) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (PropertyElement p : properties) {
			if (p.getValue() != null) {
				result.put((String) p.getName(), p.getValue());
			}
			// 如果是依赖注入
			if (p.getRef() != null) {
				String ref = (String) p.getRef();
				Element eleRef = elementLoader.getElement(ref);
				List<PropertyElement> refReslut = elementReader.getPropertyValue(eleRef);
				refInjection(ele, eleRef, refReslut, ref);
				result.put((String) p.getName(), objectResult.get(ref));
			}
		}
		return result;
	}

	// 依赖注入 创建bean
	private void refInjection(Element firstEle, Element secondEle, List<PropertyElement> refReslut, String id) {
		// 循环依赖
		if (elementReader.isRef(secondEle)) {
			Object obj1 = refInjectionGetObject(firstEle);
			Object obj2 = refInjectionGetObject(secondEle);
			refInjectionMethod(firstEle, secondEle, obj1);
			refInjectionMethod(secondEle, firstEle, obj2);
			objectResult.put(firstEle.attributeValue("id"), obj1);
			objectResult.put(secondEle.attributeValue("id"), obj2);
		} else {
			// 非循环依赖
			Object obj = refInjectionGetObject(secondEle);
			refInjectionMethod(secondEle, secondEle, obj);
			objectResult.put(firstEle.attributeValue("id"), obj);
			objectResult.put(secondEle.attributeValue("id"), obj);
		}

	}

	// 获取依赖注入的object
	private Object refInjectionGetObject(Element ele) {
		String className = elementReader.getAttribute(ele, "class");
		Object obj = null;
		// 获取constructor元素，判断是否存在
		List<Element> constructorElements = elementReader.getConstructorElements(ele);
		if (constructorElements.size() == 0) {
			obj = beanCreator.createBeanUseDefaultConstruct(className);
		} else {
			List<Object> args = getRefConstructArgs(ele);
			obj = beanCreator.createBeanUseDefineConstruct(className, args);
		}
		objectResult.put(ele.attributeValue("id"), obj);
		return obj;
	}

	// 执行被依赖对象的set方法
	private void refInjectionMethod(Element firstEle, Element secondEle, Object obj) {
		Map<String, Method> resultMethod1 = beanCreator.getSetterMethodsMap(obj);
		Map<String, Object> resultArgs1 = getRefPropertyArgs(elementReader.getPropertyValue(firstEle), secondEle);
		Iterator<String> hashmapIter1 = resultMethod1.keySet().iterator();
		while (hashmapIter1.hasNext()) {
			// 取出key值
			String keyName = hashmapIter1.next();
			if (resultMethod1.get(keyName) != null && resultArgs1.get(keyName) != null) {
				beanCreator.executeMethod(obj, resultArgs1.get(keyName), resultMethod1.get(keyName));
			}
		}
	}

	// 获得被依赖对象的Property节点的参数
	private Map<String, Object> getRefPropertyArgs(List<PropertyElement> properties, Element ele) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (PropertyElement p : properties) {
			if (p.getValue() != null) {
				result.put((String) p.getName(), p.getValue());
			}
			if (p.getRef() != null) {
				result.put((String) p.getName(), objectResult.get(ele.attributeValue("id")));
			}
		}
		return result;
	}

	// 获得被依赖对象的Construct节点的参数
	private List<Object> getRefConstructArgs(Element e) {
		List<ConstructorElement> datas = elementReader.getConstructorValue(e);
		List<Object> result = new ArrayList<Object>();
		for (ConstructorElement d : datas) {
			if (d.getValue() != null) {
				result.add(d.getValue());
			}
			if (d.getRef() != null) {
				// 如果获取的构造器依赖注入获取的bean为空，抛出异常
				if (objectResult.get(e.attributeValue("id")) == null) {
					throw new BeansException("");
				}
				result.add(objectResult.get(e.attributeValue("id")));
			}
		}
		return result;
	}
}
