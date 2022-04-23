package com.hbtknet.simo_v_7_0_1

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {

    val REQUEST_CODE = 1;
    private val methodChannelName = "channelForPayement";
    private lateinit var channel: MethodChannel

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, methodChannelName)
        channel.setMethodCallHandler { call, result ->
            val arguement = call.arguments as HashMap<*, *>
            val message = arguement["msg"]
            val prefId = arguement["prefernceId"] as String
            val pubKey = arguement["pubkey"] as String
//            val channelMercadoPagoRespuesta = MethodChannel(flutterEngine.dartExecutor?.binaryMessenger,"mercadoPagoResponse")
//                   channelMercadoPagoRespuesta.invokeMethod("kol","hajag")

            if (call.method == "showToast") {
                // Toast.makeText(this,message,Toast.LENGTH_LONG).show()
//               MercadoPago('YOUR_PUBLIC_KEY', {
//                        locale: 'en-US', })
                MercadoPagoCheckout.Builder(pubKey, prefId).build()
                    .startPayment(this@MainActivity, REQUEST_CODE);


            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
                   val channelMercadoPagoRespuesta = MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger,"mercadoPagoResponse")




            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {

                val payment = data!!.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment
               val payementStatus = payment.paymentStatus
                val paymentStatusdetails=payment.paymentStatusDetail
                val paymentid =payment.id

                val arrayList= arrayListOf<String>()
                arrayList.add(paymentid.toString())
                arrayList.add(payementStatus)
                arrayList.add(paymentStatusdetails)
                channelMercadoPagoRespuesta.invokeMethod("kol",arrayList)
            }

        else if (resultCode == RESULT_CANCELED) {

            val arrayList= arrayListOf<String>()
            arrayList.add("PayementError")
            channelMercadoPagoRespuesta.invokeMethod("paymenterror",arrayList)
        }
        else{
            val arrayList= arrayListOf<String>()
            arrayList.add("Payementcanceled")
            channelMercadoPagoRespuesta.invokeMethod("paymenterror",arrayList)
        }

        }




        }









//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE) {
////
//            val channelMercadoPagoRespuesta = MethodChannel(flutterEngine?.dartExecutor?.binaryMessenger,"mercadoPagoResponse")
//            channelMercadoPagoRespuesta.invokeMethod("mercadoPagoResponse","12345678")
//
//
//            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
////                val payment =
////                    data?.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment
////                (findViewById<View>(R.id.mp_results) as TextView).setText("Resultado del pago: " + payment.getStatus())
////                //Done!
//            } else if (resultCode == RESULT_CANCELED) {
//                if (data != null && data.getExtras() != null && data.getExtras()!!
//                        .containsKey(MercadoPagoCheckout.EXTRA_ERROR)
//                ) {
////                    val mercadoPagoError =
////                        data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR) as MercadoPagoError
////                    (findViewById<View>(R.id.mp_results) as TextView).setText("Error: " + mercadoPagoError.message)
//                    //Resolve error in checkout
//                } else
//                    //Resolve canceled checkout
//                }
//            }
//        }
//    }


