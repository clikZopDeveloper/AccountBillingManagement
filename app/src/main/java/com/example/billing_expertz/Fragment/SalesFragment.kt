package com.example.billing_expertz.Fragment

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billing_expertz.Activity.*
import com.example.billing_expertz.Adapter.GetSaleAdapter
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.Model.*
import com.example.billing_expertz.Utills.*
import com.example.billing_expertz.databinding.FragmentSalesBinding
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants

class SalesFragment : Fragment(), ApiResponseListner {

    private lateinit var apiClient: ApiController
    private var _binding: FragmentSalesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = com.example.billing_expertz.databinding.FragmentSalesBinding.inflate(
            inflater,
            container,
            false
        )

        val root: View = binding.root

        val titleText = (activity as DashboardActivity?)
        titleText?.setTitle("All Invoice")

        binding.fbAddArchitect.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    AddInvoiceActivity::class.java
                ).putExtra("way", "Add Sales")
            )
        }

        apiClient = ApiController(activity, this)
        //    ApiContants.movabalebutton(binding.fbAddArchitect,requireActivity())
        allGetApi()

        return root
    }

    fun apiGetSales(customerID: String) {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        params["customer_id"] = customerID
        apiClient.getApiPostCall(ApiContants.getSale, params)
    }

    fun allGetApi() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getCustomer, params)
    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.getSale) {
                val getSalesBean = apiClient.getConvertIntoModel<GetSalesBean>(
                    jsonElement.toString(),
                    GetSalesBean::class.java
                )
                if (getSalesBean.error == false) {
                    handleSalesLis(getSalesBean.data)
                }
            }

            if (tag == ApiContants.getCustomer) {
                val customerBean = apiClient.getConvertIntoModel<CustomerBean>(
                    jsonElement.toString(),
                    CustomerBean::class.java
                )
                if (customerBean.error == false) {
                    Log.d("asdasd", Gson().toJson(customerBean.data))
                    setCustomer(customerBean.data)
                }
            }
        } catch (e: Exception) {
            Log.d("error>>", e.localizedMessage)
        }
    }

    override fun failure(tag: String?, errorMessage: String) {
        apiClient.progressView.hideLoader()
        Utility.showSnackBar(requireActivity(), errorMessage)
    }

    fun setCustomer(data: List<CustomerBean.Data>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1,

            state
        )
        binding.SelectCustomer.setAdapter(adapte1)
        binding.SelectCustomer.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position).name))

            for (i in data.indices) {
                if (data.get(i).name.equals(parent.getItemAtPosition(position))) {
                    Log.d("StateID", data.get(i).id.toString())
                    apiGetSales(data.get(i).id.toString())
                    setCustomer(data)

                }
            }
        })
        adapte1.notifyDataSetChanged()

    }

    fun handleSalesLis(data: List<GetSalesBean.Data>) {
        binding.rcSales.layoutManager =
            LinearLayoutManager(requireContext())
        val mAllAdapter = GetSaleAdapter(requireActivity(), data, object :
            RvStatusClickListner {
            override fun clickPos(link: String, pos: Int) {
                requireActivity().startActivity(
                    Intent(
                        requireActivity(),
                        WebviewActivity::class.java
                    ).putExtra("invoiceUrl", link)
                )
            }
        })
        binding.rcSales.adapter = mAllAdapter
        mAllAdapter.notifyDataSetChanged()
        // rvMyAcFiled.isNestedScrollingEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        //   apiAllGet()
    }

}