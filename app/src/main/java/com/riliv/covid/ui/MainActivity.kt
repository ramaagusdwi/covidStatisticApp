package com.riliv.covid.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutSection
import com.riliv.covid.R
import com.riliv.covid.base.BaseActivity
import com.riliv.covid.model.Countries
import com.riliv.covid.model.SpinnerDialogCountry
import com.riliv.covid.ui.dialog.SpinnerDialogCountryAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class MainActivity : BaseActivity<MainPresenter>(), MainView,
    SpinnerDialogCountryAdapter.SpinnerDialogListener {
    lateinit var gridView: GridView
    private var titles = arrayOf("Total Case", "Active Case", "Recovered", "Death")

    private var images = intArrayOf(
        R.drawable.ic_innocent,
        R.drawable.ic_sad,
        R.drawable.ic_happy,
        R.drawable.ic_cry
    )
    var data_spinner = ArrayList<SpinnerDialogCountry>()
    private lateinit var mProgressDialog: ProgressDialog
    private var countryList: ArrayList<Countries> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVar()
        initClick()
        presenter.getSummary()
    }

    private fun initVar() {
        mProgressDialog = ProgressDialog(this)
    }

    override fun instantiatePresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun setCountries(countries: List<Countries>) {
        countryList.clear()
        countryList.addAll(countries)
        Log.d("country", countries.size.toString())
        for (item in countries) {
            if (item.Country == "Indonesia") {
                //set gridview indo // var values = arrayOf("295,449", "63,444", "221,000", "10,997")
                //total case jumlah semua, active case ambil dari total confirmed, recovered ambil dari total recovered, deaths ambil dari total deaths
                var values = arrayOf(
                    item.TotalConfirmed!! + item.TotalRecovered!! + item.TotalDeaths!!,
                    item.TotalConfirmed,
                    item.TotalRecovered,
                    item.TotalDeaths
                )
                gridView = findViewById(R.id.gridView)
                val mainAdapter = MainAdapter(this@MainActivity, values, titles, images)
                gridView.adapter = mainAdapter
                gridView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        Toast.makeText(
                            applicationContext, "You CLicked " + titles[+position],
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                tv_affected.text = commaFormat(item.TotalConfirmed)
                tv_recovered.text = commaFormat(item.TotalRecovered)
                tv_country.text = "Indonesia"

                val section2 = DonutSection(
                    name = "section_2",
                    color = Color.parseColor("#01ECCD"),
                    amount = item.TotalConfirmed.toFloat()
                )
                donut_view2.cap = 1f
                donut_view2.submitData(listOf(section2))

                val section1 = DonutSection(
                    name = "section_1",
                    color = Color.parseColor(       "#CC00BFA6"),
                    amount = item.TotalRecovered.toFloat()
                )
                donut_view3.cap = 1f
                donut_view3.submitData(listOf(section1))

                var totalReco = item.TotalRecovered
                var totalSemuaKasus = item.TotalConfirmed +item.TotalRecovered+ item.TotalDeaths
                var percentage = (totalReco.toDouble() / totalSemuaKasus) * 100
                tv_ratio.text = "${percentage.roundToInt()} %"

                val dateStr =  item.Date
                val curFormater = SimpleDateFormat("yyyy-MM-dd")
                val dateObj: Date = curFormater.parse(dateStr)

                val newDateStr: String = curFormater.format(dateObj)
                tv_date.text = "Last Update $newDateStr"
            }

            //init data spinner country
            data_spinner.add(
                SpinnerDialogCountry(
                    item.ID!!, item.Country!!, false
                )
            )

        }


    }

    override fun showLoading() {
        mProgressDialog.show()
        mProgressDialog.setCancelable(false)
        mProgressDialog.setMessage("Loading")
    }

    override fun hideLoading() {
        if (mProgressDialog.isShowing())
            mProgressDialog.hide()
    }

    private fun initClick() {
        chooseCountry.setOnClickListener {
            showDialog("Country", data_spinner)
        }
    }

    private lateinit var mDialog: androidx.appcompat.app.AlertDialog
    private fun showDialog(title: String, data: ArrayList<SpinnerDialogCountry>) {
        mDialog = androidx.appcompat.app.AlertDialog.Builder(this).create()
        val inflater = this.layoutInflater

        val dialogView =
            inflater.inflate(R.layout.dialog_searchable_list, null)
        mDialog.setView(dialogView)
        var etSearch = dialogView.findViewById(R.id.etSearch) as EditText
        var rvList = dialogView.findViewById(R.id.rvList) as RecyclerView
        var tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle) as TextView
        var btnClose = dialogView.findViewById(R.id.btnClose) as ImageView

        tvDialogTitle.text = title
        btnClose.setOnClickListener {
            mDialog.dismiss()
        }

        var pAdapter = SpinnerDialogCountryAdapter(this, this)
        val linearLayout: RecyclerView.LayoutManager = LinearLayoutManager(this!!)
        rvList.layoutManager = linearLayout
        rvList.adapter = pAdapter

        pAdapter.updateData(data)
        pAdapter.notifyDataSetChanged()

        mDialog.show()

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (s.length != 0) {
                    var tempList = filter(data, s.toString())
                    pAdapter.updateData(tempList)
                    pAdapter.notifyDataSetChanged()
                } else {
                    pAdapter.updateData(data)
                    pAdapter.notifyDataSetChanged()
                }

            }
        })
    }

    private fun filter(
        models: List<SpinnerDialogCountry>,
        query: String
    ): List<SpinnerDialogCountry> {
        val lowerCaseQuery = query.toLowerCase()

        val filteredModelList = ArrayList<SpinnerDialogCountry>()
        for (model in models) {
            val text = model.name.toLowerCase()
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }

    override fun onSelected(data: SpinnerDialogCountry) {
        data.selected = true

        for(data in data_spinner){
            for(country in countryList){
                if(data.selected && data.id == country.ID){
                    var values = arrayOf(
                        country.TotalConfirmed!! + country.TotalRecovered!! + country.TotalDeaths!!,
                        country.TotalConfirmed,
                        country.TotalRecovered,
                        country.TotalDeaths
                    )
                    gridView = findViewById(R.id.gridView)
                    val mainAdapter = MainAdapter(this@MainActivity, values, titles, images)
                    gridView.adapter = mainAdapter
                    gridView.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            Toast.makeText(
                                applicationContext, "You CLicked " + titles[+position],
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    tv_affected.text = commaFormat(country.TotalConfirmed)
                    tv_recovered.text = commaFormat(country.TotalRecovered)
                    tv_country.text = country.Country

                    val section2 = DonutSection(
                        name = "section_2",
                        color = Color.parseColor("#01ECCD"),
                        amount = country.TotalConfirmed.toFloat()
                    )
                    donut_view2.cap = 1f
                    donut_view2.submitData(listOf(section2))

                    val section1 = DonutSection(
                        name = "section_1",
                        color = Color.parseColor("#CC00BFA6"),
                        amount = country.TotalRecovered.toFloat()
                    )
                    donut_view3.cap = 1f
                    donut_view3.submitData(listOf(section1))

                    var totalReco = country.TotalRecovered
                    var totalSemuaKasus = country.TotalConfirmed +country.TotalRecovered+ country.TotalDeaths
                    var percentage = (totalReco.toDouble() / totalSemuaKasus) * 100
                    tv_ratio.text = "${percentage.roundToInt()} %"

                    val dateStr =  country.Date
                    val curFormater = SimpleDateFormat("yyyy-MM-dd")
                    val dateObj: Date = curFormater.parse(dateStr)

                    val newDateStr: String = curFormater.format(dateObj)
                    tv_date.text = "Last Update $newDateStr"
                }
            }
        }
        mDialog.dismiss()
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

    override fun onBackPressed() {
//        super.onBackPressed()


        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Anda yakin keluar dari aplikasi?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
            super.onBackPressed()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }


        builder.show()


    }
}