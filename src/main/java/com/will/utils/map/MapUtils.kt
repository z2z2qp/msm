package com.will.utils.map

/**
 * Created by zoumy on 2017/5/11 15:36.
 */
abstract class MapUtils {


    /**
     * 地球半径单位m
     */
    protected val EARTH_RADIUS = 6370856.0

    /**

     *
     * Title: rad
     *
     * Description:度转弧度
     * @author 庄佳彬
     * *
     * @Date 2016年5月30日
     * *
     * @param d
     * *
     * @return double
     */
    protected fun rad(d: Double): Double {
        return d * Math.PI / 180.0
    }

    /**

     *
     * Title: distance
     *
     * Description:两点距离
     * @author 庄佳彬
     * *
     * @Date 2016年5月30日
     * *
     * @param p1
     * *
     * @param p2
     * *
     * @return double
     */
    open fun distance(p1: GeoPoint, p2: GeoPoint): Double {
        val radLat1 = rad(p1.lan)
        val radLat2 = rad(p2.lan)
        val a = radLat1 - radLat2
        val b = rad(p1.lon) - rad(p2.lon)
        var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2.0) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2.0)))
        s *= EARTH_RADIUS
        return s
    }

    /**

     *
     * Title: location2Adress
     *
     * Description:坐标转地址
     * @author 庄佳彬
     * *
     * @Date 2016年5月30日
     * *
     * @param pointLatLng
     * *
     * @return String
     */
    abstract fun location2Adress(pointLatLng: GeoPoint): String?
}