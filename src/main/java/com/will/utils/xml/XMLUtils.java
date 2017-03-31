/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.will.utils.xml;


import com.will.utils.Value;
import org.dom4j.*;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created by Will on 2016/9/9 16:18.
 * 实现xml与Map之间转换
 * 支持从文件，输入流，字符串xml转Map
 * Map转xml文件，输出流，字符串XML
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;XX&gt;
 *     &lt;a type="1"&gt;111&lt;/a&gt;
 *     &lt;a id="456" type="2"&gt;2&lt;/a&gt;
 * &lt;/XX&gt;
 *
 * {XX=
 *      {a=[
 *           {id=123, value=111, type=1},
 *           {id=456,value=2, type=2}
 *          ]
 *       }
 *  }
 */
public class XMLUtils {

    private static final Logger log = LoggerFactory.getLogger(XMLUtils.class);

    /**
     * 传入一个xml文件解析成map
     *
     * @param file the File
     * @return the map
     * @throws IOException       the io exception
     * @throws DocumentException the document exception
     * @Auth Will
     * @Date 2016 -09-09 16:51:47
     */
    public static XMLObject xml2Map(File file) {
        XMLObject map = new XMLObject();
        try {
            map =  xml2Map(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            log.error("xml to map error:{}", e.getMessage(), e);
        }
        return map;
    }

    /**
     * 传入一个inputstream 解析成map
     *
     * @param is the InputStream
     * @return the map
     * @throws DocumentException the document exception
     * @Auth Will
     * @Date 2016 -09-09 16:51:47
     */
    public static XMLObject xml2Map(InputStream is) {
        StringBuffer sb = new StringBuffer();
        String str = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            log.error("xml to map error:{}", e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return xml2Map(sb.toString());
    }

    /**
     * 传入一个xml字符串解析成map
     *
     * @param xmlStr the xml str
     * @return the map
     * @Auth Will
     * @Date 2016 -09-09 16:51:47
     */
    public static XMLObject xml2Map(String xmlStr) {
        XMLObject map = new XMLObject();
        try {
            Document doc = DocumentHelper.parseText(xmlStr);
            Element rootElement = doc.getRootElement();
            XMLObject tm = new XMLObject();
            map.put(rootElement.getName(), tm);
            xml2Map(rootElement, tm);
        } catch (DocumentException e) {
            log.error("xml to map error:{}", e.getMessage(), e);
        }
        return map;

    }

    /**
     * map 包装成XML
     *
     * @param map    the map
     * @param writer the writer writer对象用于写
     * @return the string writer.toString 当为StringWriter时，才是需要的可读的String
     * @Auth Will
     * @Date 2016 -09-13 13:28:45
     */
    public static String map2Xml(Map<String, Object> map, Writer writer) {
        String result = "";
        Document document = DocumentHelper.createDocument();
        String rootString = map.keySet().iterator().next();
        Element root = document.addElement(rootString);
        map2Xml((Map) map.get(rootString), root);
        XMLWriter xw = null;
        try {
            xw = new XMLWriter(writer);
            xw.setEscapeText(false);
            xw.write(document);
            writer.flush();
            result = writer.toString();
        } catch (IOException e) {
            log.error("map to xml error:{}", e.getMessage(), e);
        } finally {
            if (xw != null) {
                try {
                    xw.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * map 包装成xml 并输出到File
     *
     * @param map  the map
     * @param file the file
     * @Auth Will
     * @Date 2016 -09-12 15:47:08
     */
    public static String map2Xml(Map<String, Object> map, File file) {
        String result = "";
        try (OutputStream os = new FileOutputStream(file)) {
            result = map2Xml(map, os);
        } catch (IOException e) {
            log.error("map to xml error:{}", e.getMessage(), e);
        }
        return result;
    }


    /**
     * map 包装成xml 以流形式输出
     *
     * @param map the map
     * @param os  the os
     * @return the output stream
     * @Auth Will
     * @Date 2016 -09-12 15:54:33
     */
    public static String map2Xml(Map<String, Object> map, OutputStream os) {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        return map2Xml(map, osw);
    }


    /**
     * map 包装成xml 并输出字符串
     *
     * @param map the map
     * @return the string
     * @Auth Will
     * @Date 2016 -09-12 15:47:08
     */
    public static String map2Xml(Map<String, Object> map) {
        String result = "";
        try (StringWriter sw = new StringWriter()) {
            result = map2Xml(map, sw);
        } catch (IOException e) {
            log.error("",e);
        }
        return result;
    }


    /**
     * 核心方法解析xml2map  根据 http://www.tuicool.com/articles/m2Afym 修改
     *
     * @param element
     * @param object
     * @return
     */
    private static XMLObject xml2Map(Element element, XMLObject object) {
        //加入属性
        List<Attribute> attributes = element.attributes();
        for (Attribute attribute : attributes) {
            object.put(attribute.getName(), attribute.getValue());
        }
//        System.out.println(element);
        List<Element> elements = element.elements();
        if (elements.size() == 0) {//没有子节点即标签内文本设置key为value
            object.put("value", element.getTextTrim());
//            if (!element.isRootElement()) {
//                return element.getTextTrim();
//            }
        } else if (elements.size() == 1) {
            XMLObject tm = new XMLObject();
            object.put(elements.get(0).getName(), xml2Map(elements.get(0), tm));
        } else if (elements.size() > 1) {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            Map<String, Element> tempMap = new HashMap<String, Element>();
            for (Element ele : elements) {
                tempMap.put(ele.getName(), ele);
            }
            Set<Map.Entry<String, Element>> entrySet = tempMap.entrySet();
            for (Map.Entry<String, Element> entry : entrySet) {
                Namespace namespace = entry.getValue().getNamespace();
                List<Element> elements2 = element.elements(new QName(entry.getKey(), namespace));
                // 如果同名的数目大于1则表示要构建list
                if (elements2.size() > 1) {
                    List<Object> list = new ArrayList<Object>();
                    for (Element ele : elements2) {
                        XMLObject tm = new XMLObject();
                        list.add(xml2Map(ele, tm));
                    }
                    object.put(entry.getKey(), list);
                } else {
                    // 同名的数量不大于1则直接递归去
                    XMLObject tm = new XMLObject();
                    object.put(entry.getKey(), xml2Map(elements2.get(0), tm));
                }
            }
        }

        return object;
    }


    /**
     * map 转xml 核心方法
     *
     * @param map
     * @param element
     */
    private static void map2Xml(Map<String, Object> map, Element element) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {//迭代map
            if (entry.getValue() instanceof String || entry.getValue() instanceof Number) {//如果value是string直接写入
                if("value".equals(entry.getKey())){//如果key是value则直接添加到text
                    element.addText(Value.of(entry.getValue(),""));
                }else{
                    element.addAttribute(entry.getKey(),Value.of(entry.getValue(),""));
                }
            } else if (entry.getValue() instanceof Map) {//如果value是map 递归
                map2Xml((Map) entry.getValue(), element.addElement(new QName(entry.getKey())));
            } else if (entry.getValue() instanceof List) {//如果value是list
                List values = (List) entry.getValue();
                for (Object o : values) {//迭代list value
                    if (o instanceof String || o instanceof Number) {//如果是string 直接写入
//                        element.addElement(entry.getKey()).addText(String.valueOf(o));
                        if("value".equals(entry.getKey())){
                            element.addText(Value.of(entry.getValue(),""));
                        }else{
                            element.addAttribute(entry.getKey(),String.valueOf(o));
                        }
                    } else {//应该不会有map以为类型 递归
                        map2Xml((Map) o, element.addElement(new QName(entry.getKey())));
                    }
                }
            }

        }
    }

    public static void main(String[] args){
    }
}
