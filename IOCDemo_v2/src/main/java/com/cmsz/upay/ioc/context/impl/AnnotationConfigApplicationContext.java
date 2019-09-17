package com.cmsz.upay.ioc.context.impl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cmsz.upay.ioc.beans.exception.BeansException;
import com.cmsz.upay.ioc.context.ApplicationContext;
import com.cmsz.upay.ioc.context.annotation.Bean;
import com.cmsz.upay.ioc.context.annotation.Component;
import com.cmsz.upay.ioc.context.annotation.ComponentScan;
import com.cmsz.upay.ioc.context.annotation.Configuration;
import com.cmsz.upay.ioc.context.annotation.Lazy;
import com.cmsz.upay.ioc.context.annotation.Scope;

public class AnnotationConfigApplicationContext implements ApplicationContext {
	// 存放bean的map
	private Map<String, Object> beanObj = new HashMap<String, Object>();
	// 存放@bean注解的方法
	private Map<String, Method> methods = new HashMap<String, Method>();
	// cofig.class类
	private Class configCla;
	// cofig.class类的对象
	private Object configObj;
	// 绝对路径
	private String classpath;
	// Apple.class的类名
	private String appleClassName;

	public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
		configCla = annotatedClasses[0];
		// 得到@ComponentScan注解
		ComponentScan componentScan = (ComponentScan) configCla.getAnnotation(ComponentScan.class);
		if (componentScan != null) {
			// 得到注解的value
			String packageName = componentScan.value()[0];
			// 根据value找到指定的类，并加到map中
			doAnnotationScan(packageName);
		}

		// 得到@Configuration注解
		Configuration configuration = (Configuration) configCla.getAnnotation(Configuration.class);
		if (configuration != null) {
			// 把该类下的所有方法存到一个map中
			for (Method m : configCla.getDeclaredMethods()) {
				String id = getId(m);
				methods.put(id, m);
			}
			try {
				configObj = configCla.newInstance();
				// 创建bean
				createBean();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 处理@ComponentScan注解的方法
	private void doAnnotationScan(String packageName) {
		packageName = packageName.replace('.', '\\');
		// 根据项目根路径查找到packageName的路径
		doPath(new File(System.getProperty("user.dir")), packageName);
		// 根据packageName的路径找到该路径下的类的名称
		doPath(new File(classpath), "");
		if (appleClassName != null) {
			// 对类名进行处理
			appleClassName = appleClassName.substring(appleClassName.indexOf("com"), appleClassName.indexOf(".class"));
			appleClassName = appleClassName.replace('\\', '.');
			try {
				Class cla = Class.forName(appleClassName);
				// 得到@Component注解
				Component component = (Component) cla.getAnnotation(Component.class);
				if (component != null) {
					String[] id = appleClassName.split("\\.");
					// 将bean放到map中
					beanObj.put(id[id.length - 1].toLowerCase(), cla.newInstance());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 查找路径
	private void doPath(File file, String packageName) {
		if (file.isDirectory()) {// 文件夹
			if (file.getPath().endsWith(packageName)) {
				classpath = file.getPath();
			}
			// 文件夹就递归
			File[] files = file.listFiles();
			for (File f1 : files) {
				doPath(f1, packageName);
			}
		} else {// 标准文件
			// 标准文件我们就判断是否是class文件
			if (!packageName.equals("")) {
				return;
			}
			if (file.getName().endsWith(".class")) {
				// 如果是class文件
				appleClassName = file.getPath();
			}
		}
	}

	// 创建bean
	private void createBean() {
		for (Method m : configCla.getDeclaredMethods()) {
			Scope scope = m.getAnnotation(Scope.class);
			Lazy lazy = m.getAnnotation(Lazy.class);
			// 为每一个方法创建一个bean
			Object obj = dogetBean(m);
			// 不是原型模式和懒加载模式时把bean放到map中
			if (obj != null && scope == null && lazy == null) {
				String id = getId(m);
				beanObj.put(id, obj);
			}
		}
	}

	private Object dogetBean(Method m) {
		Bean bean = m.getAnnotation(Bean.class);
		Scope scope = m.getAnnotation(Scope.class);
		Lazy lazy = m.getAnnotation(Lazy.class);

		Object param = null;
		Object obj = null;

		if (bean != null) {
			// 懒加载的情况
			if (lazy != null) {
				// 懒加载且依赖注入的情况
				if (m.getParameterTypes().length != 0) {
					throw new BeansException("");
				}
			}
			// 依赖注入的情况
			if (m.getParameterTypes().length != 0) {
				// 得到依赖的id
				String refId = getRefId(m.getParameterTypes()[0]);
				// 判断该bean是否被加到map中
				param = beanObj.get(refId);
				if (param == null) {
					// 没有被加载
					param = dogetBean(methods.get(refId));
					if (scope != null && !scope.value().equals("prototype")) {
						// 不是原型模式时放到map中
						beanObj.put(refId, param);
					}
				}
			}
			try {
				// 根据param即参数判断使用哪个invoke
				if (param == null) {
					obj = m.invoke(configObj);
				} else {
					obj = m.invoke(configObj, param);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		// 返回bean
		return obj;
	}

	// 根据方法获取获取方法名即id
	private String getId(Method m) {
		String id = m.getName();
		if (!m.getAnnotation(Bean.class).name().equals("")) {
			id = m.getAnnotation(Bean.class).name();
		}
		return id;
	}

	// 获取依赖的bean的id
	private String getRefId(Class cla) {
		String id = null;
		Iterator<String> hashmapIter = methods.keySet().iterator();
		while (hashmapIter.hasNext()) {
			// 取出key值
			String keyName = hashmapIter.next();
			if (methods.get(keyName).getReturnType().equals(cla)) {
				id = keyName;
				break;
			}
		}
		return id;
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return beanObj.get(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		Object objBean = beanObj.get(name);
		// 如果获取的bean为空 则再生成一个bean
		if (objBean == null) {
			objBean = dogetBean(methods.get(name));
		}
		return (T) objBean;
	}

	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		Object objBean = null;
		int sameObject = 0;
		Method method = null;
		// 根据requiredType类型和方法的返回类型找到对应的方法
		for (Method m : configCla.getDeclaredMethods()) {
			if (m.getReturnType().equals(requiredType)) {
				sameObject++;
				method = m;
			}
		}
		// 如果有多个方法返回类型一致
		if (sameObject != 1) {
			throw new BeansException("");
		} else {
			objBean = dogetBean(method);
		}
		return (T) objBean;
	}

}
