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
import com.spopli.gosarconstruction.databinding.ActivityMachineryBinding
import com.spopli.gosarconstruction.myClass.MachineryDetails
import java.text.SimpleDateFormat
import java.util.*

class MachineryActivity : AppCompatActivity() {
    lateinit var binding: ActivityMachineryBinding
    lateinit var macRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMachineryBinding.inflate(layoutInflater)
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

        macRef = FirebaseDatabase.getInstance().getReference("Sites").child(siteOne.toString()).child(currentDate)
            .child("Machinery")

        binding.goMachBtn.setOnClickListener {
            val macName = binding.goMachName.text.toString()
            val totalHrs = binding.goMachHrs.text.toString()
            val remark = binding.goMachRemark.text.toString()

            val machinary = MachineryDetails(macName, totalHrs, remark)

            macRef.push().setValue(machinary).addOnSuccessListener {
                Toast.makeText(this, "Data Addedd", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MaterialActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.goMachBackBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to go back!?")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                val intent = Intent(this,ManpowerActivity::class.java)
                startActivity(intent)
            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            builder.show()
        }
    }
}