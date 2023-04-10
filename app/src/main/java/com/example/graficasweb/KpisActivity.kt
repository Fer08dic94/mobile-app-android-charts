package com.example.graficasweb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KpisActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kpis)

        val tvTotLatas =findViewById<TextView>(R.id.totLatas)
        val tvTotAgua =findViewById<TextView>(R.id.totAgua)
        val tvTotAceite =findViewById<TextView>(R.id.totAceite)
        val tvTotEmb =findViewById<TextView>(R.id.totEmb)
        val tvTotEmbAgua  = findViewById<TextView>(R.id.totEmbAgua)
        val tvTotEmbAceite  = findViewById<TextView>(R.id.totEmbAcei)

        database = Firebase.database.reference

        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                var sb2 = StringBuilder()
                var sb3 = StringBuilder()
                var sb4 = StringBuilder()
                var sb5 = StringBuilder()
                var sb6 = StringBuilder()
                for(i in snapshot.children){
                    var cantAceite = i.child("Cant_aceite").getValue().toString().toInt()
                    var cantAgua = i.child("Cant_agua").getValue().toString().toInt()
                    var total = cantAgua + cantAceite
                    var embalajesAceite = cantAceite/5
                    var embalajesAgua = cantAgua/5
                    var embalajes = embalajesAceite+embalajesAgua
                    sb.append("$total")
                    sb2.append("$cantAgua")
                    sb3.append("$cantAceite")
                    sb4.append("$embalajes")
                    sb5.append("$embalajesAgua")
                    sb6.append("$embalajesAceite")
                }
                tvTotLatas.setText(sb)
                tvTotAgua.setText(sb2)
                tvTotAceite.setText(sb3)
                tvTotEmb.setText(sb4)
                tvTotEmbAgua.setText(sb5)
                tvTotEmbAceite.setText(sb6)
            }
        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
    }
}
