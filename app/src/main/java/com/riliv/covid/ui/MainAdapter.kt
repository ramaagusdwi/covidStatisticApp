package com.riliv.covid.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.riliv.covid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainAdapter(
    private val context: Context,
    private val values: Array<Int>,
    private val numbersInWords: Array<String>,
    private val numberImage: IntArray
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var textView2: TextView

    override fun getCount(): Int {
        return numbersInWords.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.rowitem, null)
        }
        imageView = convertView!!.findViewById(R.id.imageView)
        textView = convertView.findViewById(R.id.textViewLabel)
        textView2 = convertView.findViewById(R.id.textViewJumlah)
        imageView.setImageResource(numberImage[position])
        textView.text = numbersInWords[position]
        textView2.text = commaFormat(values[position])

        if (position == 0){
            textView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        }else if(position == 1){
            textView.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }else if(position == 2){
            textView.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else if(position == 3){
            textView.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        return convertView
    }

    private fun commaFormat(value: Int): String {
        var s = ""
        try {
            // The comma in the format specifier does the trick
            s = String.format("%,d", value.toLong())
        } catch (e: NumberFormatException) {
            Log.d("exc",e.message!!)
        }
        return s;
    }
}