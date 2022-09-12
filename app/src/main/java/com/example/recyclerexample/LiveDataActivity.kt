package com.example.recyclerexample

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerexample.databinding.ActivityLiveDataBinding

class LiveDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveDataBinding
    private val mylivedata: MutableLiveData<List<String>> = MutableLiveData()
    private lateinit var adapter: RecyclerViewAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_data)

        mylivedata.value = listOf("the first", "the second", "the third")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter2()
        binding.recyclerView.adapter = adapter

        binding.button.text = "Create data item"
        binding.button.setOnClickListener {
            val txt = binding.userInput.text.toString()
            val newlist = (mylivedata.value ?: listOf()) + listOf(if(txt == "") "HUH" else txt)
            binding.userInput.text.clear()
            mylivedata.value = newlist
        }

        mylivedata.observe(this, {
            Log.d("ZZZ", "mylivedata.observe")
            it?.let {
                adapter.submitList(it)
            }
        })
    }
}

class RecyclerViewAdapter2: ListAdapter<String, ViewHolder2>(MyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, vt: Int): ViewHolder2 {
        Log.d("ZZZ", "onCreateViewHolder()")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.myitemlayout, parent, false)
        return ViewHolder2(itemView)
    }

    override fun onBindViewHolder(vh: ViewHolder2, pos: Int) {
        Log.d("ZZZ", "onBindViewHolder($pos)")
        vh.itemView.findViewById<TextView>(R.id.textView2).text = getItem(pos)
    }

}

class ViewHolder2(itemView: View): RecyclerView.ViewHolder(itemView)

class MyDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}