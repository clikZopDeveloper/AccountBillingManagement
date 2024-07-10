package com.example.billing_expertz.Fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.billing_expertz.Activity.DashboardActivity
import com.example.billing_expertz.Adapter.WalletLadgerAdapter
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.Model.WalletLedgerBean
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.GeneralUtilities
import com.example.billing_expertz.Utills.RvStatusClickListner
import com.example.billing_expertz.Utills.SalesApp
import com.example.billing_expertz.Utills.Utility

import com.example.billing_expertz.databinding.FragmentWalletLadgerBinding
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import java.util.*

class WalletLadgerFragment : Fragment(), ApiResponseListner {
    private lateinit var apiClient: ApiController
    private var _binding: FragmentWalletLadgerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalletLadgerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val titleText = (activity as DashboardActivity?)
        titleText?.setTitle("Wallet Ledger")
        apiClient = ApiController(activity, this)

        binding.apply {
            editdateFrom.setOnClickListener(View.OnClickListener {
                val c = Calendar.getInstance()
                val year = c[Calendar.YEAR]
                val month = c[Calendar.MONTH]
                val day = c[Calendar.DAY_OF_MONTH]
                val datePickerDialog = DatePickerDialog(
                    requireContext(), { view, year, monthOfYear, dayOfMonth ->
                        //  dob.setText(dateofnews);
                        //    val dateofnews = "${dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year}"
                        val dateofnews =
                            "${year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString()}"

                        //   val dateofnews = (monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year

                        editdateFrom.setText(dateofnews)
                    },
                    year, month, day
                )
                datePickerDialog.show()
            })

            editToDate.setOnClickListener(View.OnClickListener {
                val c = Calendar.getInstance()
                val year = c[Calendar.YEAR]
                val month = c[Calendar.MONTH]
                val day = c[Calendar.DAY_OF_MONTH]
                val datePickerDialog = DatePickerDialog(
                    requireContext(), { view, year, monthOfYear, dayOfMonth ->
                        //  dob.setText(dateofnews);
                        //    val dateofnews = "${dayOfMonth.toString() + "/" + (monthOfYear + 1).toString() + "/" + year}"
                        val dateofnews =
                            "${year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString()}"

                        //   val dateofnews = (monthOfYear + 1).toString() + "/" + dayOfMonth + "/" + year

                        editToDate.setText(dateofnews)
                    },
                    year, month, day
                )
                datePickerDialog.show()
            })

            fbAddArchitect.setOnClickListener {
                apiWalletLadger()
            }
        }

        apiWalletLadger()

        return root

    }

    fun apiWalletLadger() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        params["fromDt"] = binding.editdateFrom.text.toString()
        params["toDt"] = binding.editToDate.text.toString()
        apiClient.getApiPostCall(ApiContants.GetWalletLadger, params)
    }

    fun dialogUpdateTask(taskID: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to update task?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.GetWalletLadger) {
                var walletLedgerBean = apiClient.getConvertIntoModel<WalletLedgerBean>(
                    jsonElement.toString(),
                    WalletLedgerBean::class.java
                )
                if (walletLedgerBean.error == false) {
                    setData(walletLedgerBean.data.userWallet)
                    //     handleAllTask(walletLedgerBean.data.walletHistory)
                } else {
                    Toast.makeText(activity, walletLedgerBean.msg, Toast.LENGTH_SHORT).show()
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

    fun setData(userWallet: WalletLedgerBean.Data.UserWallet) {
        binding.apply {
            tvDate.text=userWallet.createdAt
            tvRole.text=userWallet.role//93586
            tvName.text=userWallet.name
            tvMobNo.text=userWallet.mobile
            tvEmailID.text=userWallet.email
            tvAddress.text=userWallet.address+"\n"+userWallet.city+" "+userWallet.state
        }
    }

    fun handleAllTask(data: List<WalletLedgerBean.Data>) {
        binding.rcTask.layoutManager =
            LinearLayoutManager(requireContext())
        val mAllAdapter = WalletLadgerAdapter(requireActivity(), data, object :
            RvStatusClickListner {
            override fun clickPos(status: String, pos: Int) {
                dialogUpdateTask(pos)
            }
        })
        binding.rcTask.adapter = mAllAdapter
        mAllAdapter.notifyDataSetChanged()
        // rvMyAcFiled.isNestedScrollingEnabled = false
    }

    fun openAddTaskDialog() {
        val dialog: Dialog = GeneralUtilities.openBootmSheetDailog(
            R.layout.dialog_add_task, R.style.AppBottomSheetDialogTheme,
            requireActivity()
        )
        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val btnAddTask = dialog.findViewById<TextView>(R.id.btnAddTask) as TextView
        val edAddTask = dialog.findViewById<EditText>(R.id.edAddTask) as EditText

        btnAddTask.setOnClickListener {
            if (TextUtils.isEmpty(edAddTask.text.toString())) {
                Toast.makeText(activity, "Please enter task", Toast.LENGTH_SHORT).show()

            } else {
                dialog.dismiss()
            }


        }
        ivClose.setOnClickListener { dialog.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}