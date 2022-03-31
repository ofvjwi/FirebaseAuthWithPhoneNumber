package com.example.firebaselesson1extra.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast


// Perfect Sms Receiver
class SmsBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private const val SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED"
        private const val PDU: String = "pdus"  // PROTOCOL DATA UNIT --> pdu
        private const val TAG: String = "SmsReceiverOld"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent != null) {
            if (intent.action === SMS_RECEIVED) {
                val bundle: Bundle? = intent.extras

                if (bundle != null) {
                    val protocolDataUnits: Array<*>? = bundle[PDU] as Array<*>?

                    if (protocolDataUnits != null) {
                        val messages: Array<SmsMessage?> = arrayOfNulls(protocolDataUnits.size)
                        for (i in protocolDataUnits.indices) {
                            messages[i] =
                                SmsMessage.createFromPdu(protocolDataUnits[i] as ByteArray)
                        }
                        if (messages.size > -1) {

                            Toast.makeText(
                                context,
                                "Messages -> \n ${messages.toList()}",
                                Toast.LENGTH_SHORT
                            ).show()

                            for (i in messages.indices) {
                                Log.d(TAG, "displayMessageBody: ${messages[i]?.displayMessageBody}")
                                Log.d(
                                    TAG,
                                    "displayOriginatingAddress: ${messages[i]?.displayOriginatingAddress}"
                                )
                                Log.d(TAG, "emailBody: ${messages[i]?.emailBody}")
                                Log.d(TAG, "emailFrom: ${messages[i]?.emailFrom}")
                                Log.d(TAG, "isEmail: ${messages[i]?.isEmail}")
                                Log.d(TAG, "indexOnIcc: ${messages[i]?.indexOnIcc}")
                                Log.d(TAG, "indexOnSim: ${messages[i]?.indexOnSim}")
                                Log.d(TAG, "isMWIClearMessage: ${messages[i]?.isMWIClearMessage}")
                                Log.d(TAG, "isMWISetMessage: ${messages[i]?.isMWISetMessage}")
                                Log.d(TAG, "isMwiDontStore: ${messages[i]?.isMwiDontStore}")
                                Log.d(TAG, "isCphsMwiMessage: ${messages[i]?.isCphsMwiMessage}")
                                Log.d(
                                    TAG,
                                    "isStatusReportMessage: ${messages[i]?.isStatusReportMessage}"
                                )
                                Log.d(TAG, "timestampMillis: ${messages[i]?.timestampMillis}")
                                Log.d(TAG, "isReplace: ${messages[i]?.isReplace}")
                                Log.d(TAG, "isReplyPathPresent: ${messages[i]?.isReplyPathPresent}")
                                Log.d(TAG, "userData: ${messages[i]?.userData.contentToString()}")
                                Log.d(TAG, "originatingAddress: ${messages[i]?.originatingAddress}")
                                Log.d(TAG, "pdu: ${messages[i]?.pdu.contentToString()}")
                                Log.d(TAG, "messageBody: ${messages[i]?.messageBody}")
                                Log.d(TAG, "messageClass: ${messages[i]?.messageClass}")
                                Log.d(
                                    TAG,
                                    "pseudoSubject: ${messages[i]?.pseudoSubject.toString()}"
                                )
                                Log.d(
                                    TAG,
                                    "serviceCenterAddress: ${messages[i]?.serviceCenterAddress}"
                                )
                                Log.d(TAG, "statusOnSim: ${messages[i]?.statusOnSim}")
                                Log.d(TAG, "statusOnIcc: ${messages[i]?.statusOnIcc}")
                                Log.d(TAG, "status: ${messages[i]?.status}")
                                Log.d(TAG, "protocolIdentifier: ${messages[i]?.protocolIdentifier}")
                            }
                        } else {
                            Toast.makeText(context, "messages is empty", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "PDU list is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Bundle is null", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Intent action is not sms", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Intent is null", Toast.LENGTH_SHORT).show()
        }
    }
}

