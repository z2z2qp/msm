package com.will.utils.jsonparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的List解析器
 * <p/>
 * 可以指定List每个成员的解析器 也可以统一指定为同一个解析器
 *
 * @author DJ
 */

/**
 * 简单的List解析器
 * 可以指定List某个位置的成员的特殊解析器 也可以统一指定为同一个解析器
 * <p/>
 * 在解析时，如果有特殊解析器，则用特殊解析器进行解析
 * 否则会用统一解析器进行解析
 *
 * @param <E> 为List成员模板类
 */
public class JSONArrayParser<E> implements JSONParser<List<E>> {
    private Map<String, JSONParser<?>> indexItemParsers = new HashMap<>();
    private JSONParser<?> commonItemParsers = null;

    /**
     * 构造函数
     */
    public JSONArrayParser() {
    }

    /**
     * 构造函数
     *
     * @param arrayItemParser 所有对象共用的解析器
     */
    public JSONArrayParser(JSONParser<E> arrayItemParser) {
        this.addArrayItemParser(arrayItemParser);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> parse(String str) throws ParseErrorException {
        List<E> ret = new ArrayList<E>();
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
        if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;

            for (int i = 0; i < jsonArray.size(); i++) {
                Object jObject = jsonArray.get(i);
                if (jObject == null || jObject.toString() == null
                        || jObject.toString().equals("")) {
                    continue;
                }

                if (jObject instanceof JSONObject
                        || jObject instanceof JSONArray) {
                    JSONParser<?> parser = getParser(i);
                    if (parser == null) {
                        throw new ParseErrorException("没有可用的解析器用来解析位置" + i
                                + "的JSON对象" + ":" + str);
                    } else {
                        Object retObj = parser.parse(jObject.toString());
                        if (retObj == null) {
                            throw new ParseErrorException("无法将数据转换成List的Bean"
                                    + ":" + str);
                        }
                        ret.add((E) retObj);
                    }
                } else {//如果不是JSONObject或JSONArray说明已成功解析，一般为基础数据类型
                    ret.add((E) jObject);
                }
            }
        } else {
            throw new ParseErrorException("无法解析数据，设置的解析器只能解析JSONArray" + ":"
                    + str);
        }
        return ret;
    }

    /**
     * 为List解析器添加 所有item公用的解析器
     *
     * @param arrayItemParser
     * @return
     */
    public JSONArrayParser<E> addArrayItemParser(JSONParser<?> arrayItemParser) {
        this.commonItemParsers = arrayItemParser;
        return this;
    }

    /**
     * 为List解析器添加某个index位置的解析器
     *
     * @param index
     * @param arrayItemParser
     * @return
     */
    public JSONArrayParser<E> addArrayItemParser(int index,
                                                 JSONParser<?> arrayItemParser) {
        this.indexItemParsers.put("" + index, arrayItemParser);
        return this;
    }

    /**
     * 按照规则获取解析器
     *
     * @param index
     * @return
     */
    private JSONParser<?> getParser(int index) {
        JSONParser<?> parser = null;
        parser = indexItemParsers.get("" + index);
        if (parser != null)
            return parser;

        return commonItemParsers;
    }
}
