package com.spopli.gosarconstruction

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
        supportActionBar?.hide()

        binding.toolbarOne.toolbarLayout.setNavigationOnClickListener {
            binding.myDrawer.openDrawer(Gravity.LEFT)
        }

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
                val build = AlertDialog.Builder(this)
                build.setTitle("Download Report")
                build.setPositiveButton("Download Now", DialogInterface.OnClickListener { dialogInterface, i ->
                    val pdfDocument = PdfDocument()
                    val myPdfDocument = PdfDocument()
                    val paint = Paint()
                    val myPageInfo = PageInfo.Builder(250, 350, 1).create()
                    val myPage = myPdfDocument.startPage(myPageInfo)
                })
                build.setNegativeButton("Not Now", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    Toast.makeText(this, "Thank You All Data Addedd", Toast.LENGTH_SHORT).show()
                })
                build.show()
            }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }

        binding.goFinBackBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure you want to go back!?")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                val intent = Intent(this,MaterialActivity::class.java)
                startActivity(intent)
            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            builder.show()
        }

    }
}