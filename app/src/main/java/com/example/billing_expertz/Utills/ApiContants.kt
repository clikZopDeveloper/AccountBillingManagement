package com.stpl.antimatter.Utils

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup.MarginLayoutParams
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

public class ApiContants {
    companion object {
        var isconnectedtonetwork = false

        const val EmailAddress = "EmailAddress"
        const val REQ_CODE_VERSION_UPDATE = 530
        const val PlaceLocation = "location"
        const val mobileNumber = "mobileNumber"
        const val password = "password"
        const val name = "name"
        const val state = "state"
        const val status = "status"
        const val walletBalance = "walletBalance"
        const val PlaceRegion = "locationCountry"
        const val PlaceLatLang = "locLatLang"
        const val PlaceLat = "locLat"
        const val PlaceLang = "PlaceLang"
        val WhatsAppNumber = "+91****************"
        const val PREF_IS_METRIC = "unit"
        const val UserDetails = "userDetails"
        const val UserAvailableAmt = "useravailablebal"
        const val DeviceToken = "321"
        const val AccessToken = "accessToken"
        const val Type = "android"
        const val currency = "₹"
        const val dayStatus = "dayStatus"

///////////////////Testing URL////////////////////////////////////////
           const val BaseUrl="https://billing-expertz.clikzopdevp.com/api/"// URL



        const val success = "success"
        const val failure = "failure"
        const val NoInternetConnection = "Please check your internet connection"

        //        api Tags
        const val login = "login"
        const val logout = "logout"
        const val Dashboard = "dashboard"
        const val getCity = "get-city"
        const val getState = "get-state"
        const val getVendorLabour = "get-vendor-labour"
        const val getSale = "get-sale"
        const val getGST = "get-gst"
        const val getCustomer = "get-customers"
        const val getCompany = "get-company"
        const val getExpenseCategory = "get-expense-category"
        const val getCategory = "get-category"
        const val getExpenseSubCategory = "get-expense-sub-category"
        const val getSubCategory = "get-sub-category"
        const val getExpenses = "get-expense"
        const val getAddSale = "add-sale"
        const val getAddExpeses = "add-expense"
        const val getSettings = "settings"
        const val GetWalletLadger = "wallet-ladger"
        const val getSaleDetail = "get-sale-details"
        const val getProfile = "get-profile"
        const val getPasswordChange = "change-password"

      fun nameFirstLatter(text:String,textPrint:TextView){
        //  val str = "Hello World"
          val firstChar = text.first()
          textPrint.text=firstChar.toString()
      }

///////////////////////////////Call Upload Image/////////////////

        fun uploadImage(activity: Activity, SELECT_PICTURES: Int) {
            if (Build.VERSION.SDK_INT < 19) {
                var intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                activity.startActivityForResult(
                    Intent.createChooser(intent, "Choose Pictures"), SELECT_PICTURES
                )
            } else { // For latest versions API LEVEL 19+
                var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                activity.startActivityForResult(intent, SELECT_PICTURES);
            }
        }
        ////////////////////////
        fun ClickPicCamera(acivity: Activity, CAMERA_PERMISSION_CODE: Int) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            acivity.startActivityForResult(intent, CAMERA_PERMISSION_CODE)
        }

        fun requestCameraPermission(activity: Activity, PERMISSION_CODE: Int) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES
                ),
                PERMISSION_CODE
            )
        }

        ///////////////////////Toast Message//////////////////
        fun showToastMsg(context: Context,msg:String){
            Toast.makeText(
                context,
                msg,
                Toast.LENGTH_SHORT
            ).show()
        }
        ///////////////////////////Date Time/////////////////////

        fun showDate(context: Context, editDate: EditText){
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR]
            val month = c[Calendar.MONTH]
            val day = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                context,
                { view, year, monthOfYear, dayOfMonth ->
                    //  dob.setText(dateofnews);
                    //   val dateofnews = "${year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString()}"
                    //   MM-DD-YYYY
                    val dateofnews ="${year.toString() + "-" + (monthOfYear + 1).toString() + "-" + dayOfMonth.toString()}"

                    editDate.setText(dateofnews)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        fun showTime(context: Context, editTime: EditText) {
            val calendar = Calendar.getInstance();
            val hour: Int = calendar!!.get(Calendar.HOUR_OF_DAY)
            val min: Int = calendar!!.get(Calendar.MINUTE)
            editTime.setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    context,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        editTime.setText(
                            "$hourOfDay:$minute"
                        )
                    },
                    hour,
                    min,
                    false
                )
                timePickerDialog.show()
            }
        }


        ///////////////////Moveable Button//////////

        fun movabalebutton(view: FloatingActionButton, context: Activity?) {
            var dX = 0.0f
            var dY = 0.0f
            var downRawX = 0f
            val CLICK_DRAG_TOLERANCE = 10f
             var downRawY:kotlin.Float = 0f
            view.setOnTouchListener(OnTouchListener { v, motionEvent ->
                val layoutParams = view.layoutParams as MarginLayoutParams
                val action = motionEvent.action
                if (action == MotionEvent.ACTION_DOWN) {
                    downRawX = motionEvent.rawX
                    downRawY = motionEvent.rawY
                    dX = view.x - downRawX
                    dY = view.y - downRawY
                    return@OnTouchListener true // Consumed
                } else if (action == MotionEvent.ACTION_MOVE) {
                    val viewWidth = view.width
                    val viewHeight = view.height
                    val viewParent = view.parent as View
                    val parentWidth = viewParent.width
                    val parentHeight = viewParent.height
                    var newX: Float = motionEvent.rawX + dX
                    newX = Math.max(
                        layoutParams.leftMargin.toFloat(),
                        newX
                    ) // Don't allow the FAB past the left hand side of the parent
                    newX = Math.min(
                        (parentWidth - viewWidth - layoutParams.rightMargin).toFloat(),
                        newX
                    ) // Don't allow the FAB past the right hand side of the parent
                    var newY: Float = motionEvent.rawY + dY
                    newY = Math.max(
                        layoutParams.topMargin.toFloat(),
                        newY
                    ) // Don't allow the FAB past the top of the parent
                    newY = Math.min(
                        (parentHeight - viewHeight - layoutParams.bottomMargin).toFloat(),
                        newY
                    ) // Don't allow the FAB past the bottom of the parent
                    view.animate()
                        .x(newX)
                        .y(newY)
                        .setDuration(0)
                        .start()
                    return@OnTouchListener true // Consumed
                } else if (action == MotionEvent.ACTION_UP) {
                    val upRawX = motionEvent.rawX
                    val upRawY = motionEvent.rawY
                    val upDX: Float = upRawX - downRawX
                    val upDY: Float = upRawY - downRawY
                    if (Math.abs(upDX) <CLICK_DRAG_TOLERANCE && Math.abs(
                            upDY
                        ) <CLICK_DRAG_TOLERANCE
                    ) { // A click
                     //   context?.startActivity(Intent(context, AddLeadActivity::class.java).putExtra("way","Add Lead"))

                        // openWhatsApp(phoneNumber, context)
                        return@OnTouchListener true
                    } else { // A drag
                        return@OnTouchListener true // Consumed
                    }
                }
                false
            })
        }

    }


}