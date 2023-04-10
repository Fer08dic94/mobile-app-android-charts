package com.example.graficasweb

import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.BLUE
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.params.RggbChannelVector.BLUE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.core.graphics.blue
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class LineChartActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart)
        database = Firebase.database.reference
        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var cantAceite = i.child("Cant_aceite").getValue().toString().toInt()
                    var cantAgua = i.child("Cant_agua").getValue().toString().toInt()
                    val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)
                    anyChartView.setProgressBar(findViewById(R.id.progress_bar))
                    val cartesian: Cartesian = AnyChart.line()
                    cartesian.animation(true)
                    cartesian.padding(10.0, 20.0, 5.0, 20.0)
                    cartesian.crosshair().enabled(true)
                    cartesian.crosshair()
                        .yLabel(true)
                        .yStroke(null as Stroke?, null, null, null as String?, null as String?)
                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                    cartesian.title("Número de Latas Producidas por Mes del Año 2023.");
                    cartesian.yAxis(0).title("Número de Latas Producidas")
                    cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
                    val seriesData: MutableList<DataEntry> = mutableListOf()
                    val current = LocalDate.now()
                    val mes = current.monthValue
                    //val mesp = 2
                    // Log.i("FECHA PRUEBA", mes.toString());
                    if(mes.equals(1)){
                        seriesData.add(CustomDataEntry("Ene", cantAgua, cantAceite))
                        seriesData.add(CustomDataEntry("Feb", 0, 0))
                        seriesData.add(CustomDataEntry("Mar", 0, 0))
                        seriesData.add(CustomDataEntry("Abr", 0, 0))
                        seriesData.add(CustomDataEntry("May", 0, 0))
                        seriesData.add(CustomDataEntry("Jun", 0, 0))
                    }
                    if(mes.equals(2)){
                        seriesData.add(CustomDataEntry("Ene", 3, 2))
                        seriesData.add(CustomDataEntry("Feb", cantAgua, cantAceite))
                        seriesData.add(CustomDataEntry("Mar", 0, 0))
                        seriesData.add(CustomDataEntry("Abr", 0, 0))
                        seriesData.add(CustomDataEntry("May", 0, 0))
                        seriesData.add(CustomDataEntry("Jun", 0, 0))
                    }
                    if(mes.equals(3)){
                        seriesData.add(CustomDataEntry("Ene", 3, 2))
                        seriesData.add(CustomDataEntry("Feb", 5, 4))
                        seriesData.add(CustomDataEntry("Mar", cantAgua, cantAceite))
                        seriesData.add(CustomDataEntry("Abr", 0, 0))
                        seriesData.add(CustomDataEntry("May", 0, 0))
                        seriesData.add(CustomDataEntry("Jun", 0, 0))
                    }
                    if(mes.equals(4)){
                        seriesData.add(CustomDataEntry("Ene", 3, 2))
                        seriesData.add(CustomDataEntry("Feb", 5, 4))
                        seriesData.add(CustomDataEntry("Mar", 8, 6))
                        seriesData.add(CustomDataEntry("Abr", cantAgua, cantAceite))
                        seriesData.add(CustomDataEntry("May", 0, 0))
                        seriesData.add(CustomDataEntry("Jun", 0, 0))
                    }
                    if(mes.equals(5)){
                        seriesData.add(CustomDataEntry("Ene", 3, 2))
                        seriesData.add(CustomDataEntry("Feb", 5, 4))
                        seriesData.add(CustomDataEntry("Mar", 8, 6))
                        seriesData.add(CustomDataEntry("Abr", 1, 1))
                        seriesData.add(CustomDataEntry("May", cantAgua, cantAceite))
                        seriesData.add(CustomDataEntry("Jun", 0, 0))
                    }
                    if(mes.equals(6)){
                        seriesData.add(CustomDataEntry("Ene", 3, 2))
                        seriesData.add(CustomDataEntry("Feb", 5, 4))
                        seriesData.add(CustomDataEntry("Mar", 8, 6))
                        seriesData.add(CustomDataEntry("Abr", 1, 1))
                        seriesData.add(CustomDataEntry("May", 2, 3))
                        seriesData.add(CustomDataEntry("Jun", cantAgua, cantAceite))
                    }
                    val set = Set.instantiate()
                    set.data(seriesData)
                    val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
                    val series2Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
                    val series1: Line = cartesian.line(series1Mapping)


                    series1.name("Agua")
                        .color("#19AEE2")
                    series1.hovered().markers().enabled(true)
                    series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4.0)

                    series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5.0)
                        .offsetY(5.0)

                    val series2: Line = cartesian.line(series2Mapping)
                    series2.name("Aceite")
                        .color("#E2D019")
                    series2.hovered().markers().enabled(true)
                    series2.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4.0)
                    series2.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5.0)
                        .offsetY(5.0)

                    cartesian.legend().enabled(true);
                    cartesian.legend().fontSize(13.0);
                    cartesian.legend().padding(0.0, 0.0, 10.0, 0.0);
                    anyChartView.setChart(cartesian);
                }
            }
        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }

    private class CustomDataEntry internal constructor(
        x: String?,
        value: Number?,
        value2: Number?
    ) :
        ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
        }
    }
}