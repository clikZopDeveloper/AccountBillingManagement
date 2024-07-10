package com.example.billing_expertz.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.billing_expertz.Activity.AddInvoiceActivity
import com.example.billing_expertz.Model.GetSalesBean
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.RvStatusClickListner
import com.stpl.antimatter.Utils.ApiContants


class GetSaleAdapter(
    var context: Activity,
    var list: List<GetSalesBean.Data>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<GetSaleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_sales, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        /*     holder.tvAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
             holder.tvQtyAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
             holder.tvQtyMinus.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
             holder.tvQty.background = RoundView(Color.TRANSPARENT, RoundView.getRadius(20f), true, R.color.orange)
             holder.tvOff.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
             holder.tvAdd.visibility = View.VISIBLE*/

        holder.llInvoice.visibility=View.VISIBLE
        holder.tvInvoice.text = list[position].invoice
       // holder.tvInvoiceNumber.text = list[position].invoiceNumber
        holder.tvPaymentStatus.text = list[position].paymentStatus
        holder.tvAmount.text =  ApiContants.currency+list[position].amt
        holder.tvIsBilled.text = list[position].isBilled
        holder.tvDueDate.text = list[position].dueDate
        holder.tvInvoiceDate.text = list[position].invoiceDate
        if (list[position].paymentStatus.equals("pending")) {
            holder.tvPaymentStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.yellow_color
                )
            );
        } else if (list[position].paymentStatus.equals("Success")) {
            holder.tvPaymentStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            holder.tvPaymentStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.paymentsdk_color_red
                )
            );
        }

        holder.ivEdit.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    AddInvoiceActivity::class.java
                )
                    .putExtra("id", list[position].id.toString())
                    .putExtra("way","EditInvoice")
            )
        }

        holder.tvViewAdminInvoice.setOnClickListener {
             rvClickListner.clickPos(list[position].adminCopy,list[position].id)
        }
        holder.tvViewCustInvoice.setOnClickListener {
             rvClickListner.clickPos(list[position].customerCopy,list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvInvoice: TextView = itemview.findViewById(R.id.tvInvoice)
        val tvPaymentStatus: TextView = itemview.findViewById(R.id.tvPaymentStatus)
        val tvInvoiceNumber: TextView = itemview.findViewById(R.id.tvInvoiceNumber)
        val tvIsBilled: TextView = itemview.findViewById(R.id.tvIsBilled)
        val tvDueDate: TextView = itemview.findViewById(R.id.tvDueDate)
        val tvViewAdminInvoice: TextView = itemview.findViewById(R.id.tvViewAdminInvoice)
        val tvViewCustInvoice: TextView = itemview.findViewById(R.id.tvViewCustInvoice)
        val tvInvoiceDate: TextView = itemview.findViewById(R.id.tvInvoiceDate)
        val tvAmount: TextView = itemview.findViewById(R.id.tvAmount)
        val llInvoice: LinearLayout = itemview.findViewById(R.id.llInvoice)
        val ivEdit: ImageView = itemview.findViewById(R.id.ivEdit)
    }

}