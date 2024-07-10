package com.example.billing_expertz.Activity

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.billing_expertz.ApiHelper.ApiController
import com.example.billing_expertz.ApiHelper.ApiResponseListner
import com.example.billing_expertz.Model.*
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.ConnectivityListener
import com.example.billing_expertz.Utills.GeneralUtilities
import com.example.billing_expertz.Utills.SalesApp
import com.example.billing_expertz.Utills.Utility
import com.example.billing_expertz.databinding.ActivityAddExpenseBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.stpl.antimatter.Utils.ApiContants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddExpensesActivity : AppCompatActivity(), ApiResponseListner,
    GoogleApiClient.OnConnectionFailedListener,
    ConnectivityListener.ConnectivityReceiverListener {
    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var apiClient: ApiController
    val PERMISSION_CODE = 12345
    val CAMERA_PERMISSION_CODE1 = 123
    var SELECT_PICTURES1 = 1
    var customerID = ""
    var vendorID = ""
    var catName = ""
    var subCatName = ""
    var id = ""
    var vendorLabourName = ""
    var file2: File? = null
    var myReceiver: ConnectivityListener? = null
    var activity: Activity = this
    val expenseType = listOf("Vendor")
    val builledType = listOf("Billed", "Not Billed")
    val paymentModeType = listOf("Bank Transfer", "Cheque", "Cash")
    val activeType = listOf("Yes", "No")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_expense)
        if (SalesApp.isEnableScreenshort == true) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myReceiver = ConnectivityListener()
        binding.igToolbar.tvTitle.text = "Add Expenses"
        binding.igToolbar.ivMenu.setImageDrawable(resources.getDrawable(R.drawable.ic_back_black))
        binding.igToolbar.ivMenu.setOnClickListener { finish() }
        binding.igToolbar.ivMenu.visibility = View.VISIBLE
        binding.igToolbar.tvWalletBal.visibility = View.GONE
        apiClient = ApiController(activity, this)
        val wayType = intent.getStringExtra("way")
        if (wayType.equals("EditExpense")) {
            binding.igToolbar.tvTitle.text = "Edit Expenses"
            //   val expesneRsposne =intent.getSerializableExtra("expenseResponse") as GetExpensesBean.Data
            binding.apply {

                Glide.with(this@AddExpensesActivity)
                    .load(ApiContants.BaseUrl + intent.getStringExtra("file"))
                    .into(btnAadharFront)
                id = intent.getStringExtra("id").toString()
                customerID = intent.getStringExtra("ids").toString()
                vendorID = intent.getStringExtra("vendorId").toString()

                editName.setText(intent.getStringExtra("name").toString())
                editNote.setText(intent.getStringExtra("note").toString())
                SelectCategory.setText(intent.getStringExtra("expenseCategory"))
                editExpensesDate.setText(intent.getStringExtra("expenseDate"))
                editExpenseAmount.setText(intent.getStringExtra("amount"))
                SelectCustomer.setText(intent.getStringExtra("customerName"))
                SelectVendorLabour.setText(intent.getStringExtra("vendorName"))
                SelectPaymentMode.setText(intent.getStringExtra("paymentMode"))
                editInvoiceNumber.setText(intent.getStringExtra("invoiceNo"))
                editRefNumber.setText(intent.getStringExtra("refNo"))

            }

        } else {
            binding.igToolbar.tvTitle.text = "Add Expenses"
            id = ""
        }
        allGetApi()
        setExpenseType(expenseType)
        setBuilled(builledType)
        setPaymentMode(paymentModeType)
        apiVendorLabour()
        requestPermission()

        binding.apply {
            btnUplaodImages.setOnClickListener {
                openCameraDialog(SELECT_PICTURES1, CAMERA_PERMISSION_CODE1)
            }
            btnAddCategory.setOnClickListener {
                dialogAddCategory()
            }

            editExpensesDate.setOnClickListener(View.OnClickListener {
                ApiContants.showDate(activity, editExpensesDate)
            })

            btnSubmit.setOnClickListener {
                apiAddExpenses()
            }
        }
    }

    fun apiAddExpenses() {
        SalesApp.isAddAccessToken = true
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("expense_id", id)
        builder.addFormDataPart("customer_id", customerID)
        builder.addFormDataPart("amount", binding.editExpenseAmount.text.toString())
        builder.addFormDataPart("name", binding.editName.text.toString())
        builder.addFormDataPart("expense_category", binding.SelectCategory.text.toString())
        builder.addFormDataPart("expense_subcategory", "0")
        builder.addFormDataPart("expense_date", binding.editExpensesDate.text.toString())
        builder.addFormDataPart("payment_mode", binding.SelectPaymentMode.text.toString())
        builder.addFormDataPart("trans_id", "")
        builder.addFormDataPart("note", binding.editNote.text.toString())
        builder.addFormDataPart("invoice_no", binding.editInvoiceNumber.text.toString())
        builder.addFormDataPart("ref_no", binding.editRefNumber.text.toString())
        builder.addFormDataPart("vendor_id", vendorID.toString())
        builder.addFormDataPart("expense_type", binding.SelectExpenseType.text.toString())
        builder.addFormDataPart("invoice_id", "")
        builder.addFormDataPart("billed", binding.SelectBilled.text.toString())

        if (file2 != null) {
            builder.addFormDataPart(
                "file", file2?.name,
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file2!!)
            )
        } else {
            file2 = null
        }

        apiClient.progressView.showLoader()
        apiClient.makeCallMultipart(ApiContants.getAddExpeses, builder.build())
    }

    fun allGetApi() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getExpenseCategory, params)
        apiClient.getApiPostCall(ApiContants.getCustomer, params)
    }


    fun apiSale(custID: String) {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["customer_id"] = custID
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getSale, params)
    }

    fun apiVendorLabour() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getVendorLabour, params)
    }

    fun apiSubCategory() {
        SalesApp.isAddAccessToken = true
        val params = Utility.getParmMap()
        params["category_name"] = catName
        apiClient.progressView.showLoader()
        apiClient.getApiPostCall(ApiContants.getExpenseSubCategory, params)
    }

    override fun success(tag: String?, jsonElement: JsonElement) {
        try {
            apiClient.progressView.hideLoader()
            if (tag == ApiContants.getAddExpeses) {
                val categoryBean = apiClient.getConvertIntoModel<CategoryBean>(
                    jsonElement.toString(),
                    CategoryBean::class.java
                )
                if (categoryBean.error == false) {
                    //  catList = categoryBean.data
                    Toast.makeText(this@AddExpensesActivity, categoryBean.msg, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                } else {
                    Toast.makeText(this@AddExpensesActivity, categoryBean.msg, Toast.LENGTH_SHORT)
                        .show()

                }
            }
            if (tag == ApiContants.getExpenseCategory) {
                val categoryBean = apiClient.getConvertIntoModel<CategoryBean>(
                    jsonElement.toString(),
                    CategoryBean::class.java
                )
                if (categoryBean.error == false) {
                    //  catList = categoryBean.data
                    Log.d("asdasd", Gson().toJson(categoryBean.data))
                    setCategory(categoryBean.data)
                }
            }

            if (tag == ApiContants.getExpenseSubCategory) {
                val subCatBean = apiClient.getConvertIntoModel<SubCategoryBean>(
                    jsonElement.toString(),
                    SubCategoryBean::class.java
                )
                if (subCatBean.error == false) {
                    setSubCategory(subCatBean.data)
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

            if (tag == ApiContants.getSale) {
                val categoryBean = apiClient.getConvertIntoModel<SalesBean>(
                    jsonElement.toString(),
                    SalesBean::class.java
                )
                if (categoryBean.error == false) {
                    //  catList = categoryBean.data
                    Log.d("asdasd", Gson().toJson(categoryBean.data))
                    setSales(categoryBean.data)
                }
            }

            if (tag == ApiContants.getVendorLabour) {
                val vendorLabourBean = apiClient.getConvertIntoModel<VendorLabourBean>(
                    jsonElement.toString(),
                    VendorLabourBean::class.java
                )
                if (vendorLabourBean.error == false) {
                    //  catList = categoryBean.data
                    Log.d("asdasd", Gson().toJson(vendorLabourBean.data))
                    setVendorLabour(vendorLabourBean.data)
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

    fun setSales(data: List<SalesBean.Data>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).invoice
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.SelectSale.setAdapter(adapte1)
        binding.SelectSale.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position).invoice))
            // catInvoice=data.get(position).invoice.toString()
            setSales(data)
            for (i in data.indices) {
                if (data.get(i).invoice.equals(parent.getItemAtPosition(position))) {
                    Log.d("StateID", data.get(i).id.toString())
                }
            }
        })
        adapte1.notifyDataSetChanged()

    }

    fun setExpenseType(data: List<String>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i)
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.SelectExpenseType.setAdapter(adapte1)
        binding.SelectExpenseType.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position)))
            vendorLabourName = data.get(position)
            setExpenseType(data)
            if (data.get(position).equals("Vendor")) {
                binding.inputVendor.visibility = View.VISIBLE
                binding.inputVendor.setHint("Select Vendor")
                //   binding.inputLabour.visibility=View.GONE
            } else if (data.get(position).equals("Labour")) {
                binding.inputVendor.setHint("Select Labour")
                binding.inputVendor.visibility = View.VISIBLE
                //    binding.inputLabour.visibility=View.VISIBLE
            } else {
                binding.inputVendor.visibility = View.GONE
                //  binding.inputLabour.visibility=View.GONE
            }

            /* for (i in data.indices) {
                 if (data.get(i).invoice.equals(parent.getItemAtPosition(position))) {
                     Log.d("StateID", data.get(i).id.toString())


                 }
             }*/
        })
        adapte1.notifyDataSetChanged()

    }

    fun setBuilled(data: List<String>) {
        /*   val arr_bnk_List = ArrayList<String>()
           for (i in 0 until data.size) {
               arr_bnk_List.add( java.lang.String.valueOf(data.get(i)))
           }

           binding.SelectBilled.setAdapter(
               ArrayAdapter(
                   this@AddExpensesActivity,
                   android.R.layout.simple_list_item_1,
                   arr_bnk_List
               )
           )

           binding.SelectBilled.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
               //countryIDD = response.body().getCountrylist().get(position).getId()
           })
           binding.SelectBilled.setThreshold(1)*/


        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i)
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.SelectBilled.setAdapter(adapte1)
        binding.SelectBilled.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position)))
            catName = data.get(position)
            /* for (i in data.indices) {
                 if (data.get(i).name.equals(parent.getItemAtPosition(position))) {
                     Log.d("StateID", data.get(i).id.toString())
                     setBuilled(data)

                 }
             }*/
            setBuilled(data)
        })
        adapte1.notifyDataSetChanged()

    }

    fun setPaymentMode(data: List<String>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i)
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.SelectPaymentMode.setAdapter(adapte1)
        binding.SelectPaymentMode.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position)))
            //catName=data.get(position)
            setPaymentMode(data)
            /* for (i in data.indices) {
                 if (data.get(i).invoice.equals(parent.getItemAtPosition(position))) {
                     Log.d("StateID", data.get(i).id.toString())


                 }
             }*/
        })
        adapte1.notifyDataSetChanged()

    }

    fun setCustomer(data: List<CustomerBean.Data>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,

            state
        )
        binding.SelectCustomer.setAdapter(adapte1)
        binding.SelectCustomer.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position).name))
            catName = data.get(position).name.toString()
            for (i in data.indices) {
                if (data.get(i).name.equals(parent.getItemAtPosition(position))) {
                    Log.d("StateID", data.get(i).id.toString())
                    customerID = data.get(i).id.toString()
                    apiSale(data.get(i).id.toString())
                    setCustomer(data)

                }
            }
        })
        adapte1.notifyDataSetChanged()

    }

    fun setCategory(data: List<CategoryBean.Data>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.SelectCategory.setAdapter(adapte1)
        binding.SelectCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position).name))
            catName = data.get(position).name.toString()
            //    apiSubCategory()
            setCategory(data)
            /* for (i in data.indices) {
                 if (data.get(i).name.equals(parent.getItemAtPosition(position))) {
                     Log.d("StateID", data.get(i).id.toString())


                 }
             }*/
        })
        adapte1.notifyDataSetChanged()
    }

    fun setSubCategory(data: List<SubCategoryBean.Data>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.SelectSubCategory.setAdapter(adapte1)
        binding.SelectSubCategory.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position).name))
            subCatName = data.get(position).name.toString()
            for (i in data.indices) {
                if (data.get(i).name.equals(parent.getItemAtPosition(position))) {
                    Log.d("StateID", data.get(i).id.toString())
                    //      setCategory(data)
                    //    apiSubCategory( )
                }
            }
        })
        adapte1.notifyDataSetChanged()

    }

    fun setVendorLabour(data: List<VendorLabourBean.Data>) {
        val state = arrayOfNulls<String>(data.size)
        for (i in data.indices) {
            state[i] = data.get(i).name
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        binding.SelectVendorLabour.setAdapter(adapte1)
        binding.SelectVendorLabour.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(data.get(position).name))
            //     subCatName=data.get(position).name.toString()
            for (i in data.indices) {
                if (data.get(i).name.equals(parent.getItemAtPosition(position))) {
                    Log.d("StateID", data.get(i).id.toString())
                    //      setCategory(data)
                    vendorID = data.get(i).id.toString()
                    //     apiSubCategory( )
                }
            }
        })
        adapte1.notifyDataSetChanged()

    }

    fun ClickPicCamera(CAMERA_PERMISSION_CODE: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_PERMISSION_CODE)
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
            ),
            PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is Granted", Toast.LENGTH_SHORT).show()

            } else {
                requestPermission()
            }
        }
    }

    private fun uploadImage(SELECT_PICTURES: Int) {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures"), SELECT_PICTURES
            )
        } else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_PICTURES);
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
            requestPermission()
            uploadImage(SELECT_PICTURES)
        }

        llClickPhoto.setOnClickListener {
            dialog.dismiss()
            requestPermission()
            ClickPicCamera(CAMERA_PERMISSION_CODE)

        }
        ivClose.setOnClickListener { dialog.dismiss() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PICTURES1) {
            if (data?.getClipData() != null) { // if multiple images are selected
                var count = data.clipData?.itemCount
                Log.d("wewwe", "$count")
                for (i in 0..count!! - 1) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    val picturePath: String = GeneralUtilities.getPath(
                        applicationContext, imageUri
                    )
                    file2 = File(picturePath)
                    //val custImg = CustProdImgBean(file2)

                    //   Log.d("MultiPicturePath", picturePath)

                    //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                }

            } else if (data?.getData() != null) {   // if single image is selected
                var imageUri: Uri = data.data!!
                val picturePath: String = GeneralUtilities.getPath(
                    applicationContext, imageUri
                )
                file2 = File(picturePath)
                val myBitmap = BitmapFactory.decodeFile(file2!!.absolutePath)
                binding.btnAadharFront.setImageBitmap(myBitmap)


                Log.d("SinglePicturePath", picturePath)
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview
            }
        }

        if (requestCode == CAMERA_PERMISSION_CODE1) {
            try {
                Toast.makeText(this@AddExpensesActivity, "sdfsd", Toast.LENGTH_SHORT).show()

                val imageBitmap = data?.extras?.get("data") as Bitmap
                binding.btnAadharFront.setImageBitmap(imageBitmap)
                val tempUri = GeneralUtilities.getImageUri(applicationContext, imageBitmap)
                file2 = File(GeneralUtilities.getRealPathFromURII(this, tempUri))
                Log.e("Path", file2.toString())

                //Toast.makeText(getContext(), ""+picturePath, Toast.LENGTH_SHORT).show();
            } catch (e: java.lang.Exception) {
                Log.e("Path Error", e.toString())
                Toast.makeText(applicationContext, "" + e, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun dialogAddCategory() {
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()
        val dialog = layoutInflater.inflate(R.layout.dialog_add_cat, null)

        builder.setView(dialog)

        builder.setCanceledOnTouchOutside(false)
        builder.show()

        val ivClose = dialog.findViewById<ImageView>(R.id.ivClose)
        val editCatName =
            dialog.findViewById<TextInputEditText>(R.id.editCatName) as TextInputEditText
        val SelectActive =
            dialog.findViewById<AutoCompleteTextView>(R.id.SelectActive) as AutoCompleteTextView

        val btnSubmit = dialog.findViewById<TextView>(R.id.btnSubmit) as TextView

        ivClose.setOnClickListener { builder.dismiss() }
        val state = arrayOfNulls<String>(activeType.size)
        for (i in activeType.indices) {
            state[i] = activeType.get(i)
        }

        val adapte1: ArrayAdapter<String?>
        adapte1 = ArrayAdapter(
            this@AddExpensesActivity,
            android.R.layout.simple_list_item_1,
            state
        )
        SelectActive.setAdapter(adapte1)
        SelectActive.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("xcvxcvc", Gson().toJson(activeType.get(position)))
            //catName=data.get(position)

        })
        adapte1.notifyDataSetChanged()

        btnSubmit.setOnClickListener {
            builder.dismiss()
            if (editCatName.text.isNullOrEmpty()) {
                Toast.makeText(this, "Enter Category Name", Toast.LENGTH_SHORT).show()
            } else {
                //      apiAccept(status,ids)
            }

        }
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
