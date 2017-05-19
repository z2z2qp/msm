package com.will.utils

import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.Reader
import java.util.*

/**
 * Created by zoumy on 2017/5/15 10:27.
 */
object PropertyLoader {
    val log = LoggerFactory.getLogger(PropertyLoader.javaClass)

    /**
     * 根据Reader读取Properties
     *
     * @param reader the reader
     * @return the properties
     */
    private fun getProperties(read: Reader): Properties {
        val properties = Properties()
        properties.load(read)
        read.close()
        return properties
    }

    /**
     * 根据文件绝对路径字符集编码读取
     *
     * @param filePath    the filepath
     * @param charsetName the charset name
     * @return the properties from absolute path
     */
    fun getPropertiesFromAbsolutePath(filePath:String,charsetName:String = "UTF-8"):Properties{
        val reader = InputStreamReader(FileInputStream(filePath),charsetName)
        return getProperties(reader)
    }

    /**
     * 根据文件相对项目路径符集编码读取
     *
     * @param filePath    the filepath
     * @param charsetName the charset name
     * @return the properties from class path
     */
    fun getPropertiesFromClassPath(filePath: String,charsetName: String = "UTF-8"):Properties{
        val reader = InputStreamReader(PropertyLoader.javaClass.classLoader.getResourceAsStream(filePath),charsetName)
        return getProperties(reader)
    }
}