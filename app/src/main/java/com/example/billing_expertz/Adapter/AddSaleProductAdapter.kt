package com.example.billing_expertz.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.RvStatusClickListner
import com.example.kktext_testing.Model.AddProductBean
import com.stpl.antimatter.Utils.ApiContants


class AddSaleProductAdapter(
    var context: Activity,
    var list: List<AddProductBean>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<AddSaleProductAdapter.MyViewHolder>() {
    private var listData: MutableList<AddProductBean> = list as MutableList<AddProductBean>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_add_product, parent, false)
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

        holder.tvCatName.text = list[position].prod_category
        holder.tvSubCatName.text = list[position].prod_subcategory
        holder.tvPrice.text = ApiContants.currency+list[position].price
        holder.tvQty.text = "QTY : "+list[position].qty
        holder.tvGST.text = list[position].gst.toString()
        holder.tvDecription.text = list[position].description
        holder.tvCOMMISION.text = list[position].commision
      //  holder.tvCOMMISION.text = list[position].commision

        holder.itemView.setOnClickListener {
            //  rvClickListner.clickPos(list[position].indexId)
        }

        holder.ivDelete.setOnClickListener {
            deleteItem(position)
        }
    }
    fun deleteItem(index: Int){
        listData.removeAt(index)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvCatName: TextView = itemview.findViewById(R.id.tvCatName)
        val tvSubCatName: TextView = itemview.findViewById(R.id.tvSubCatName)
        val tvPrice: TextView = itemview.findViewById(R.id.tvPrice)
        val tvQty: TextView = itemview.findViewById(R.id.tvQty)
        val tvGST: TextView = itemview.findViewById(R.id.tvGST)
        val tvDecription: TextView = itemview.findViewById(R.id.tvDecription)
        val tvCOMMISION: TextView = itemview.findViewById(R.id.tvCOMMISION)
        val ivDelete: ImageView = itemview.findViewById(R.id.ivDelete)
    }

}