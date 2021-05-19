package com.riliv.covid.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riliv.covid.R
import com.riliv.covid.model.SpinnerDialogCountry
import kotlinx.android.synthetic.main.item_searcable_list.view.*

class SpinnerDialogCountryAdapter(
    private val mContext: Context,
    private val listener: SpinnerDialogListener
) :
    RecyclerView.Adapter<SpinnerDialogCountryAdapter.ViewHolder>() {
    private var dataList: ArrayList<SpinnerDialogCountry> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_searcable_list, parent, false)
        return ViewHolder(view)
    }

    fun updateData(dataList: List<SpinnerDialogCountry>) {
        Log.e("dataspineer",dataList.toString())
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position],position,mContext,listener)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            data: SpinnerDialogCountry,
            position: Int,
            ctx: Context,
            listener: SpinnerDialogListener
        ) {

            itemView.tv_item.text = data.name
            itemView.holderLayout.setOnClickListener {
                listener.onSelected(data)
            }
        }
    }



    interface SpinnerDialogListener {
        fun onSelected(data: SpinnerDialogCountry)
    }


}