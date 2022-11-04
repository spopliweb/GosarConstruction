package com.spopli.gosarconstruction

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.spopli.gosarconstruction.databinding.ActivityWorkDetailBinding
import com.spopli.gosarconstruction.myClass.WorkDetails
import java.text.SimpleDateFormat
import java.util.*

class WorkDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkDetailBinding
    lateinit var workRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.toolbarOne.toolbarLayout.setNavigationOnClickListener {
            binding.myDrawer.openDrawer(Gravity.LEFT)
        }

        val sharedPreferences : SharedPreferences = getSharedPreferences("User Data", MODE_PRIVATE)
        val siteOne: String? = sharedPreferences.getString("Site Name", "")

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        workRef = FirebaseDatabase.getInstance().getReference("Sites").child(siteOne.toString()).child(currentDate)
            .child("Work Details")
        binding.goCurrentDate.text = currentDate
        binding.goWorkBtn.setOnClickListener {
            val des = binding.goWorkDes.text.toString()
            val local = binding.goWorkLocal.text.toString()
            val unit = binding.goWorkUnit.text.toString()
            val total = binding.goWorkQty.text.toString()
            val remark = binding.goWorkRemark.text.toString()

            val workDetails = WorkDetails(des, local, unit, total, remark)
            workRef.push().setValue(workDetails).addOnSuccessListener {
                Toast.makeText(this,"Details Added", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@WorkDetailActivity, ManpowerActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }


    }
}