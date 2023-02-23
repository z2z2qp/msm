package com.will.utils.map.baidu

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.will.utils.PropertyLoader
import com.will.utils.Value
import com.will.utils.map.GeoPoint
import com.will.utils.map.MapUtils
import com.will.utils.scure.Md5Secure
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.math.BigDecimal
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.*

@Suppress("UNREACHABLE_CODE")
/**
 * Created by zoumy on 2017/5/11 15:37.
 */
object BaiduUtils : MapUtils() {

    private val log = LoggerFactory.getLogger(BaiduUtils::class.java)
    private var SK: String? = null//= "zMkeFDU6gTnkAkWMMiw8nElvYhYIGOt4";
    private var AK: String? = null//= "APOCRdC8SRKgMTb5oPFqr88I";
    private var isInit = false

    init {
        val properties = PropertyLoader.getPropertiesFromClassPath("baidu.properties")
        SK = properties.getProperty("SK")
        AK = properties.getProperty("AK")
        isInit = true
    }


    private fun init() {
        try {
            val properties = PropertyLoader.getPropertiesFromClassPath("baidu.properties")
            SK = properties.getProperty("SK")
            AK = properties.getProperty("AK")
            isInit = true
        } catch (e: Exception) {
            log.error("other Exception", e)
        }

    }


    /**

     *
     * Title: location2Adress
     *
     * Description:坐标转地址
     * @author 庄佳彬
     * *
     * @param pointLatLng
     * *
     * @return String
     */
    override fun location2Adress(pointLatLng: GeoPoint): String? {
        try {
            val paramsMap = LinkedHashMap<String, String>()
            paramsMap.put("location", pointLatLng.lan.toString() + "," + pointLatLng.lon)
            paramsMap.put("output", "json")
            paramsMap.put("ak", AK!!)
            val queryStr = toQueryString(paramsMap)
            val wholeStr = "/geocoder/v2/?" + queryStr + SK
            val tempStr: String = URLEncoder.encode(wholeStr, "UTF-8")
            val SN = Md5Secure.encode(tempStr)!!
            paramsMap.put("sn", SN)
            val url = URL("http://api.map.baidu.com/geocoder/v2/?" + toQueryString(paramsMap))
            val connection = url.openConnection()

            val `in` = connection.getInputStream()
            var resultJson: String? = null
            val tt = ByteArray(`in`.available())
            while (`in`.read(tt, 0, tt.size) != -1) {
                resultJson = String(tt, Charset.forName("utf-8"))
            }
            return getFormattedAddress(resultJson!!)
        } catch (e: MalformedURLException) {
            log.error("坐标转地址异常", e)
        } catch (e: IOException) {
            log.error("坐标转地址异常", e)
        } catch (e: Exception) {
            log.error("坐标转地址异常", e)
        }
        return null
    }


    /**

     *
     * Title: geoconvPointLatLng
     *
     * Description：坐标转换
     * @author 庄佳彬
     * *
     * @param ps
     * *
     * @return List<PointLatLng>
    </PointLatLng> */
    fun geoconvPointLatLng(ps: List<GeoPoint>): List<GeoPoint>? {
        try {
            val sb = StringBuilder()
            val paramsMap = LinkedHashMap<String, String>()
            for (pointLatLng in ps) {
                sb.append(pointLatLng.lon).append(",").append(pointLatLng.lan)
                sb.append(";")
            }
            paramsMap.put("coords", sb.substring(0, sb.length - 1))
            paramsMap.put("output", "json")
            paramsMap.put("ak", AK!!)
            paramsMap.put("from", "1")
            paramsMap.put("to", "5")
            val queryStr = toQueryString(paramsMap)
            val wholeStr = "/geoconv/v1/?" + queryStr + SK
            val tempStr = URLEncoder.encode(wholeStr, "UTF-8")
            val SN = Md5Secure.encode(tempStr)!!
            paramsMap.put("sn", SN)

            val url = URL("http://api.map.baidu.com/geoconv/v1/?" + toQueryString(paramsMap))
            val connection = url.openConnection()

            val `in` = connection.getInputStream()
            var resultJson: String? = null
            val tt = ByteArray(`in`.available())
            while (`in`.read(tt, 0, tt.size) != -1) {
                resultJson = String(tt, Charset.forName("utf-8"))
            }
            return getPointLatLng(resultJson!!)
        } catch (e: UnsupportedEncodingException) {
            log.error("坐标转换异常", e)
        } catch (e: MalformedURLException) {
            log.error("坐标转换异常", e)
        } catch (e: IOException) {
            log.error("坐标转换异常", e)
        }

        return null
    }

    /**

     *
     * Title: geoconvPointLatLng
     *
     * Description:单坐标转换
     * @author 庄佳彬
     * *
     * @param p
     * *
     * @return PointLatLng
     */
    fun geoconvPointLatLng(p: GeoPoint): GeoPoint? {
        val list = ArrayList<GeoPoint>()
        list.add(p)
        val result = geoconvPointLatLng(list)
        if (result != null && result.isNotEmpty()) {
            return result.get(0)
        } else {
            return null
        }
    }

    /**

     *
     * Title: getFormattedAddress
     *
     * Description:解析返回地址
     * @author 庄佳彬
     * *
     * @param json
     * *
     * @return String
     */
    private fun getFormattedAddress(json: String): String {
        val map = JSONObject.parseObject(json)
        return (map["result"] as Map<*, *>)["formatted_address"].toString()
    }


    /**
     *
     * Title: getPointLatLng
     *
     * Description:解析返回坐标
     * @author 庄佳彬
     * *
     * @param json
     * *
     * @return PointLatLng
     */
    private fun getPointLatLng(json: String): List<GeoPoint> {
        val ps = ArrayList<GeoPoint>()
        val map = JSONObject.parseObject(json)
        if ("0" == map["status"].toString()) {
            val list = map["result"] as List<*>
            for (result in list) {
                result as Map<*, *>
                val p = GeoPoint()
                val lng = Value.of(result["x"], Value.DOUBLE_NULL)
                val lat = Value.of(result["x"], Value.DOUBLE_NULL)
                p.lan = lat!!
                p.lon = lng!!
                ps.add(p)
            }
        }
        return ps
    }

    /**
     * 测距，两点测距

     * @param p1 the p 1
     * *
     * @param p2 the p 2
     * *
     * @return the double
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-31 09:36:53
     */
    override fun distance(p1: GeoPoint, p2: GeoPoint): Double {
        val distances = distances(p1, p2)
        if (distances.isNotEmpty()) {
            return distances!![0]
        } else {
            return 0.0
        }
    }


    /**
     * 多点测距，两两测距

     * @param ps the ps
     * *
     * @return the list
     * *
     * @Auth Will
     * *
     * @Date 2016 -08-31 09:37:13
     */
    fun distances(vararg ps: GeoPoint): List<Double> {
        try {
            val sb = StringBuilder()
            val paramsMap = LinkedHashMap<String, String>()
            for (pointLatLng in ps) {
                sb.append(pointLatLng.lon).append(",").append(pointLatLng.lan)
                sb.append(";")
            }
            paramsMap.put("waypoints", sb.substring(0, sb.length - 1))
            paramsMap.put("output", "json")
            paramsMap.put("ak", AK!!)
            val queryStr = toQueryString(paramsMap)
            val wholeStr = "/telematics/v3/distance?" + queryStr + SK
            val tempStr: String = URLEncoder.encode(wholeStr, "UTF-8")
            val SN = Md5Secure.encode(tempStr)!!
            paramsMap.put("sn", SN)
            val url = URL("http://api.map.baidu.com/telematics/v3/distance?" + toQueryString(paramsMap))
            val connection = url.openConnection()

            val `in` = connection.getInputStream()
            var resultJson: String? = null
            val tt = ByteArray(`in`.available())
            while (`in`.read(tt, 0, tt.size) != -1) {
                resultJson = String(tt, Charset.forName("utf-8"))
            }
            return getDistances(resultJson!!)
        } catch (e: UnsupportedEncodingException) {
            log.error("计算两点距离异常", e)
        } catch (e: MalformedURLException) {
            log.error("计算两点距离异常", e)
        } catch (e: IOException) {
            log.error("计算两点距离异常", e)
        }

        return Collections.emptyList()
    }

    private fun getDistances(resultJson: String): List<Double> {
        val jo = JSON.parseObject(resultJson)
        //        log.error(jo.toString());
        val bds = jo["results"] as List<BigDecimal>
        val ds = bds.map { it.toDouble() }
        return ds
    }

    // 对Map内所有value作utf8编码，拼接返回结果   此方法由百度例子自带无需更改
    @Throws(UnsupportedEncodingException::class)
    private fun toQueryString(data: Map<*, *>): String {
        val queryString = StringBuilder()
        for ((key, value) in data) {
            queryString.append("$key=")
            queryString.append(
                URLEncoder.encode(
                    value as String,
                    "UTF-8"
                ) + "&"
            )
        }
        if (queryString.isNotEmpty()) {
            queryString.deleteCharAt(queryString.length - 1)
        }
        return queryString.toString()
    }
}