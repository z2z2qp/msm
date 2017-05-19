package com.will.framework.entity

import java.lang.reflect.Modifier
import java.util.*

/**
 * Created by zoumy on 2017/5/11 11:14.
 */
class MapEntity : HashMap<String, Any>,IEntity{
    val serialVersionUID:Long = -992107495647664914L
    constructor(initialCapacity: Int, loadFactor: Float) : super(initialCapacity, loadFactor)
    constructor(initialCapacity: Int) : super(initialCapacity)
    constructor() : super()
    constructor(m: MutableMap<out String, out Any>?) : super(m)
    constructor(entity: BaseEntity):super(){
        val clazz = entity.javaClass
        val fields = clazz.declaredFields
        for (field in fields){
            try {
                field.isAccessible = true
                val name = field.name
                var mod = field.modifiers
                if(Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod)){
                    continue
                }
                if(name.equals("SerialVersionUID",true)){
                    continue
                }
                if(field.get(entity) == null){
                    continue
                }
                put(name,field.get(entity))
            }catch (e:IllegalArgumentException){
                e.printStackTrace()
            }catch (e:IllegalAccessException){
                e.printStackTrace()
            }

        }
        fields.forEach {
        }
    }
}