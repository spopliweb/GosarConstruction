package com.spopli.gosarconstruction

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.spopli.gosarconstruction.databinding.ActivityManpowerBinding
import com.spopli.gosarconstruction.myClass.ManPower
import java.text.SimpleDateFormat
import java.util.*

class ManpowerActivity : AppCompatActivity() {
    lateinit var binding: ActivityManpowerBinding
    lateinit var manDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManpowerBinding.inflate(layoutInflater)
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
        manDbRef = FirebaseDatabase.getInstance().getReference("Sites").child(siteOne.toString()).child(currentDate)
            .child("Manpower")
        binding.goManBtn.setOnClickListener {
            val contractor = binding.goManContractor.text.toString()
            val skilled = binding.goManSkilledLabour.text.toString()
            val unskilled = binding.goManUnSkilledLabour.text.toString()

            val manPower = ManPower(contractor.toInt(), skilled.toInt(), unskilled.toInt())
            manDbRef.push().setValue(manPower).addOnSuccessListener {
                Toast.makeText(this,"Man Power Data Added", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MachineryActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.goManBackBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to go back!?")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                val intent = Intent(this,WorkDetailActivity::class.java)
                startActivity(intent)
            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            builder.show()
        }
    }
}