package com.example.graficasweb

import android.R.attr.data
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.charts.Pie
import com.anychart.core.cartesian.series.Column
import com.anychart.palettes.RangeColors
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PieChartActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var chart: AnyChartView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
        chart = findViewById(R.id.pieChart)
        database = Firebase.database.reference

        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var cantAceite = i.child("Cant_aceite").getValue().toString().toInt()
                    var cantAgua = i.child("Cant_agua").getValue().toString().toInt()
                    val pie : Pie = AnyChart.pie()
                    val dataPieChart: MutableList<DataEntry> = mutableListOf()
                    dataPieChart.add(ValueDataEntry("Agua", cantAgua))
                    dataPieChart.add(ValueDataEntry("Aceite", cantAceite))
                    pie.data(dataPieChart)
                    pie.title("Porcentaje de latas vendidas")
                    chart!!.setChart(pie)
                }
            }
        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }
}