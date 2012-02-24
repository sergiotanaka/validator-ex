package com.pinguin.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
	public static List<Field> getAllFields(Class<? extends Object> clazz) {
		List<Field> fields = new ArrayList<Field>();
		
		fields.addAll(getFieldsOfClass(clazz));
		
		Class<?> superclass = clazz.getSuperclass();
		while (superclass != null) {
			fields.addAll(getFieldsOfClass(superclass));
			superclass = superclass.getSuperclass();
		}
		
		return fields;
	}
	
	public static List<Field> getFieldsOfClass(Class<? extends Object> clazz) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field);
		}
		return fields;
	}
	

}
