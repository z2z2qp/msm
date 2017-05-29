package com.will.utils.barcode

import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.will.utils.FileUtils
import java.awt.image.BufferedImage
import java.io.File
import java.io.OutputStream
import java.util.*
import javax.imageio.ImageIO

/**
 * Created by zoumy on 2017/5/12 16:52.
 */
object BarCodeUtils{
    val CHARSET = "utf-8"
    val FORMAT_NAME = "JPG"
    //二维码尺寸
    val QRCODE_SIZE = 300
    //logo 宽度
    val WIDTH = 100
    //logo 高度
    val HEIGHT = 100

    private fun  createImage(content:String,imgPath:String?,needCompress:Boolean = false): BufferedImage {
        val hits = Hashtable<EncodeHintType,Any>()
        hits.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H)
        hits.put(EncodeHintType.CHARACTER_SET, CHARSET)
        hits.put(EncodeHintType.MARGIN,1)
        val bitMatrix = MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,hits)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val image = BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB)
        for (x in 0 .. width -1){
            for (y in 0 .. height - 1){
                image.setRGB(x, y, if (bitMatrix.get(x, y))  0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }
        if(imgPath.isNullOrBlank()){
            return image
        }
        insertImage(image, imgPath, needCompress);
        return image
    }

    /**
     * 插入logo
     */
    private fun insertImage(source:BufferedImage,imgPath: String?,needCompress: Boolean){
        var file = File(imgPath)
        if(!file.exists()){
            println("$imgPath 该文件不存在")
            return
        }
    }

    /**
     * 生成二维码文件
     */
    fun encode(content: String,destPath:String,imgPath: String? = null,needCompress: Boolean = false){
        val image = createImage(content,imgPath,needCompress)
        FileUtils.mkdir(destPath)
        ImageIO.write(image, FORMAT_NAME,File(destPath))
    }

    /**
     * 生成二维码输出流
     */
    fun encdoe(content: String,outPut:OutputStream,imgPath: String? = null,needCompress: Boolean = false){
        val image = createImage(content,imgPath,needCompress)
        ImageIO.write(image, FORMAT_NAME,outPut)
    }

    /**
     * 解析二维码
     */
    fun decode(file:File):String?{
        val image = ImageIO.read(file) ?: return null

        val source = BufferedImageLuminanceSource(image)
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        val hits:Hashtable<DecodeHintType,Any>  = Hashtable()
        hits.put(DecodeHintType.CHARACTER_SET, CHARSET)
        val result = MultiFormatReader().decode(bitmap,hits)
        return result.text

    }

    fun  decode(path:String):String?{
        return decode(File(path))
    }
}