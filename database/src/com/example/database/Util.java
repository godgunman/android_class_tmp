package com.example.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.util.Pair;

public class Util {

	public static List<Pair<String, String>> getFields(Class<?> c) {

		Field[] fields = c.getDeclaredFields();
		List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			String name = f.getName();
			String type = parseType(f);

			DBAnnotation info = f.getAnnotation(DBAnnotation.class);

			if (info != null) {
				if ("_default".equals(info.name()) == false) {
					name = info.name();
				}
				if ("_default".equals(info.type()) == false) {
					type = info.type();
				}
				Pair<String, String> pair = Pair.create(name, type);
				result.add(pair);
			}
		}
		return result;
	}

	public static final String SQLITE_TEXT = "TEXT";
	public static final String SQLITE_INTEGER = "INTEGER";
	public static final String SQLITE_REAL = "REAL";
	public static final String SQLITE_BLOB = "BLOB";

	private static String parseType(Field field) {
		Class<?> cls = field.getType();
		if (cls == String.class) {
			return SQLITE_TEXT;
		} else if (cls == short.class || 
				cls == int.class || 
				cls == long.class ||
				cls == boolean.class) {
			return SQLITE_INTEGER;
		} else if (cls == double.class ||
				cls == float.class) {
			return SQLITE_REAL;
		} else if (cls == byte[].class) {
			return SQLITE_BLOB;
		}
		return null;
	}
	
	public static ContentValues parseContentValues(Object obj) {
		Class<?> cls = obj.getClass();
		List<Pair<String, String>> fields = getFields(cls);
		ContentValues contentValues = new ContentValues();
		for(Pair<String, String> field : fields) {
			try {
				Field f = cls.getDeclaredField(field.first);
				Class<?> type = f.getType();
				if (type == int.class) {
					contentValues.put(field.first, f.getInt(obj));
				}
				else if (type == String.class) {
					contentValues.put(field.first, (String)f.get(obj));
				}
				else if (type == double.class) {
					contentValues.put(field.first, f.getDouble(obj));
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return contentValues;
	}
}
