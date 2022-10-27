package com.spopli.gosarconstruction

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.spopli.gosarconstruction.databinding.ActivityFinanceBinding
import com.spopli.gosarconstruction.myClass.FinanceDetails
import java.text.SimpleDateFormat
import java.util.*

class FinanceActivity : AppCompatActivity() {
    lateinit var binding: ActivityFinanceBinding
    lateinit var finRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences : SharedPreferences = getSharedPreferences("User Data", MODE_PRIVATE)
        val siteOne: String? = sharedPreferences.getString("Site Name", "")
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        binding.goCurrentDate.text = currentDate
        finRef = FirebaseDatabase.getInstance().getReference("Sites").child(siteOne.toString()).child(currentDate)
            .child("Finance")

        binding.goFinBtn.setOnClickListener {
            val closingBalnce = binding.goFinClosing.text.toString()
            val receipt = binding.goFinReceipt.text.toString()
            val expenditure = binding.goFinExpend.text.toString()
            val closingBalance = binding.goFinClosing.text.toString()
            val visitors = binding.goFinVisitor.text.toString()
            val remark = binding.goFinRemark.text.toString()

            val financeDetails = FinanceDetails(closingBalnce.toDouble(), receipt, expenditure, closingBalance.toDouble(), visitors, remark)

            finRef.push().setValue(financeDetails).addOnSuccessListener {
                Toast.makeText(this, "Thank You All Data Addedd", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }

    }
}