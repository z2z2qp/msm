package com.will.utils

/**
 * Created by zoumy on 2017/5/12 12:27.
 * 提供16进制与String转换功能
 */
object HexByteUtils {

    /**
     * 将字节数据转换成16进制字符串
     * @param src
     * *
     * @return
     */
    fun byteArrayToHexString(src: ByteArray?): String? {
        val stringBuilder = StringBuilder("")
        if (src == null || src.size <= 0) {
            return null
        }
        for (i in src) {
            val hv = Integer.toHexString(i.toInt().and(0xFF))
            if (hv.length < 2) {
                stringBuilder.append(0)
            }
            stringBuilder.append(hv)
        }
        return stringBuilder.toString()
    }

    /**
     * 将16进制字符串转换成字节数组
     * @param hexString
     * *
     * @return
     */
    fun hexStringToByteArray(hexString: String?): ByteArray? {
        var hexString = hexString
        if (hexString == null || hexString == "") {
            return null
        }
        hexString = hexString.toUpperCase()
        val length = hexString.length / 2
        val hexChars = hexString.toCharArray()
        val d = ByteArray(length)
        for (i in 0..length - 1) {
            val pos = i * 2
            // d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
            d[i] = (charToByte(hexChars[pos])).toInt().shl(4).or(charToByte(hexChars[pos + 1]).toInt()).toByte()
//            d[i] = (charToByte(hexChars[pos]) shl 4 or charToByte(hexChars[pos + 1])).toByte()
        }
        return d
    }


    /**
     * 字符转换为字节
     * @param c
     * *
     * @return
     */
    fun charToByte(c: Char): Byte {
        return "0123456789ABCDEF".indexOf(c).toByte()
    }
}