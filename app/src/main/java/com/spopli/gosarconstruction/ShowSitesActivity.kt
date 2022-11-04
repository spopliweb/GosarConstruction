package com.spopli.gosarconstruction

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.spopli.gosarconstruction.databinding.ActivityShowSitesBinding

class ShowSitesActivity : AppCompatActivity() {
    lateinit var binding : ActivityShowSitesBinding
    lateinit var spinnerRef : DatabaseReference
    lateinit var list: ArrayList<String>
    lateinit var keys: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowSitesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.toolbarOne.toolbarLayout.setNavigationOnClickListener {
            binding.myDrawer.openDrawer(Gravity.LEFT)
        }

        spinnerRef = FirebaseDatabase.getInstance().reference.child("Sites")
        list = ArrayList()
        keys = ArrayList()

        spinnerRef.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    keys.clear()
                    list.add("Choose Site")
                    for (childsnap in snapshot.children){
                        val site: String? = childsnap.child("").key.toString()
                        val key: String? = childsnap.key
                        if (site != null){
                            list.add(site)
                        }
                        if (key !=null){
                            keys.add(key)
                        }

                        val adpater = ArrayAdapter<String>(this@ShowSitesActivity, android.R.layout.simple_list_item_1, list)

                        binding.goSiteSpinner.adapter = adpater
                        
                        binding.goSiteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                p2: Int,
                                p3: Long
                            ) {
                                if (p2 != 0){
                                    val shareadPrefrences: SharedPreferences = getSharedPreferences("User Data", MODE_PRIVATE)
                                    val editor: SharedPreferences.Editor = shareadPrefrences.edit()
                                    editor.putString("Site Name", list[p2])
                                    editor.apply()
                                    val intent:Intent = Intent(this@ShowSitesActivity, WorkDetailActivity::class.java)
                                    startActivity(intent)
                                }else{
                                    Snackbar.make(binding.root, "${list[p2]} Selected", Snackbar.LENGTH_SHORT).show()
                                }
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ShowSitesActivity, "Database Error", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }
}