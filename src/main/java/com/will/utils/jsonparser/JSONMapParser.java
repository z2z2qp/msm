package com.will.utils.jsonparser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 简单的Map解析器
 * <p/>
 * 用于指定哪一个key对应的val使用哪个解析器来解析
 *
 * @author DJ
 */
public class JSONMapParser implements JSONParser<Map<String, Object>> {
    private Map<String, JSONParser<?>> mapItemParsers = new HashMap<>();

    public JSONMapParser() {
    }

    /**
     * 构造函数
     *
     * @param mapItemParsers 每个key对应的解析器
     */
    public JSONMapParser(Map<String, JSONParser<?>> mapItemParsers) {
        this.addMapItemParsers(mapItemParsers);
    }

    /**
     * 构造函数
     *
     * @param key           map的key
     * @param mapItemParser 此key对应的解析器
     */
    public JSONMapParser(String key, JSONParser<?> mapItemParser) {
        this.addMapItemParser(key, mapItemParser);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> parse(String str) throws ParseErrorException {
        Map<String, Object> ret = null;
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
            ret = JSONObject.parseObject(str, Map.class);
            for (Entry<String, Object> entry : ret.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value != null
                        && !value.equals("")
                        && (value instanceof JSONObject || value instanceof JSONArray)) {
                    JSONParser<?> parser = getParser(key);
                    if (parser != null) {
                        Object valueParsered = parser.parse(value.toString());
                        ret.put(key, valueParsered);
                    }
                }
            }
        } else {
            throw new ParseErrorException("无法解析数据，设置的解析器只能解析JSONObject:" + str);
        }
        return ret;
    }

    /**
     * 添加多个解析器
     *
     * @param mapItemParsers
     * @return 解析器自身
     */
    public JSONMapParser addMapItemParsers(
            Map<String, JSONParser<?>> mapItemParsers) {
        this.mapItemParsers.putAll(mapItemParsers);
        return this;
    }

    /**
     * 为key添加解析器
     *
     * @param key
     * @param mapItemParser
     * @return 解析器自身
     */
    public JSONMapParser addMapItemParser(String key,
                                          JSONParser<?> mapItemParser) {
        this.mapItemParsers.put(key, mapItemParser);
        return this;
    }

    /**
     * 获得某个map中的key的解析器
     *
     * @param key
     * @return
     */
    private JSONParser<?> getParser(String key) {
        if (mapItemParsers == null)
            return null;
        if (!mapItemParsers.containsKey(key))
            return null;

        return mapItemParsers.get(key);
    }
}
