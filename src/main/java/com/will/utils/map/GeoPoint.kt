package com.will.utils.map

/**
 * Created by zoumy on 2017/5/11 15:29.
 */
class GeoPoint() {

    var lan: Double = 0.toDouble()
    var lon: Double = 0.toDouble()
    var direct: Int = 0

    val FROM_GEO = "84"
    val TO_GEO = "bd"

    constructor(point: GeoPoint):this(){
        this.lan = point.lan
        this.lon = point.lon
        this.direct = point.direct
    }

    constructor(lan:Double,lon:Double):this(){
        this.lon = lon
        this.lan = lan
    }

    constructor(lan: Double,lon: Double,direct:Int):this(lan, lon){
        this.direct = direct
    }

}