package com.will.utils

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Created by zoumy on 2017/5/15 17:16.
 */
object DateTimeUtils {
    /**
     * The constant DATE_FORMAT_YMD.
     */
    val DATE_FORMAT_YMD = "yyyy-MM-dd"
    /**
     * The constant DATE_FORMAT_LX_YMD.
     */
    val DATE_FORMAT_LX_YMD = "yyyyMMdd"
    /**
     * The constant DATE_FORMAT_YMDH.
     */
    val DATE_FORMAT_YMDH = "yyyy-MM-dd HH"
    /**
     * The constant DATE_FORMAT_YMDHM.
     */
    val DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm"
    /**
     * The constant DATE_FORMAT_YMDHMS.
     */
    val DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss"
    /**
     * The constant DATE_FORMAT_LX_YMDHMS.
     */
    val DATE_FORMAT_LX_YMDHMS = "yyyyMMddHHmmss"
    /**
     * The constant DATE_FORMAT_LX_YMDHM.
     */
    val DATE_FORMAT_LX_YMDHM = "yyyyMMddHHmm"
    /**
     * The constant DATE_FORMAT_LX_HMS.
     */
    val DATE_FORMAT_LX_HMS = "HHmmss"
    /**
     * The constant DATE_FORMAT_LX_HMS.
     */
    val DATE_FORMAT_HMS = "HH:mm:ss"

    /**
     * 格式化日期默认当天 yyyyMMdd HH:mm:ss
     */
    fun formatDate(date: Date = Date(), fmt: String = DATE_FORMAT_YMDHMS): String {
        val localDateTime = date2LocalDateTime(date)
        return localDateTime.format(DateTimeFormatter.ofPattern(fmt))
    }

    /**
     * 字符串日期转Date 默认格式yyyyMMdd
     */
    fun getDateByStringFmt(timeStr: String, fmt: String = DATE_FORMAT_YMD): Date {
        val ldt = LocalDate.parse(timeStr, DateTimeFormatter.ofPattern(fmt))
        return localDate2Date(ldt)
    }

    /**
     * 字符串日期时间转Date 默认格式yyyyMMdd HH:mm:ss
     */
    fun getDateTimeByStringFmt(timeStr: String, fmt: String = DATE_FORMAT_YMDHMS): Date {
        val ldt = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(fmt))
        return localDateTime2Date(ldt)
    }

    /**
     * 当前日期
     */
    fun currDate(): Date {
        return Date()
    }

    /**
     * 当前年
     */
    fun getYear(date: Date = Date()): Int {
        val ld = date2LocalDate(date)
        return ld.year
    }

    /**
     * 当前月
     */
    fun getMonth(date: Date = Date()): Int {
        val ld = date2LocalDate(date)
        return ld.monthValue
    }

    /**
     * 当前日
     */
    fun getDay(date: Date = Date()): Int {
        val ld = date2LocalDate(date)
        return ld.dayOfMonth
    }

    /**
     * 一周中第几天
     */
    fun getDayOfWeek(date: Date = Date()): Int {
        val ld = date2LocalDate(date)
        return ld.dayOfWeek.value
    }

    /**
     * 一年中第几周
     */
    fun getWeekOfYear(date: Date = Date()): Int {
        val ld = date2LocalDate(date)
        return ld.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    }

    /**
     * 一个月多少天
     */
    fun countDayOfMonth(date: Date = Date()): Int {
        val ld = date2LocalDate(date)
        val leapYear = ld.isLeapYear
        return ld.month.length(leapYear)
    }

    /**
     * 一个月剩余天数
     */
    fun countRemainDayOfMonth(date: Date = Date()): Int {
        return countDayOfMonth(date) - getDay(date)
    }

    /**
     * 一年中第几天
     */
    fun getDayOfYear(date: Date = Date()): Int {
        val ld = date2LocalDate(date)
        return ld.dayOfYear
    }

    /**
     * 获取月的第一天
     */
    fun getFirstDateOfMonth(date: Date = Date()):Date{
        val ld = LocalDate.of(getYear(date), getMonth(date),1)
        return localDate2Date(ld)
    }

    /**
     * 获取月的最后一天
     */
    fun  getLastDateOfMonth(date: Date = Date()):Date{
        val ld = LocalDate.of(getYear(date), getMonth(date), countDayOfMonth(date))
        return localDate2Date(ld)
    }

    /**
     * 季度第一天
     */
    fun getFirstDateOfQuarter(date: Date = Date()):Date{
        return getQuarterDate(date)[0]
    }

    /**
     * 季度最后一天
     */
    fun getLastDateOfQuarter(date:Date = Date()):Date{
        return getLastDateOfMonth(getQuarterDate(date)[2])
    }

    /**
     * 季度多少天
     */
    fun countDayOfQuarter(date: Date = Date()):Int{
        val day = getQuarterDate(date).sumBy { countDayOfMonth(it) }
        return day
    }

    /**
     * 季度中第几天
     */
    fun getDayOfQuarter(date: Date = Date()):Int{
        val today = date2LocalDate(date)
        val first = date2LocalDate(getFirstDateOfQuarter(date))
        return first.until(today,ChronoUnit.DAYS).toInt()
    }

    /**
     * 季度中剩余天数
     */
    fun countRemainDayOfQuarter(date: Date = Date()):Int{
        return countDayOfQuarter(date) - getDayOfQuarter(date)
    }

    /**
     * 第几个季度
     */
    fun getQuarter(date: Date = Date()):Int{
        val month = getMonth(date)
        var quarter = 0
        when (month) {
            1, 2, 3-> quarter = 1
            4,5,6 -> quarter = 2
            7,8,9 -> quarter = 3
            10,11,12 -> quarter = 4
            else -> {
            }
        }
        return quarter
    }

    /**
     * 获取季度Date集合
     */
    fun getQuarterDate(date: Date = Date()):Array<Date>{
        var ld = date2LocalDate(date)
        val month = ld.month.firstMonthOfQuarter().value
        val first = localDate2Date(LocalDate.of(getYear(date),month,1))
        val second = localDate2Date(LocalDate.of(getYear(date),month + 1,1))
        val third = localDate2Date(LocalDate.of(getYear(date),month + 2,1))
        val dateArray = arrayOf(first,second,third)
        return dateArray
    }

    /**
     * 持续时间
     */
    fun getDuration(begin:Date,end:Date ):Duration{
        val bldt = date2LocalDateTime(begin)
        val eldt = date2LocalDateTime(end)
        return Duration.between(bldt,eldt)
    }
    fun date2LocalDateTime(date: Date, zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime {
        val instant = date.toInstant()
        return LocalDateTime.ofInstant(instant, zoneId)
    }

    fun localDateTime2Date(ldt: LocalDateTime, zoneId: ZoneId = ZoneId.systemDefault()): Date {
        val instant = ldt.atZone(zoneId).toInstant()
        return Date.from(instant)
    }

    fun date2LocalDate(date: Date, zoneId: ZoneId = ZoneId.systemDefault()): LocalDate {
        val instant = date.toInstant()
        return LocalDateTime.ofInstant(instant, zoneId).toLocalDate()
    }

    fun localDate2Date(ld: LocalDate, zoneId: ZoneId = ZoneId.systemDefault()): Date {
        val instant = ld.atStartOfDay().atZone(zoneId).toInstant()
        return Date.from(instant)
    }
}
