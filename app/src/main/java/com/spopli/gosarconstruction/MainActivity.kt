package com.spopli.gosarconstruction

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.spopli.gosarconstruction.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.toolbarOne.toolbarLayout.setNavigationOnClickListener {
            binding.myDrawer.openDrawer(Gravity.LEFT)
        }

        auth = FirebaseAuth.getInstance()
        val sharedPreferences : SharedPreferences = getSharedPreferences("User Data", MODE_PRIVATE)
        val email: String? = sharedPreferences.getString("Email", "")

        if(email != ""){
            val intent:Intent = Intent(this, ShowSitesActivity::class.java)
            startActivity(intent)
        }else{
            binding.goUserLoginBtn.setOnClickListener {
                val user = binding.goUser.text.toString()
                val pass = binding.goUserPass.text.toString()

                auth.signInWithEmailAndPassword(user, pass)
                    .addOnSuccessListener {
                        val shareadPrefrences: SharedPreferences = getSharedPreferences("User Data", MODE_PRIVATE)
                        val editor: SharedPreferences.Editor =shareadPrefrences.edit()
                        editor.putString("Email", user)
                        editor.apply()

                        val intent= Intent(this, ShowSitesActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }
}