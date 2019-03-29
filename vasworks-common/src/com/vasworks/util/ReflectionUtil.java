package com.vasworks.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.azeckoski.reflectutils.ReflectUtils;

public class ReflectionUtil {	
	private static final Log LOG = LogFactory.getLog(ReflectionUtil.class);
	
	public static final ReflectUtils REFLECTION_UTILS = ReflectUtils.getInstance();

	public static void setPropertyValue(Object object, Object value, String property) throws Exception {
		Method[] m = object.getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			if (m[i].getName().equalsIgnoreCase("set" + property) || m[i].getName().equalsIgnoreCase("is" + property)) {
				m[i].invoke(object, value);
				break;
			}
		}
	}

	public static Object getPropertyValue(Object object, String property) throws Exception {
		Object value = null;

		Method[] m = object.getClass().getMethods();
		for (int i = 0; i < m.length; i++) {
			if (m[i].getName().equalsIgnoreCase("get" + property) || m[i].getName().equalsIgnoreCase("is" + property)) {
				value = m[i].invoke(object, (Object[]) null);
				break;
			}
		}

		if (value != null && "false".equals(value.toString())) {
			value = "N/A";
		}
		if (value != null && "true".equals(value.toString())) {
			value = "YES";
		}

		if (value instanceof Double) {
			DecimalFormat formatMask = new DecimalFormat("###,###,###,###,###,###,###,###,###.00");
			value = formatMask.format(value);
		}

		return value;
	}

	public static Class<?> getPropertyType(Object object, String property) throws Exception {
		Class<?> c = null;

		Field[] f = object.getClass().getDeclaredFields();

		for (int i = 0; i < f.length; i++) {
			if (f[i].getName().equals(property)) {
				c = f[i].getType();
				break;
			}
		}

		return c;
	}

	public static Object execMethod(Object object, String methodName, Class<?>[] paramTypes, Object[] params) throws Exception {
		Class<?> c = object.getClass();

		Method m = c.getMethod(methodName, paramTypes);

		return m.invoke(object, params);
	}

	public static Object createInstance(Class<?> c) throws Exception {
		Object instance = null;

		int modifiers = c.getModifiers();

		if (!c.isInterface() || !Modifier.isAbstract(modifiers)) {
			instance = c.newInstance();
		}

		return instance;
	}

	public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		// Get a File object for the package
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = '/' + packageName.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(resource.getFile());
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package");
		}
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					classes.add(Class.forName(packageName + '.' + files[i].substring(0, files[i].length() - 6)));
				}
			}
		} else {
			throw new ClassNotFoundException(packageName + " does not appear to be a valid package");
		}
		Class<?>[] classesA = new Class[classes.size()];
		classes.toArray(classesA);
		return classesA;
	}

	public static Class<?>[] getClassesInJar(Class<?> referenceClass, String jarFileName, String packageName) throws ClassNotFoundException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		packageName = packageName.replaceAll("\\.", "/");
				
		LOG.debug("Jar " + jarFileName + " looking for " + packageName);
		
		ClassLoader cld = referenceClass.getClassLoader();
		if (cld == null) {
			throw new ClassNotFoundException("Can't get class loader.");
		}
		
		try {
			JarInputStream jarFile = new JarInputStream(cld.getResourceAsStream(jarFileName));
			JarEntry jarEntry;

			while (true) {
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class"))) {
					LOG.debug("Found " + jarEntry.getName().replaceAll("/", "\\."));
					classes.add(Class.forName(jarEntry.getName().replaceAll("/", "\\.")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes.toArray(new Class<?>[classes.size()]);
	}
}
