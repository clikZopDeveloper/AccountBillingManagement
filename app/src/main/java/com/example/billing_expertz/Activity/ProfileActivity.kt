package com.example.billing_expertz.Activity

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.*
import com.example.billing_expertz.databinding.ActivityProfileBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import java.io.File


class ProfileActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var apiClient: ApiController
    val CAMERA_PERMISSION_CODE1 = 123
    var SELECT_PICTURES1 = 1
    val PERMISSION_CODE = 12345
    var file2: File? = null
    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        if (SalesApp.isEnableScreenshort == true) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            );
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()
        binding.igToolbar.tvTitle.text = "My Profile"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivMenu.visibility = View.VISIBLE
        binding.igToolbar.tvWalletBal.visibility=View.GONE

      //  ApiContants.requestCameraPermission(this, PERMISSION_CODE)

      //  val profileData = intent.getSerializableExtra("profileResponse") as ProfileBean.Data
      //  Log.d("zxvcxc", Gson().toJson(profileData))

        setData()

        binding.apply {
            btnSubmit.setOnClickListener {

            }
            binding.ivAddImage.setOnClickListener {
                openCameraDialog(SELECT_PICTURES1, CAMERA_PERMISSION_CODE1)
            }
        }
    }

    fun openCameraDialog(SELECT_PICTURES: Int, CAMERA_PERMISSION_CODE: Int) {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dialog_camera, R.style.AppBottomSheetDialogTheme,
            this
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val llInternalPhoto = dialog.findViewById<View>(R.id.llInternalPhoto) as LinearLayout
        val llClickPhoto = dialog.findViewById<View>(R.id.llClickPhoto) as LinearLayout

        llInternalPhoto.setOnClickListener {
            dialog.dismiss()
            ApiContants.requestCameraPermission(this, PERMISSION_CODE)
            ApiContants.uploadImage(this, SELECT_PICTURES)
        }

        llClickPhoto.setOnClickListener {
            dialog.dismiss()
            ApiContants.requestCameraPermission(this, PERMISSION_CODE)
            ApiContants.ClickPicCamera(this, CAMERA_PERMISSION_CODE)

        }

        ivClose.setOnClickListener { dialog.dismiss() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURES1) {
            if (data?.getData() != null) {   // if single image is selected//Front

                var imageUri: Uri = data.data!!
                val picturePath: String = GeneralUtilities.getPath(
                    applicationContext, imageUri
                )
                file2 = File(picturePath)
                val myBitmap = BitmapFactory.decodeFile(file2!!.absolutePath)
                binding.ivImage.setImageBitmap(myBitmap)

                Log.d("SinglePicturePath", picturePath)
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PERMISSION_CODE1) {//Front
            try {
             //   Toast.makeText(this@ProfileActivity, "sdfsd", Toast.LENGTH_SHORT).show()

                val imageBitmap = data?.extras?.get("data") as Bitmap
                binding.ivImage.setImageBitmap(imageBitmap)
                val tempUri = GeneralUtilities.getImageUri(applicationContext, imageBitmap)
                file2 = File(GeneralUtilities.getRealPathFromURII(this, tempUri))
                //  imgList.add(file2!!)
                Log.e("Path", file2.toString())

                //Toast.makeText(getContext(), ""+picturePath, Toast.LENGTH_SHORT).show();
            } catch (e: java.lang.Exception) {
                Log.e("Path Error", e.toString())
                Toast.makeText(applicationContext, "" + e, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            /*        if (tag == ApiContants.getProfile) {
                        val profileBean = apiClient.getConvertIntoModel<ProfileBean>(
                            jsonElement.toString(),
                            ProfileBean::class.java
                        )
                        setData(profileBean.data)
                        Toast.makeText(this, profileBean.msg, Toast.LENGTH_SHORT).show()
                        finish()
                    }

        */
        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    private fun setData() {
        binding.apply {
         /*   binding.igToolbar.tvWalletBal.setText(
                PrefManager.getString(
                    ApiContants.walletBalance,
                    "0"
                )
            )*/
            edFullName.setText(PrefManager.getString(ApiContants.name,"0"))
            edMobNo.setText(PrefManager.getString(ApiContants.mobileNumber,"0"))
            edEmail.setText(PrefManager.getString(ApiContants.EmailAddress,"0"))
            edAddress.setText(PrefManager.getString(ApiContants.state,"0"))
            if (PrefManager.getString(ApiContants.status,"0").equals("1")){
                ivStatus.visibility=View.VISIBLE
            }else{
                ivStatus.visibility=View.GONE
            }
        //    edStatus.setText(PrefManager.getString(ApiContants.status,"0"))
        }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()

        Utility.showSnackBar(activity, errorMessage)
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
        ApiContants.isconnectedtonetwork = isconnected
        GeneralUtilities.internetConnectivityAction(this, isconnected)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    override fun onDestroy() {
        super.onDestroy()
        // Start the LocationService when the app is closed
        //   startService(Intent(this, LocationService::class.java))
    }
}
