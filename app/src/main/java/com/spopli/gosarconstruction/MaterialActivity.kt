package com.spopli.gosarconstruction

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.spopli.gosarconstruction.databinding.ActivityMaterialBinding
import com.spopli.gosarconstruction.myClass.MaterialDetails
import java.text.SimpleDateFormat
import java.util.*

class MaterialActivity : AppCompatActivity() {
    lateinit var binding : ActivityMaterialBinding
    lateinit var matRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.toolbarOne.toolbarLayout.setNavigationOnClickListener {
            binding.myDrawer.openDrawer(Gravity.LEFT)
        }

        val sharedPreferences : SharedPreferences = getSharedPreferences("User Data", MODE_PRIVATE)
        val siteOne: String? = sharedPreferences.getString("Site Name", "")
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        binding.goCurrentDate.text = currentDate
        matRef = FirebaseDatabase.getInstance().getReference("Sites").child(siteOne.toString()).child(currentDate)
            .child("Material")

        binding.goMatBtn.setOnClickListener {
            val item = binding.goMatItem.text.toString()
            val inwardMaterial = binding.goMatInwardItem.text.toString()
            val used = binding.goMatItemUsed.text.toString()
            val balance = binding.goMatItemBalance.text.toString()

            val matDetails = MaterialDetails(item, inwardMaterial, used, balance)

            matRef.push().setValue(matDetails).addOnSuccessListener {
                Toast.makeText(this, "Data Addedd", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, FinanceActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

    }
}