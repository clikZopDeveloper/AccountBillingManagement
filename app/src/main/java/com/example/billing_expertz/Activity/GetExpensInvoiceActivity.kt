package com.example.billing_expertz.Activity

import android.app.*
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billing_expertz.Adapter.GetCustomerAdapter
import com.example.billing_expertz.Adapter.GetVendorAdapter
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.Model.*
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.*
import com.example.billing_expertz.databinding.ActivityExpensInvoiceBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants


class GetExpensInvoiceActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityExpensInvoiceBinding
    private lateinit var apiClient: ApiController

    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expens_invoice)
        if (SalesApp.isEnableScreenshort==true){
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()

        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivMenu.visibility=View.VISIBLE
        binding.igToolbar.tvWalletBal.visibility=View.GONE
        apiClient = ApiController(activity, this)
        if (intent.getStringExtra("way").equals("Customer")){
            binding.igToolbar.tvTitle.text = "Customer"
            getCustomerApi()
        }else{
            binding.igToolbar.tvTitle.text = "Vendor"
            apiVendorLabour()
        }
    }

    fun getCustomerApi() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getCustomer, params)
    }

    fun apiVendorLabour() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getVendorLabour, params)
    }


    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()

            if (tag == ApiContants.getCustomer) {
                val customerBean = apiClient.getConvertIntoModel<CustomerBean>(
                    jsonElement.toString(),
                    CustomerBean::class.java
                )
                if (customerBean.error == false) {
                    Log.d("asdasd",Gson().toJson(customerBean.data))
                    handleCustomerList(customerBean.data)
                }
            }
            if (tag == ApiContants.getVendorLabour) {
                val vendorLabourBean = apiClient.getConvertIntoModel<VendorLabourBean>(
                    jsonElement.toString(),
                    VendorLabourBean::class.java
                )
                if (vendorLabourBean.error == false) {
                    //  catList = categoryBean.data
                    Log.d("asdasd",Gson().toJson(vendorLabourBean.data))
                    handleVendorList(vendorLabourBean.data)
                }
            }


        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }

    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(activity, errorMessage)
        Log.d("error", errorMessage)

    }

    fun handleCustomerList(data: List<CustomerBean.Data>) {
        binding.rcExpenses.layoutManager =
            LinearLayoutManager(this)
        val mAllAdapter = GetCustomerAdapter(this, data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcExpenses.adapter = mAllAdapter
        mAllAdapter.notifyDataSetChanged()
        // rvMyAcFiled.isNestedScrollingEnabled = false

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (data != null) {
                    mAllAdapter.filter.filter(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                mAllAdapter.filter.filter(s)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAllAdapter.filter.filter(s)
                /* if (s.toString().trim { it <= ' ' }.length < 1) {
                     ivClear.visibility = View.GONE
                 } else {
                     ivClear.visibility = View.GONE
                 }*/
            }
        })
    }

    fun handleVendorList(data: List<VendorLabourBean.Data>) {
        binding.rcExpenses.layoutManager =
            LinearLayoutManager(this)
        val mAllAdapter = GetVendorAdapter(this, data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {

            }
        })
        binding.rcExpenses.adapter = mAllAdapter
        mAllAdapter.notifyDataSetChanged()
        // rvMyAcFiled.isNestedScrollingEnabled = false

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (data != null) {
                    mAllAdapter.filter.filter(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                mAllAdapter.filter.filter(s)
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAllAdapter.filter.filter(s)
                /* if (s.toString().trim { it <= ' ' }.length < 1) {
                     ivClear.visibility = View.GONE
                 } else {
                     ivClear.visibility = View.GONE
                 }*/
            }
        })
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
