package com.example.billing_expertz.Activity

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.Model.SettingsBean
import com.example.billing_expertz.Utills.*

import com.example.billing_expertz.databinding.ActivitySplashBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import com.stpl.antimatter.Utils.ApiContants.Companion.isconnectedtonetwork


class SplashActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener, ApiResponseListner {
    private lateinit var binding: ActivitySplashBinding
    var refresher: CountDownTimer? = null
    private var refreshRate = 300000L //5min
    private var refresherDuration = 780000L //12min
    var myReceiver: ConnectivityListener? = null
    private lateinit var apiClient: ApiController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_splash)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        myReceiver = ConnectivityListener()
        apiClient = ApiController(this, this)
        val animation4 = AnimationUtils.loadAnimation(this, com.example.billing_expertz.R.anim.zoom)
        //GeneralUtilities.getInstance().setStatusBarColor(SplashActivity.this, ContextCompat.getColor(context, R.color.colorPrimaryDark));
     //   binding.image.startAnimation(animation4)
     //   initNotificationRefresher()
        allGetApi()

        /*Handler(Looper.getMainLooper()).postDelayed({
            // callNextActivity()
            if (PrefManager.getString(ApiContants.AccessToken, "") != "") {
                GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                finishAffinity()
            } else {
                GeneralUtilities.launchActivity(this, LoginActivity::class.java)
                finishAffinity()
            }

        }, 2200)*/

    }
    fun allGetApi() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getSettings, params)
    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()

            if (tag == ApiContants.getSettings) {
                val settingsBean = apiClient.getConvertIntoModel<SettingsBean>(
                    jsonElement.toString(),
                    SettingsBean::class.java
                )
                if (settingsBean.error == false) {

                    Log.d("asdasd", Gson().toJson(settingsBean.data))

                    PrefManager.putString("CompanyLogo", settingsBean.data.logo)

                    Glide.with(this).load(settingsBean.data.logo).into(binding.imagea)

                    Handler(Looper.getMainLooper()).postDelayed({
                        // callNextActivity()
                        if (PrefManager.getString(ApiContants.AccessToken, "") != "") {
                            GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                            finishAffinity()
                        } else {
                            GeneralUtilities.launchActivity(this, LoginActivity::class.java)
                            finishAffinity()
                        }

                    }, 2200)
                }
            }


        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }

    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(this, errorMessage)
        Log.d("error", errorMessage)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        GeneralUtilities.unregisterBroadCastReceiver(this, myReceiver)
    }

    override fun onResume() {
        GeneralUtilities.registerBroadCastReceiver(this, myReceiver)
        SalesApp.setConnectivityListener(this)
        super.onResume()
    }

    override fun onNetworkConnectionChange(isconnected: Boolean) {
        isconnectedtonetwork = isconnected
        GeneralUtilities.internetConnectivityAction(this, isconnected)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    override fun onDestroy() {
        super.onDestroy()
        // Start the LocationService when the app is closed
      //  startService(Intent(this, LocationService::class.java))
    }
    fun initNotificationRefresher(){
        refresher = object : CountDownTimer(refresherDuration, refreshRate) {
            override fun onTick(millisUntilFinished: Long) {
               Toast.makeText(this@SplashActivity,"Call Code",Toast.LENGTH_SHORT).show()
            }
            override fun onFinish() {
                initNotificationRefresher()
            }
        }.start()
    }
}