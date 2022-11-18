package com.spopli.gosarconstruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.spopli.gosarconstruction.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.hide()

        binding.toolbarOne.toolbarLayout.setNavigationOnClickListener {
            binding.myDrawer.openDrawer(Gravity.LEFT)
        }
        binding.goProfileLayout.setOnClickListener {
            Toast.makeText(this, "Profile Tab is selected", Toast.LENGTH_SHORT).show()
        }
        binding.goSitesCheck.setOnClickListener {
            val intent = Intent(this, ShowSitesActivity::class.java)
            startActivity(intent)
        }
    }
}