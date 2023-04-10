package com.example.graficasweb

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BarChartActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var chart: AnyChartView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)
        database = Firebase.database.reference
        chart = findViewById(R.id.barChart)

        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var cantAceite = i.child("Cant_aceite").getValue().toString().toInt()
                    var cantAgua = i.child("Cant_agua").getValue().toString().toInt()
                    val cartesian: Cartesian = AnyChart.column()
                    val dataBarChart: MutableList<DataEntry> = mutableListOf()
                    dataBarChart.add(ValueDataEntry("Agua", cantAgua))
                    dataBarChart.add(ValueDataEntry("Aceite", cantAceite))
                    val column: Column = cartesian.column(dataBarChart)
                    column.fill("function() {" +
                            "if (this.x === 'Agua') return '#1EC0E8';\n" +
                            "return '#E8D31E';}");

                    column.tooltip().titleFormat("{%x}").position(Position.CENTER_BOTTOM).anchor(
                        Anchor.CENTER_BOTTOM).offsetX(0f).offsetY(5f).format("{%Value}{groupsSeparator: }")
                    cartesian.animation(true)
                    cartesian.title("Cantidad de latas por tipo")
                    cartesian.yScale().minimum(0f)
                    cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }")
                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
                    cartesian.interactivity().hoverMode(HoverMode.BY_X)
                    cartesian.xAxis(0).title("Tipo")
                    cartesian.yAxis(0).title("Cantidad")
                    //cartesian.background().enabled(true);
                    //cartesian.background().fill("#2f3241");
                    chart!!.setChart(cartesian)
                }
            }
        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }
}