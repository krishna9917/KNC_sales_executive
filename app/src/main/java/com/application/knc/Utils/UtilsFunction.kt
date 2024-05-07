package com.application.knc.Utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import com.application.knc.R
import java.util.Locale


class UtilsFunction {

    companion object {

        fun isInternetConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // For devices running Android Q and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val capabilities =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
            // For devices running below Android Q
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }


        fun showError(context: Context, message: String) {
            showToast(context, message)
            vibration(context)
        }

        fun vibration(context: Context)
        {
            var vibrator: Vibrator
            var vibratorManager: VibratorManager
            if (Build.VERSION.SDK_INT >= 31) {
                vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibrator = vibratorManager.defaultVibrator
            } else {
                vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                //deprecated in API 26
                vibrator.vibrate(500);
            }
        }


        fun contactDialer(contact: String) {
            val dialPhone = Intent(Intent.ACTION_DIAL)
            dialPhone.data = Uri.parse("tel:" + contact)
            dialPhone.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            MyApp.MySingleton.getAppContext().startActivity(dialPhone)
        }


        fun shareText(text: String, context: Context) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(shareIntent, "Share with:"))
        }


        fun openMapByAddress(address:String,context: Context)
        {
            try {
                val map = "http://maps.google.co.in/maps?q=${address}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
                context.startActivity(intent)
            }catch (ex: ActivityNotFoundException)
            {
                showToast(MyApp.MySingleton.getAppContext(),
                    context.getString(R.string.there_are_no_map_client_installed_on_your_device))
            }
        }


    }

}