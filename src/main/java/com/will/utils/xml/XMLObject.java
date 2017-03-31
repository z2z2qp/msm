package com.will.utils.xml;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Will on 2016/10/18 9:49.
 */
public class XMLObject implements Map<String,Object>,Cloneable, Serializable {
    private static final long serialVersionUID = -7006835342455866809L;
    /**
     * 初始容量
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final Map<String, Object> map;


    /**
     * 无参构造器
     *
     * @Auth Will
     * @Date 2017 -03-27 10:09:12
     */
    public XMLObject() {
        this(DEFAULT_INITIAL_CAPACITY, false);
    }

    /**
     * 传入一个Map的构造器
     *
     * @param map the map
     * @Auth Will
     * @Date 2017 -03-27 10:09:19
     */
    public XMLObject(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 是否排序构造器
     *
     * @param ordered the ordered
     * @Auth Will
     * @Date 2017 -03-27 10:09:19
     */
    public XMLObject(boolean ordered) {
        this(DEFAULT_INITIAL_CAPACITY, ordered);
    }

    /**
     * 传入初始容量的构造器
     *
     * @param initialCapacity the initial capacity
     * @Auth Will
     * @Date 2017 -03-27 10:09:19
     */
    public XMLObject(int initialCapacity) {
        this(initialCapacity, false);
    }

    /**
     * 传入初始容量以及是否排序
     *
     * @param initialCapacity the initial capacity
     * @param ordered         the ordered
     * @Auth Will
     * @Date 2017 -03-27 10:09:19
     */
    public XMLObject(int initialCapacity, boolean ordered) {
        if(ordered) {
            this.map = new LinkedHashMap(initialCapacity);
        } else {
            this.map = new HashMap(initialCapacity);
        }

    }

    /**
     * Size int.
     *
     * @return the int
     * @Auth Will
     * @Date 2017 -03-28 09:39:20
     */
    @Override
    public int size() {
        return this.map.size();
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     * @Auth Will
     * @Date 2017 -03-28 09:39:20
     */
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /**
     * Contains key boolean.
     *
     * @param key the key
     * @return the boolean
     * @Auth Will
     * @Date 2017 -03-28 09:39:20
     */
    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    /**
     * Contains value boolean.
     *
     * @param value the value
     * @return the boolean
     * @Auth Will
     * @Date 2017 -03-28 09:39:21
     */
    @Override
    public boolean containsValue(Object value) {
        return this.map.containsKey(value);
    }

    /**
     * Get object.
     *
     * @param key the key
     * @return the object
     * @Auth Will
     * @Date 2017 -03-28 09:39:21
     */
    @Override
    public Object get(Object key) {
        return this.map.get(key);
    }

    /**
     * 根据Key获取一个XMLObject 可能会空，可能会报类型转换异常
     *
     * @param key the key
     * @return the xml object
     */
    public XMLObject getXMLObject(String key) {
        Object value = this.map.get(key);
        if(value instanceof XMLObject || value == null){
            return (XMLObject) value;
        }else{
            throw new ClassCastException(value.getClass().getName() + "不能转换为XMLObject");
        }
    }

    /**
     * 根据Key获取一个List&lt;XMLObject&gt; 可能会空，可能会报类型转换异常
     *
     * @param key the key
     * @return the list
     * @Auth Will
     * @Date 2017 -03-27 10:09:19
     */
    public List<XMLObject> getList(String key){
        Object value = this.map.get(key);
        if(value instanceof List || value == null){
            return (List) value;
        }else{
            throw new ClassCastException(value.getClass().getName() + " 不能转换为List");
        }
    }

    /**
     * 获取一个XMLObject对象下的value
     *
     * @return the value
     */
    public Object getValue() {
        return this.map.get("value");
    }


    /**
     * 设置XMLObject对象下的value值
     *
     * @param value the value
     * @return the object
     * @Auth Will
     * @Date 2017 -03-28 09:39:21
     */
    public Object setValue(Object value){
        return this.map.put("value",value);
    }

    /**
     * 递归获取某节点下所有子集
     * @param key
     * @return
     */
    public Object recursionValue(String key){
        Object o = null;
        for(Map.Entry<String,Object> entry:this.entrySet()){
            if(entry.getKey().equals(key)){
                o = entry.getValue();
                break;
            }else{
                if(entry.getValue() instanceof XMLObject){
                    o = ((XMLObject)entry.getValue()).recursionValue(key);
                }
            }
        }
        return o;
    }

    @Override
    public Object put(String key, Object value) {
        return this.map.put(key,value);
    }

    @Override
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public Object clone() {
        return new XMLObject(new HashMap(this.map));
    }

    @Override
    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
