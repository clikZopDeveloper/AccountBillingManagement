package com.example.billing_expertz.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billing_expertz.Model.VendorLabourBean
import com.example.billing_expertz.R
import com.example.billing_expertz.Utills.RvStatusClickListner


class GetVendorAdapter(
    var context: Activity,
    var list: List<VendorLabourBean.Data>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<GetVendorAdapter.MyViewHolder>(), Filterable {
    var mFilteredList: MutableList<VendorLabourBean.Data> = list as MutableList<VendorLabourBean.Data>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_customer_list, parent, false)
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

        holder.tvName.text = mFilteredList[position].name
        holder.tvMobNo.text = mFilteredList[position].number

        holder.itemView.setOnClickListener {
            //  rvClickListner.clickPos(list[position].indexId)
        }
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvMobNo: TextView = itemview.findViewById(R.id.tvMobNo)
        val tvName: TextView = itemview.findViewById(R.id.tvName)

    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    mFilteredList = list as MutableList<VendorLabourBean.Data>
                } else {
                    val filteredList = ArrayList<VendorLabourBean.Data>()
                    for (serviceBean in list) {
                        if (serviceBean.name.toString().toLowerCase().contains(charString.toLowerCase())
                            ||serviceBean.number.toString().toLowerCase().contains(charString.toLowerCase())

                        ) {
                            filteredList.add(serviceBean)
                        }
                    }
                    mFilteredList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mFilteredList = filterResults.values as ArrayList<VendorLabourBean.Data>
                android.os.Handler().postDelayed(Runnable { notifyDataSetChanged() }, 200)
            }
        }
    }
}