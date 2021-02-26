package com.example.mylauncher

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylauncher.adapter.AppListAdapter
import com.example.sdk.AppListProvider
import com.example.sdk.model.AppData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var appListProvider = AppListProvider
    lateinit var appListAdapter: AppListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()

        val list = appListProvider.getList(this)
        appListAdapter.differ.submitList(list)

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                val query: String = charSequence.toString().toLowerCase().trim()
                val filteredList: ArrayList<AppData> = ArrayList()

                for (i in 0 until list.size) {
                    val text: String = list.get(i).app_name.toLowerCase()
                    if (text.contains(query)) {
                        filteredList.add(list.get(i))
                    }
                }

                appListAdapter.differ.submitList(filteredList)
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        appListAdapter.setOnItemClickListener {
            val launchApp: Intent? = packageManager.getLaunchIntentForPackage(it.package_name)

            if (launchApp != null)
                startActivity(packageManager.getLaunchIntentForPackage(it.package_name))
            else
                Toast.makeText(applicationContext, "Cannot launch App", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        appListAdapter = AppListAdapter()
        rvList.apply {
            adapter = appListAdapter
            layoutManager = LinearLayoutManager(this.context)
        }
    }
}