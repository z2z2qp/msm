package com.will.utils.barcode

import com.google.zxing.LuminanceSource
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage

/**
 * Created by zoumy on 2017/5/15 8:56.
 */
class BufferedImageLuminanceSource : LuminanceSource {

    val image: BufferedImage
    val left: Int
    val top: Int


    constructor(image: BufferedImage, left: Int = 0, top: Int = 0, width: Int = image.width, height: Int = image.height) : super(width, height) {
        val sourceWidth = image.width
        val sourceHeight = image.height
        if (left + width > sourceWidth || top + height > sourceHeight) {
            throw IllegalArgumentException("Crop rectangle does not fit within image data.")
        }
        for (y in top..top + height) {
            (left..left + width)
                    .filter { image.getRGB(it, y).and(0xFF000000.toInt()) == 0 }
                    .forEach { image.setRGB(it, y, 0xFFFFFFFF.toInt()) }
        }
        this.image = BufferedImage(sourceWidth,sourceHeight,BufferedImage.TYPE_BYTE_GRAY)
        this.image.graphics.drawImage(image,0,0,null)
        this.left = left
        this.top = top
    }

    override fun getRow(y: Int, row: ByteArray?): ByteArray {
        if(y < 0 || y > height){
            throw IllegalArgumentException("Requested row is outside the image: $y")
        }
        var rows = row
        if(rows == null || rows.size < width){
            rows = ByteArray(width)
        }
        image.raster.getDataElements(left,top+y,width,1,rows)
        return rows
    }

    override fun getMatrix(): ByteArray {
        val area = width * height
        val matrix = ByteArray(area)
        image.raster.getDataElements(left,top,width,height,matrix)
        return matrix
    }

    override fun isCropSupported(): Boolean {
        return true
    }

    override fun crop(left: Int, top: Int, width: Int, height: Int): LuminanceSource {
        return BufferedImageLuminanceSource(image,this.left + left,this.top + top,width,height)
    }

    override fun isRotateSupported(): Boolean {
        return true
    }

    override fun rotateCounterClockwise(): LuminanceSource {
        val sourceWidth = image.width
        val sourceHeight = image.height
        val transform = AffineTransform(0.0,-1.0,1.0,0.0,0.0, sourceWidth.toDouble())
        val rotatedImage = BufferedImage(sourceHeight,sourceWidth,BufferedImage.TYPE_BYTE_GRAY)
        val g = rotatedImage.createGraphics()
        g.drawImage(image,transform,null)
        g.dispose()
        return BufferedImageLuminanceSource(rotatedImage,top,sourceWidth - (left + width),height,width)
    }
}