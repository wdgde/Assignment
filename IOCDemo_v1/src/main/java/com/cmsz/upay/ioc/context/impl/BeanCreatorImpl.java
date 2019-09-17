package com.cmsz.upay.ioc.context.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanCreatorImpl implements BeanCreator {

	// 使用空构造器,属性注入
	@Override
	public Object createBeanUseDefaultConstruct(String className) {
		// TODO Auto-generated method stub
		Object object = null;
		try {
			Class clazz = Class.forName(className);
			object = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	// 使用定义的构造器，构造器注入
	@Override
	public Object createBeanUseDefineConstruct(String className, List<Object> args) {
		// TODO Auto-generated method stub
		// 获取参数的class类型
		Class[] argsClass = getArgsClasses(args);
		try {
			Class clazz = Class.forName(className);
			// 找到对应的构造器
			Constructor constructor = findConstructor(clazz, argsClass);
			return constructor.newInstance(args.toArray());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// 根据类型和参数查找构造器
	private Constructor findConstructor(Class clazz, Class[] argsClass) throws NoSuchMethodException {
		// 获取构造器 参数不匹配 返回空
		Constructor constructor = clazz.getConstructor(argsClass);
		if (constructor == null) {
			return null;
		}
		// 获取该类的所有构造器
		Constructor[] constructors = clazz.getConstructors();
		// 对每一条构造函数进行判断
		for (Constructor c : constructors) {
			// 得到该构造函数的参数类型
			Class[] constructorArgsClass = c.getParameterTypes();
			if (constructorArgsClass.length == argsClass.length) {
				// 判断参数类型是否一致
				if (isSameArgs(argsClass, constructorArgsClass)) {
					return c;
				}
			}
		}
		return null;
	}

	// 判断参数类型是否一致
	private boolean isSameArgs(Class[] argsClass, Class[] constructorArgsClass) {
		for (int i = 0; i < argsClass.length; i++) {
			try {
				// 如果不匹配会抛出异常
				argsClass[i].asSubclass(constructorArgsClass[i]);
				if (i == (argsClass.length - 1)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 返回每个参数的class类型
	private Class[] getArgsClasses(List<Object> args) {
		// 装有class类的list集合
		List<Class> reslut = new ArrayList<Class>();
		for (Object arg : args) {
			reslut.add(getClass(arg));
		}
		Class[] a = new Class[reslut.size()];
		return reslut.toArray(a);
	}

	// 根据obj的父类返回自己的类型
	private Class getClass(Object obj) {
		if (obj instanceof Integer) {
			return Integer.TYPE;
		} else if (obj instanceof Double) {
			return Double.TYPE;
		} else if (obj instanceof Long) {
			return Long.TYPE;
		} else if (obj instanceof Float) {
			return Float.TYPE;
		} else if (obj instanceof Character) {
			return Character.TYPE;
		} else if (obj instanceof Byte) {
			return Byte.TYPE;
		}
		return obj.getClass();
	}

	// 获取所有的set方法放经map中
	@Override
	public Map<String, Method> getSetterMethodsMap(Object obj) {
		// TODO Auto-generated method stub
		List<Method> methods = getSetterMethodsList(obj);
		Map<String, Method> result = new HashMap<String, Method>();
		for (Method method : methods) {
			String propertyName = getMethodNameWithOutSet(method.getName());
			result.put(propertyName, method);
		}
		return result;
	}

	// 获取所有的set方法
	private List<Method> getSetterMethodsList(Object obj) {
		Class clazz = obj.getClass();
		List<Method> result = new ArrayList<Method>();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				result.add(method);
			}
		}
		return result;
	}

	// 还原setter方法，去掉set,将函数名的第一个字母转小写
	private String getMethodNameWithOutSet(String methodname) {
		String propertyName = methodname.replace("set", "");
		// 取第一个字母，转小写
		String firstWord = propertyName.substring(0, 1);
		String lowerFirstWord = firstWord.toLowerCase();
		// 替换成小写
		return propertyName.replaceFirst(firstWord, lowerFirstWord);
	}

	// 执行方法
	@Override
	public void executeMethod(Object object, Object argBean, Method method) {
		// TODO Auto-generated method stub
		try {
			// 取得函数的参数类型
			Class[] paramterTypes = method.getParameterTypes();
			if (paramterTypes[0].getName().equals("int")) {
				argBean = Integer.parseInt((String) argBean);
			}
			if (paramterTypes.length == 1) {
				if (isMethodArgs(method, paramterTypes[0])) {
					method.invoke(object, argBean);
				}
			}
		} catch (Exception e) {

		}
	}

	// 判断是否时该方法的参数，class1为第一个参数类型
	private boolean isMethodArgs(Method method, Class class1) {
		Class[] c = method.getParameterTypes();
		if (c.length == 1) {
			try {
				// 将一个类转换成另外一个的实例，如果转换异常就会抛出ClassCastException异常，也就是这个类不是另外一个类的实例
				class1.asSubclass(c[0]);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
