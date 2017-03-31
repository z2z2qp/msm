/**
 * @Title MapEntity.java
 * @Package com.chuangyuan.entity
 * @Description
 * @Author 庄佳彬
 * @Date 2016年3月3日
 * @Version v1.0
 */
package com.will.framework.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * <li>ClassName: MapEntity</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年3月3日 上午11:10:42</li>
 * @author 庄佳彬
 */
public class MapEntity<K,V> extends HashMap<K, V> implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -992107495647664914L;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */
	public MapEntity() {
		super();
		
	}


	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public MapEntity(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param initialCapacity
	 */
	public MapEntity(int initialCapacity) {
		super(initialCapacity);
		
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param m
	 */
	public MapEntity(Map<? extends K, ? extends V> m) {
		super(m);
		
	}

	/**
	 * 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	public MapEntity(BaseEntity entity) {
		super();
		if(entity == null){
			return;
		}
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				String name = field.getName();
				int mod = field.getModifiers();
				if(Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod)){
					continue;
				}
				if (name.equalsIgnoreCase("SerialVersionUID")) {
					continue;
				}
				if(field.get(entity) == null){
					continue;
				}
				put((K) name, (V) field.get(entity));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} 
		}
	}
}
