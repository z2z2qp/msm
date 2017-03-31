package com.will.utils.jsonparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 简单的bean类解析器
 *
 * @param <E> E为解析出来的对象类
 */
public class JSONBeanParser<E> implements JSONParser<E> {
    private Class<?> objectParserClass;
    private Map<String, JSONParser<?>> attrParser = new HashMap<String, JSONParser<?>>();

    /**
     * 构造函数
     *
     * @param objectParserClass 要解析成哪个bean类
     * @param attrParsers       属性解析器map
     */
    public JSONBeanParser(Class<?> objectParserClass,
                          Map<String, JSONParser<?>> attrParsers) {
        this.objectParserClass = objectParserClass;
        this.addAttrParsers(attrParsers);
    }

    /**
     * 构造函数
     *
     * @param objectParserClass 需要解析成哪个bean类
     */
    public JSONBeanParser(Class<?> objectParserClass) {
        this.objectParserClass = objectParserClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E parse(String str) throws ParseErrorException {
        E ret = null;
        if (str == null)
            return null;
        Object obj = parseObj(str);
//        try {
//            obj = JSON.parse(str);
//            if (obj == null)
//                throw new ParseErrorException("无法将数据转换成JSONObect或JSONArray:"
//                        + str);
//        } catch (Exception e) {
//            throw new ParseErrorException(e.getMessage());
//        }

        if (obj instanceof JSONObject) {
            try {
                ret = (E) JSONObject.parseObject(str, objectParserClass);
                if (ret == null) {
                    throw new ParseErrorException("无法将数据转换成Bean"
                            + objectParserClass.getSimpleName() + ":" + str);
                }
            } catch (Exception e) {
                throw new ParseErrorException("无法将JSON数据转换成Bean"
                        + objectParserClass.getSimpleName() + ":" + str);
            }
            for (Entry<String, JSONParser<?>> entry : attrParser.entrySet()) {
                String attrName = entry.getKey();
                JSONParser<?> parser = entry.getValue();
                String attrJsonStr = ((JSONObject) obj).getString(attrName);
                if (attrJsonStr == null || attrJsonStr.equals(""))
                    continue;

                Field field;
                try {
                    field = ret.getClass().getDeclaredField(attrName);
                } catch (Exception e) {
                    throw new ParseErrorException("无法从类"
                            + ret.getClass().getSimpleName() + "中获得属性"
                            + attrName + ":" + str);
                }
                field.setAccessible(true);
                Object fieldRet = parser.parse(attrJsonStr);
                try {
                    field.set(ret, fieldRet);
                } catch (Exception e) {
                    throw new ParseErrorException("无法设置类"
                            + ret.getClass().getSimpleName() + "的属性"
                            + attrName + ":" + str);
                }
            }
        } else if (obj instanceof JSONArray) {
            throw new ParseErrorException("无法解析数据，设置的解析器只能解析JSONObject" + ":"
                    + str);
        } else {
            ret = (E) obj;
        }
        return ret;
    }

    /**
     * 为该对象添加属性解析器map
     * <p/>
     * map.entry.key为属性字段名
     * map.entry.val为解析器
     *
     * @param attrParsers
     * @return 自身
     */
    public JSONBeanParser<E> addAttrParsers(
            Map<String, JSONParser<?>> attrParsers) {
        this.attrParser.putAll(attrParsers);
        return this;
    }

    /**
     * 为该对象添加属性解析器map
     *
     * @param key        属性字段名
     * @param attrParser 解析器
     * @return 自身
     */
    public JSONBeanParser<E> addAttrParser(String key, JSONParser<?> attrParser) {
        this.attrParser.put(key, attrParser);
        return this;
    }
}
