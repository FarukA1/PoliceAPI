package com.example.policecrimerecords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_layout.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    var data = mutableListOf<String>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout,viewGroup,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data = data[position]
        viewHolder.itemView.tv_crime.text = data

    }

    inner class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}