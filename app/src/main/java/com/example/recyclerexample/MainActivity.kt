package com.example.recyclerexample

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mydata = (100..140).map { it.toString() } // data to be shown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = MyRecyclerViewAdapter(this, mydata) // hook recyclerView with data

        binding.button.text = "to livedata example" // for moving to live data example
        binding.button.setOnClickListener {
            val i = Intent(this, LiveDataActivity::class.java)
            startActivity(i)
        }
    }
}

class MyRecyclerViewAdapter(private val context: Context, private val mydata: List<String>):
        RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(vg: ViewGroup, vt: Int): MyViewHolder {
        Log.d("ZZZ", "onCreateViewHolder()")
        // inflate creates layout including the widget objects in the layout
        val itemView = LayoutInflater.from(context).inflate(R.layout.myitemlayout, vg, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mydata.size
    }

    // populate the view, in this case one-to-one mapping between list elements and recyclerView positions
    override fun onBindViewHolder(vh: MyViewHolder, pos: Int) {
        Log.d("ZZZ", "onBindViewHolder($pos)")
        vh.itemView.findViewById<TextView>(R.id.textView2).text = mydata[ pos ]
    }
}

// type for the views
class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
