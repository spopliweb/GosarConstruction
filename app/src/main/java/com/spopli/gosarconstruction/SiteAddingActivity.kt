package com.spopli.gosarconstruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.spopli.gosarconstruction.databinding.ActivitySiteAddingBinding
import com.spopli.gosarconstruction.myClass.SiteDetails

class SiteAddingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySiteAddingBinding
    lateinit var auth: FirebaseAuth
    lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiteAddingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mySiteBtn.setOnClickListener {
            val siteName = binding.mySiteName.text.toString()
            val siteAddress = binding.mySiteAddress.text.toString()
            val sitePhone = binding.mySitePhone.text.toString()
            dbref = FirebaseDatabase.getInstance().getReference("Sites").child(siteName)
            val site = SiteDetails(siteName, siteAddress, sitePhone)
            dbref.push().setValue(site).addOnSuccessListener {
                val intent = Intent(this, ShowSitesActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}