package com.will.utils

import java.awt.Color
import java.awt.Font
import java.awt.LinearGradientPaint
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import javax.imageio.ImageIO

/**
 * Created by zoumy on 2017/5/11 13:47.
 */
object VerifyCodeUtils {

    private val VERIFY_CODES = "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ"//剔除0o Il易混淆字符
//	public static final String VERIFY_CODES = "1234567890";


    /**
     * 使用系统默认字符源生成验证码
     * @param verifySize    验证码长度
     * *
     * @return
     */
    private fun generateVerifyCode(verifySize: Int): String {
        return generateVerifyCode(verifySize, VERIFY_CODES)
    }

    /**
     * 使用指定源生成验证码
     * @param verifySize    验证码长度
     * *
     * @param sources    验证码字符源
     * *
     * @return
     */
    private fun generateVerifyCode(verifySize: Int, sources: String?): String {
        var newSources = sources
        if (newSources == null || newSources.length == 0) {
            newSources = VERIFY_CODES
        }
        val codesLen = newSources.length
        val rand = Random(System.currentTimeMillis())
        val verifyCode = StringBuilder(verifySize)
        for (i in 0..verifySize - 1) {
            verifyCode.append(newSources[rand.nextInt(codesLen - 1)])
        }
        return verifyCode.toString()
    }

    /**
     * 生成随机验证码文件,并返回验证码值
     * @param width 图片宽
     * *
     * @param height 图片高
     * *
     * @param outputFile 文件
     * *
     * @param verifySize 验证码长度
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun outputVerifyImage(width: Int, height: Int, outputFile: File, verifySize: Int): String {
        val verifyCode = generateVerifyCode(verifySize)
        outputImage(width, height, outputFile, verifyCode)
        return verifyCode
    }

    /**
     * 输出随机验证码图片流,并返回验证码值
     * @param width 图片宽
     * *
     * @param height 图片高
     * *
     * @param os 输出流
     * *
     * @param verifySize 验证码长度
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun outputVerifyImage(width: Int, height: Int, os: OutputStream, verifySize: Int): String {
        val verifyCode = generateVerifyCode(verifySize)
        outputImage(width, height, os, verifyCode)
        return verifyCode
    }

    /**
     * 生成指定验证码图像文件
     * @param width 图片宽
     * *
     * @param height 图片高
     * *
     * @param outputFile 输出文件
     * *
     * @param code 验证码值
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun outputImage(width: Int, height: Int, outputFile: File?, code: String) {
        if (outputFile == null) {
            return
        }
        val dir = outputFile.parentFile
        if (!dir.exists()) {
            dir.mkdirs()
        }
        try {
            outputFile.createNewFile()
            val fos = FileOutputStream(outputFile)
            outputImage(width, height, fos, code)
            fos.close()
        } catch (e: IOException) {
            throw e
        }

    }

    /**
     * 输出指定验证码图片流
     * @param width 图片宽
     * *
     * @param height 图片高
     * *
     * @param os 输出流
     * *
     * @param code 验证码值
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun outputImage(width: Int,
                            height: Int,
                            os: OutputStream,
                            code: String) {
        val verifySize = code.length
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val rand = Random()
        val g2 = image.createGraphics()
        val colors = arrayOfNulls<Color>(5)
        val colorSpaces = arrayOf(Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW)
        val fractions = FloatArray(colors.size)
        for (i in colors.indices) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.size)]
            fractions[i] = rand.nextFloat()
        }
        Arrays.sort(fractions)
        val linearPaint = LinearGradientPaint(0f, 0f, width.toFloat(), height.toFloat(), fractions, colors)
        val linearPaint2 = LinearGradientPaint(0f, 0f, width.toFloat(), height.toFloat(), floatArrayOf(0.3f, .6f, .8f, .9f), arrayOf(Color.BLUE, Color.BLACK, Color.GREEN, Color.BLUE))
        //设置图片背景为白色
        //		g2.setPaint(Color.WHITE);
        //		g2.fillRect(0, 0, w, h);
        //设置图片渐变背景
        g2.paint = linearPaint
        g2.fillRoundRect(0, 0, width, height, 5, 5)

        g2.paint = linearPaint2
        val fontSize = Math.min(width / verifySize, height).toInt() + 2
        val font = Font("微软雅黑", Font.BOLD, fontSize)
        g2.font = font
        val chars = code.toCharArray()
        for (i in 0..verifySize - 1) {
            val affine = AffineTransform()
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (if (rand.nextBoolean()) 1 else -1).toDouble(), (width / verifySize * i + fontSize / 2).toDouble(), (height / 2).toDouble())
            g2.transform = affine
            g2.drawChars(chars, i, 1, width / verifySize * i, height / 2 + fontSize / 2)
        }
        g2.dispose()
        ImageIO.write(image, "jpg", os)
    }
}