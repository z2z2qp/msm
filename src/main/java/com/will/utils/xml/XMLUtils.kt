package com.will.utils.xml

import com.will.utils.Value
import org.dom4j.*
import org.dom4j.io.XMLWriter
import org.slf4j.LoggerFactory
import java.io.*
import java.util.*

/**
 * Created by zoumy on 2017/5/12 13:45.
 */
object XMLUtils {

    private val log = LoggerFactory.getLogger(XMLUtils::class.java)

    /**
     * 传入一个xml文件解析成map

     * @param file the File
     * *
     * @return the map
     * *
     * @throws IOException       the io exception
     * *
     * @throws DocumentException the document exception
     * *
     * @Auth Will
     * *
     * @Date 2016 -09-09 16:51:47
     */
    fun xml2XMLObject(file: File): XMLObject {
        var map = XMLObject()
        try {
            map = xml2XMLObject(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            log.error("xml to map error:{}", e.message, e)
        }

        return map
    }

    /**
     * 传入一个inputstream 解析成map

     * @param is the InputStream
     * *
     * @return the map
     * *
     * @throws DocumentException the document exception
     * *
     * @Auth Will
     * *
     * @Date 2016 -09-09 16:51:47
     */
    fun xml2XMLObject(`is`: InputStream?): XMLObject {
        val sb = StringBuilder()
        var str = ""
        try {
            BufferedReader(InputStreamReader(`is`!!)).use { br ->

                br.forEachLine { sb.append(str) }
            }
        } catch (e: IOException) {
            log.error("xml to map error:{}", e.message, e)
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                }

            }
        }
        return xml2XMLObject(sb.toString())
    }

    /**
     * 传入一个xml字符串解析成map

     * @param xmlStr the xml str
     * *
     * @return the map
     * *
     * @Auth Will
     * *
     * @Date 2016 -09-09 16:51:47
     */
    fun xml2XMLObject(xmlStr: String): XMLObject {
        val map = XMLObject()
        try {
            val doc = DocumentHelper.parseText(xmlStr)
            val rootElement = doc.rootElement
            val tm = XMLObject()
            map.put(rootElement.name, tm)
            xml2XMLObject(rootElement, tm)
        } catch (e: DocumentException) {
            log.error("xml to map error:{}", e.message, e)
        }

        return map

    }

    /**
     * map 包装成XML

     * @param map    the map
     * *
     * @param writer the writer writer对象用于写
     * *
     * @return the string writer.toString 当为StringWriter时，才是需要的可读的String
     * *
     * @Auth Will
     * *
     * @Date 2016 -09-13 13:28:45
     */
    fun map2Xml(map: XMLObject, writer: Writer): String {
        var result = ""
        val document = DocumentHelper.createDocument()
        val rootString = map.keys.iterator().next()
        val root = document.addElement(rootString)
        map2Xml(map[rootString] as Map<String, Any>,root)
//        map2Xml(map[rootString] as Map<*, *>?, root)
        var xw: XMLWriter? = null
        try {
            xw = XMLWriter(writer)
            xw.isEscapeText = false
            xw.write(document)
            writer.flush()
            result = writer.toString()
        } catch (e: IOException) {
            log.error("map to xml error:{}", e.message, e)
        } finally {
            if (xw != null) {
                try {
                    xw.close()
                } catch (e: IOException) {
                }

            }
        }
        return result
    }

    /**
     * map 包装成xml 并输出到File

     * @param map  the map
     * *
     * @param file the file
     * *
     * @Auth Will
     * *
     * @Date 2016 -09-12 15:47:08
     */
    fun map2Xml(map: XMLObject, file: File): String {
        var result = ""
        try {
            FileOutputStream(file).use { os -> result = map2Xml(map, os) }
        } catch (e: IOException) {
            log.error("map to xml error:{}", e.message, e)
        }

        return result
    }


    /**
     * map 包装成xml 以流形式输出

     * @param map the map
     * *
     * @param os  the os
     * *
     * @return the output stream
     * *
     * @Auth Will
     * *
     * @Date 2016 -09-12 15:54:33
     */
    fun map2Xml(map: XMLObject, os: OutputStream): String {
        val osw = OutputStreamWriter(os)
        return map2Xml(map, osw)
    }


    /**
     * map 包装成xml 并输出字符串

     * @param map the map
     * *
     * @return the string
     * *
     * @Auth Will
     * *
     * @Date 2016 -09-12 15:47:08
     */
    fun map2Xml(map: XMLObject): String {
        var result = ""
        try {
            StringWriter().use { sw -> result = map2Xml(map, sw) }
        } catch (e: IOException) {
            log.error("", e)
        }

        return result
    }


    /**
     * 核心方法解析xml2XMLObject  根据 http://www.tuicool.com/articles/m2Afym 修改

     * @param element
     * *
     * @param object
     * *
     * @return
     */
    private fun xml2XMLObject(element: Element, `object`: XMLObject): XMLObject {
        //加入属性
        val attributes = element.attributes() as List<Attribute>
        for (attribute in attributes) {
            `object`.put(attribute.name, attribute.value)
        }
        //        System.out.println(element);
        val elements = element.elements() as List<Element>
        if (elements.size == 0) {//没有子节点即标签内文本设置key为value
            `object`.put("value", element.textTrim)
            //            if (!element.isRootElement()) {
            //                return element.getTextTrim();
            //            }
        } else if (elements.size == 1) {
            val tm = XMLObject()
            `object`.put(elements[0].name, xml2XMLObject(elements[0], tm))
        } else if (elements.size > 1) {
            // 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
            // 构造一个map用来去重
            val tempMap = HashMap<String, Element>()
            for (ele in elements) {
                tempMap.put(ele.name, ele)
            }
            val entrySet = tempMap.entries
            for ((key, value1) in entrySet) {
                val namespace = value1.namespace
                val elements2 = element.elements(QName(key, namespace)) as List<Element>
                // 如果同名的数目大于1则表示要构建list
                if (elements2.size > 1) {
                    val list = ArrayList<Any>()
                    for (ele in elements2) {
                        val tm = XMLObject()
                        list.add(xml2XMLObject(ele, tm))
                    }
                    `object`.put(key, list)
                } else {
                    // 同名的数量不大于1则直接递归去
                    val tm = XMLObject()
                    `object`.put(key, xml2XMLObject(elements2[0], tm))
                }
            }
        }

        return `object`
    }


    /**
     * map 转xml 核心方法

     * @param map
     * *
     * @param element
     */
    private fun map2Xml(map: Map<String, Any>, element: Element) {
        val entries = map.entries
        for ((key, value1) in entries) {//迭代map
            if (value1 is String || value1 is Number) {//如果value是string直接写入
                if ("value" == key) {//如果key是value则直接添加到text
                    element.addText(Value.of(value1, ""))
                } else {
                    element.addAttribute(key, Value.of(value1, ""))
                }
            } else if (value1 is Map<*, *>) {//如果value是map 递归
                map2Xml(value1 as Map<String, Any>, element.addElement(QName(key)))
            } else if (value1 is List<*>) {//如果value是list
                for (o in value1) {//迭代list value
                    if (o is String || o is Number) {//如果是string 直接写入
                        //                        element.addElement(entry.getKey()).addText(String.valueOf(o));
                        if ("value" == key) {
                            element.addText(Value.of(value1, ""))
                        } else {
                            element.addAttribute(key, o.toString())
                        }
                    } else {//应该不会有map以为类型 递归
                        map2Xml(o as Map<String, Any>, element.addElement(QName(key)))
                    }
                }
            }

        }
    }
}