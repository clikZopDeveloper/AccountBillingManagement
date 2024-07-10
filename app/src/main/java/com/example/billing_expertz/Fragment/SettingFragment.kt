package com.example.billing_expertz.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.billing_expertz.Activity.DashboardActivity
import com.example.billing_expertz.Activity.LoginActivity
import com.example.billing_expertz.Activity.ProfileActivity
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.Model.BaseResponseBean
import com.example.billing_expertz.Model.PasswordChangeBean
import com.example.billing_expertz.Model.ProfileBean
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.GeneralUtilities
import com.example.billing_expertz.Utills.PrefManager
import com.example.billing_expertz.Utills.SalesApp
import com.example.billing_expertz.Utills.Utility

import com.example.billing_expertz.databinding.FragmentSettingBinding
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants


class SettingFragment : Fragment(), ApiResponseListner {
    private lateinit var apiClient: ApiController
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val titleText = (activity as DashboardActivity?)
        titleText?.setTitle("Dashboard")
        setData()
       // apiProfileData()
        binding.apply {

            llChangePass.setOnClickListener {
           //  startActivity(Intent(requireContext(),PasswordChnageActivity::class.java))
                openPassChangeDialog()
            }

            llLogout.setOnClickListener {
                dialogLogout()
            }

        }

        return root
    }

    fun apiProfileData() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        //     params["password"] = binding.editNewpass.text.toString()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getProfile, params)

    }

    fun dialogLogout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to logout app?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database

                apiCallLogout()
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }

    fun apiCallLogout() {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(requireContext(), this)
        val params = Utility.getParmMap()
        params["mobile"] = PrefManager.getString(ApiContants.mobileNumber, "")
        params["password"] = PrefManager.getString(ApiContants.password, "")
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.logout, params)
    }

    fun apiChangePassword(editNewpass: String) {
        SalesApp.isAddAccessToken = true
        apiClient = ApiController(activity, this)
        val params = Utility.getParmMap()
        params["password"] = editNewpass
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getPasswordChange, params)

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.logout) {
                val baseResponseBean = apiClient.getConvertIntoModel<BaseResponseBean>(
                    jsonElement.toString(),
                    BaseResponseBean::class.java
                )
                Toast.makeText(activity, baseResponseBean.msg, Toast.LENGTH_SHORT).show()
                PrefManager.clear()
                GeneralUtilities.launchActivity(
                    requireContext() as AppCompatActivity?,
                    LoginActivity::class.java
                )
                requireActivity().finishAffinity()
            }
            if (tag == ApiContants.getPasswordChange) {
                val updateLeadBean = apiClient.getConvertIntoModel<PasswordChangeBean>(
                    jsonElement.toString(),
                    PasswordChangeBean::class.java
                )

                Toast.makeText(requireContext(), updateLeadBean.msg, Toast.LENGTH_SHORT).show()

            }
            if (tag == ApiContants.getProfile) {
                val profileBean = apiClient.getConvertIntoModel<ProfileBean>(
                    jsonElement.toString(),
                    ProfileBean::class.java
                )

                Toast.makeText(requireContext(), profileBean.msg, Toast.LENGTH_SHORT).show()

            }


        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    private fun setData() {
    binding.apply {
    tvName.setText(PrefManager.getString(ApiContants.name,"0"))
    tvMobNo.setText(PrefManager.getString(ApiContants.mobileNumber,"0"))
    tvEmail.setText(PrefManager.getString(ApiContants.EmailAddress,"0"))

    llProfile.setOnClickListener {
        startActivity(Intent(requireContext(),ProfileActivity::class.java))
    }
    rlProfile.setOnClickListener {
        startActivity(Intent(requireContext(),ProfileActivity::class.java))
    }
  }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(requireActivity(), errorMessage)
    }

    fun openPassChangeDialog() {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dialog_pass_change, R.style.AppBottomSheetDialogTheme,
            requireActivity()
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val edPassChange = dialog.findViewById<View>(R.id.edPassChange) as EditText
        val btnSubmit = dialog.findViewById<View>(R.id.btnSubmit) as TextView
        btnSubmit.setOnClickListener {
            apiChangePassword(edPassChange.text.toString()
            )
            dialog.dismiss()
        }


        ivClose.setOnClickListener { dialog.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}