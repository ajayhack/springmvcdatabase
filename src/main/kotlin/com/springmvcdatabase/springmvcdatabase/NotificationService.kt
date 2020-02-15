package com.springmvcdatabase.springmvcdatabase
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


object AndroidPushNotificationsService {
    private const val FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send"
    private const val FIREBASE_SERVER_KEY = "AAAAoqu_Vus:APA91bFEbm-eBO2UfnpXHFCg_QGRfEDcQgkw2Ba_9Wz3NZBVmw-XEdSakVD9didLySnWDxtYtjmvma36XUHRdAfG4raGiIeCCWaROT54s4WJYshgTXaBm_qPqEEvhajv8YRglluFnpIe"
    @Throws(IOException::class)
    fun sendPushNotification(deviceToken: String, Message: String?, Message1: String?){
        var result = ""
        val url = URL(FIREBASE_API_URL)
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.useCaches = false
        conn.doInput = true
        conn.doOutput = true
        conn.requestMethod = "POST"
        conn.setRequestProperty("content-type", "application/json")
        conn.setRequestProperty("Authorization", "key=$FIREBASE_SERVER_KEY")
        conn.connect()

        val info = HashMap<String , String>()
        val infoNotification = HashMap<String , String>()
        infoNotification["\"title\""] = " \"$Message\" "
        infoNotification["\"body\""] = " \"$Message1\" "
        infoNotification["\"message\""] = "\"Notes App FCM Test Push\" "
        info["\"notification\""] = "$infoNotification"
        info["\"to\""] = deviceToken.trim()

        try {
            val wr = OutputStreamWriter(conn.outputStream)
            wr.write(info.toString().replace("=" , ":"))
            wr.flush()
            wr.close()
            println("Response:- ${info.toString()}")
            val br = BufferedReader(InputStreamReader(conn.inputStream))
            var output: String? = null
            println("Output from Server .... \n")
            while (br.readLine().also { output = it } != null) {
                println(output)
            }
            result = "success"
        } catch (e: Exception) {
            e.printStackTrace()
            result = "failure"
        }
    }
}

