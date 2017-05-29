package com.will.utils

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.text.DecimalFormat
import java.util.*

/**
 * Created by zoumy on 2017/5/15 14:26.
 */
object FileUtils : org.apache.commons.io.FileUtils() {


    /**
     * 检查目录是否存在，不存在创建之
     */
    fun mkdir(dirPath:String){
        val dir = File(dirPath)
        if(!dir.exists() && !dir.isDirectory){
            dir.mkdirs()
        }
    }


    /**
     * 检查同名文件是否存在
     */
    fun isExist(filePath:String): Boolean {
        val file = File(filePath)
        return file.exists()
    }


    /**
     * 检查文件名为filepath，并且md5为filemd5的文件是否存在
     */
    fun isExist(filePath:String,fileMd5:String?):Boolean{
        if (!isExist(filePath) || fileMd5 == null){
            return false
        }
        return   checkFileMd5(filePath,fileMd5)
    }


    /**
     * 获取文件扩展名
     */
    fun getExtension(fileName: String):String{
        if (fileName.isNotBlank()){
            val dot = fileName.lastIndexOf('.')
            if (dot > 0 && dot < fileName.length){
                return fileName.substring(dot + 1)
            }
        }
        return ""

    }

    /**
     * 获取文件名，不带扩展名
     */
    fun getNameWithoutExtension(fileName:String):String{
        if (fileName.isNotBlank()){
            val dot = fileName.lastIndexOf('.')
            if (dot > 0 && dot < fileName.length){
                return fileName.substring(0,dot)
            }
        }
        return fileName
    }

    /**
     * 重命名文件名，在文件名和扩展名中添加token
     */

    fun rename(fileName: String,token:String):String{
        val name = getNameWithoutExtension(fileName)
        val ext = getExtension(fileName)
        return "$name$token.$ext"
    }

    /**
     * 写入文件，返回文件名 ,写入失败，返回null
     */
    fun write(file:File,path:String):String?{
        val uploadFile = File(path)
        val byteArray = file.readBytes()
        uploadFile.writeBytes(byteArray)
        return uploadFile.name
    }


    /**
     * 判断相同文件是否存在，如果同名但不同文件存在，进行重命名并保存 返回文件名 ,写入失败，返回null
     */
    fun writeTo(file:File,path: String,fileMd5: String? = null):String?{
        var filePath = path
        if(!isExist(path,fileMd5)){//不存在一样的文件
            if(isExist(path)){//存在同名文件
                filePath = rename(path,UUID.randomUUID().toString())
            }
            return write(file,filePath)
        }
        return File(path).name
    }


    /**
     * 检查文件的md5是否为参数md5对应的值
     */

    fun checkFileMd5(filePath: String?,md5:String?):Boolean{
        if (filePath == null || md5 == null){
            return false
        }
        val fileMd5:String = getMd5ByFile(filePath)
        return fileMd5.equals(md5,true)
    }


    /**
     * 获得流中数据的md5
     *
     * @param input
     * @return
     */
    fun getMd5ByFile(input:InputStream):String{
        val value:String
        val md5 = MessageDigest.getInstance("MD5")
        val byteIterator = BufferedInputStream(input).iterator()
        while (byteIterator.hasNext()){
            md5.update(byteIterator.nextByte())
        }
        val bi = BigInteger(1,md5.digest())
        value = bi.toString(16)
        return value
    }

    /**
     * 获得文件的md5
     *
     * @param file
     * @return
     */
    fun getMd5ByFile(file:File):String{
        return getMd5ByFile(FileInputStream(file))
    }


    /**
     * 获得文件的md5
     *
     * @param filename
     * @return
     */
    fun getMd5ByFile(fileName: String):String{
        return getMd5ByFile(File(fileName))
    }


    /**
     * 格式化文件大小
     */
    fun formatFileSize(size:Double):String{
        val format = DecimalFormat("0.00")
        val kiloByte = size / 1024
        if(kiloByte < 1){
            return "$size Byte(s)"
        }
        val megaByte = kiloByte / 1024
        if(megaByte < 1){
            return "${format.format(kiloByte)} KB"
        }
        val gigaByte = megaByte / 1024
        if(gigaByte < 1){
            return "${format.format(megaByte)} MB"
        }
        val teraByte = gigaByte / 1024
        if (teraByte < 1){
            return "${format.format(gigaByte)} GB"
        }
        return "${format.format(teraByte)} TB"
    }
}