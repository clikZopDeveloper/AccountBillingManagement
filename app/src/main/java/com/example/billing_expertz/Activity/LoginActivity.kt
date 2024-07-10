package com.example.billing_expertz.Activity

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.Model.LoginBean
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.GeneralUtilities
import com.example.billing_expertz.Utills.PrefManager
import com.example.billing_expertz.Utills.Utility
import com.example.billing_expertz.databinding.ActivityLoginBinding

import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class LoginActivity : AppCompatActivity(), ApiResponseListner {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiClient: ApiController
    var activity: Activity = this

    private var isPermissionGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        Glide.with(this).load(PrefManager.getString("CompanyLogo","")).into(binding.cmpnyLogo)

        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        if (TextUtils.isEmpty(binding.editMobNo.text.toString())) {
            Utility.showSnackBar(this, "Please enter username")
        } /*else if (binding.editMobNo.text.toString().length < 10) {
            Utility.showSnackBar(this, "Please enter valid mobile number")
        }*/ else if (TextUtils.isEmpty(binding.editPassword.text.toString())) {
            Utility.showSnackBar(this, "Please enter password")
        } else {
            //    loginViewModel.apiCallLogin()
            apiCallLogin()
        }
    }

    fun apiCallLogin() {
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["username"] = binding.editMobNo.text.toString()
        params["password"] = binding.editPassword.text.toString()

        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.login, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.login) {
                val loginModel = apiClient.getConvertIntoModel<LoginBean>(
                    jsonElement.toString(),
                    LoginBean::class.java
                )
                if (loginModel.error == false) {
                    PrefManager.putString(ApiContants.AccessToken, loginModel.data.token)
                    PrefManager.putString(
                        ApiContants.mobileNumber,
                        loginModel.data.mobile.toString()
                    )
                    PrefManager.putString(
                        ApiContants.password,
                        binding.editPassword.text.toString()
                    )
                /*    PrefManager.putString(
                        ApiContants.walletBalance,loginModel.data.wa.toString()
                    )*/
                    PrefManager.putString(
                        ApiContants.name,
                        loginModel.data.name.toString()
                    )
                    PrefManager.putString(
                        ApiContants.EmailAddress,
                        loginModel.data.email.toString()
                    )
                    PrefManager.putString(
                        ApiContants.state,
                        loginModel.data.state.toString()
                    )
                    PrefManager.putString(
                        ApiContants.status,
                        loginModel.data.status.toString()
                    )
                    Toast.makeText(activity, loginModel.msg, Toast.LENGTH_SHORT).show()
                    GeneralUtilities.launchActivity(this, DashboardActivity::class.java)
                    finishAffinity()
                }

            }
        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }

    }

    override fun failure(tag: String?, errorMessage: String) {

        apiClient.progressView.hideLoader()

        Utility.showSnackBar(activity, errorMessage)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Start the LocationService when the app is closed
        //   startService(Intent(this, LocationService::class.java))
    }

    override fun onResume() {
        super.onResume()
    }

}
