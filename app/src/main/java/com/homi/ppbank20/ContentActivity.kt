package com.homi.ppbank20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.fragment_account.*

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)


    }

    override fun onStart() {
        super.onStart()
        val navController=findNavController(R.id.navfragment)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_account -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_apply -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_task -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_more -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
}
