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
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_account -> {
                    findNavController(R.id.navfragment).navigate(R.id.accountFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_apply -> {
                    findNavController(R.id.navfragment).navigate(R.id.applyFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_task -> {
                    findNavController(R.id.navfragment).navigate(R.id.taskFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_more -> {
                    findNavController(R.id.navfragment).navigate(R.id.moreFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
}
