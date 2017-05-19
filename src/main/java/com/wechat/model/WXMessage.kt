package com.wechat.model



/**
 * Created by zoumy on 2017/5/12 9:36.
 * 微信消息类
 */
class WXMessage(){


    /**
     * The type MessageType.

     * @Auth Will
     * *
     * @Date 2017 -04-10 14:18:21
     */
    object MessageType {
        /**
         * The constant TEXT.
         */
        var TEXT = "text"
        /**
         * The constant IMAGE.
         */
        var IMAGE = "image"
        /**
         * The constant VOICE.
         */
        var VOICE = "voice"
        /**
         * The constant VIDEO.
         */
        var VIDEO = "video"
        /**
         * The constant SHORT_VIDEO.
         */
        var SHORT_VIDEO = "shortvideo"
        /**
         * The constant LOCATION.
         */
        var LOCATION = "location"
        /**
         * The constant LINK.
         */
        var LINK = "link"
        /**
         * The constant EVENT.
         */
        var EVENT = "event"
    }

    /**
     * The type EventType.

     * @Auth Will
     * *
     * @Date 2017 -04-10 14:18:22
     */
    object EventType {
        /**
         * The constant SUBSCRIBE.
         */
        var SUBSCRIBE = "subscribe"
        /**
         * The constant UNSUBSCRIBE.
         */
        var UNSUBSCRIBE = "unsubscribe"
        /**
         * The constant SCAN.
         */
        var SCAN = "SCAN"
        /**
         * The constant LOCATION.
         */
        var LOCATION = "LOCATION"
        /**
         * The constant CLICK.
         */
        var CLICK = "CLICK"
        /**
         * The constant VIEW.
         */
        var VIEW = "VIEW"
    }

    var ToUserName: String? = null
    var FromUserName: String? = null
    var CreateTime: Long = 0
    var MsgType: String? = null
    var Content: String? = null
    var PicUrl: String? = null
    var MediaId: String? = null

    constructor(ToUserName:String,FromUserName:String,CreateTime:Long,MsgType:String,Content:String):this(){
        this.ToUserName = ToUserName
        this.FromUserName = FromUserName
        this.CreateTime = CreateTime
        this.MsgType = MsgType
        this.Content = Content
    }

    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("<xml><ToUserName>$ToUserName</ToUserName><FromUserName>$FromUserName</FromUserName><CreateTime>$CreateTime</CreateTime><MsgType>$MsgType</MsgType>")
        if (Content != null) {
            sb.append("<Content>$Content</Content>")
        }
        if (MediaId != null) {
            sb.append("<MediaId>$MediaId</MediaId>")
        }
        if (PicUrl != null) {
            sb.append("<PicUrl>$PicUrl</PicUrl>")
        }
        sb.append("</xml>")

        return sb.toString()
    }
}
