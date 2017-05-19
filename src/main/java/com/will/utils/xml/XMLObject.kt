package com.will.utils.xml

import java.io.Serializable

/**
 * Created by zoumy on 2017/5/11 13:58.
 */
class XMLObject : MutableMap<String,Any>,Serializable {

    val VALUE:String = "value"
    val DEFAULT_INITIAL_CAPACITY:Int = 16
    val map:MutableMap<String,Any>

    constructor(initialCapacity:Int,ordered:Boolean = false){
        if (ordered){
            this.map = LinkedHashMap(initialCapacity)
        }else{
            this.map = HashMap(initialCapacity)
        }
    }

    constructor():this(16)

    constructor(ordered: Boolean):this(16,ordered)

    constructor(map:Map<String,Any>){
        this.map = map as MutableMap<String, Any>
    }

    constructor(initialCapacity: Int):this(initialCapacity,false)

    override val size: Int
        get() = this.map.size

    override fun isEmpty(): Boolean {
        return this.map.isEmpty()
    }

    override fun containsKey(key: String): Boolean {
        return this.map.containsKey(key)
    }

    override fun containsValue(value: Any): Boolean {
        return this.map.containsValue(value)
    }

    override fun get(key: String): Any? {
        return this.map[key]
    }

    override val entries: MutableSet<MutableMap.MutableEntry<String, Any>>
        get() = this.map.entries
    override val keys: MutableSet<String>
        get() = this.map.keys
    override val values: MutableCollection<Any>
        get() = this.map.values

    override fun clear() {
        this.map.clear()
    }

    override fun put(key: String, value: Any): Any? {
        return this.map.put(key,value)
    }

    override fun putAll(from: Map<out String, Any>) {
        this.map.putAll(from)
    }

    override fun remove(key: String): Any? {
        return this.map.remove(key)
    }



    /**
     * 根据Key获取一个XMLObject 可能会空，可能会报类型转换异常

     * @param key the key
     * *
     * @return the xml object
     */
    fun getXMLObject(key: String): XMLObject {
        val value = this.map[key]
        if (value is XMLObject || value == null) {
            return value as XMLObject
        } else {
            throw ClassCastException(value.javaClass.name + "不能转换为XMLObject")
        }
    }


    /**
     * 根据Key获取一个List&lt;XMLObject&gt; 可能会空，可能会报类型转换异常

     * @param key the key
     * *
     * @return the list
     * *
     * @Auth Will
     * *
     * @Date 2017 -03-27 10:09:19
     */
    fun getList(key: String): List<XMLObject> {
        val value = this.map[key]
        if (value is List<*> || value == null) {
            return value as List<XMLObject>
        } else {
            throw ClassCastException(value.javaClass.name + " 不能转换为List")
        }
    }


    /**
     * 获取一个XMLObject对象下的value

     * @return the value
     */
    fun getValue(): Any? {
        return this.map[VALUE]
    }


    /**
     * 设置XMLObject对象下的value值

     * @param value the value
     * *
     * @return the object
     * *
     * @Auth Will
     * *
     * @Date 2017 -03-28 09:39:21
     */
    fun setValue(value: Any): Any? {
        return this.map.put(VALUE, value)
    }

    /**
     * 递归获取某节点下所有子集

     * @param key 关键字
     * *
     * @return 返回的结果
     */
    fun recursionValue(key: String): Any? {
        var o: Any? = null
        for ((key1, value) in this) {
            if (key1 == key) {
                o = value
                break
            } else {
                if (value is XMLObject) {
                    o = value.recursionValue(key)
                }
            }
        }
        return o
    }

    override fun toString(): String {
        var sb:StringBuilder = StringBuilder()
        sb.append("{")
        this.map.forEach { t, u ->
            sb.append("$t:$u\n")
        }
        sb.append("}")
        return sb.toString()
    }

}