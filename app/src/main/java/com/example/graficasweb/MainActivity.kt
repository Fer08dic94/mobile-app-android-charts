package com.example.graficasweb

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.graficasweb.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityMainBinding

    lateinit var irGraficaBarras: Button
    lateinit var irGraficaPastel: Button
    lateinit var irGraficaLineas: Button
    lateinit var irKpis: Button
    lateinit var cerrar: Button

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
       //setContentView(binding.root)
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_main)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#3949AB")))
        irGraficaBarras = findViewById(R.id.btnbarras)
        irGraficaPastel = findViewById(R.id.btnpastel)
        irGraficaLineas = findViewById(R.id.btnlineas)
        irKpis = findViewById(R.id.btnKpis)
        cerrar = findViewById(R.id.btnCerrarSesion)

        irGraficaBarras.setOnClickListener(){
            startActivity(Intent(this,BarChartActivity::class.java))
        }
        irGraficaPastel.setOnClickListener(){
            startActivity(Intent(this,PieChartActivity::class.java))
        }
        irGraficaLineas.setOnClickListener(){
            startActivity(Intent(this,LineChartActivity::class.java))
        }
        irKpis.setOnClickListener(){
            startActivity(Intent(this,KpisActivity::class.java))
        }
        cerrar.setOnClickListener{
            auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }
}