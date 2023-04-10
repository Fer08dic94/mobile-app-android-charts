package com.example.graficasweb

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.graficasweb.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        //binding.
        binding.btnLogin.setOnClickListener {
            val correo = binding.etUsername.text.toString()
            val contrasena = binding.etPassword.text.toString()

            when{
                correo.isEmpty() || contrasena.isEmpty() -> {
                    Toast.makeText(baseContext, "El correo o contraseña no pueden ir vacios.",
                        Toast.LENGTH_SHORT).show()
                } else -> {
                SignIn(correo, contrasena)
                }
            }
        }
    }

    private fun SignIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    redirecciona()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Correo o contraseña incorrectos.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun redirecciona(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}