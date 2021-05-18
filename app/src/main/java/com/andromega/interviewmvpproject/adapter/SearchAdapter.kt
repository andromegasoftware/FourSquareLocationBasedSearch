package com.andromega.interviewmvpproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andromega.interviewmvpproject.modelclass.Venue
import com.andromega.interviewmvpproject.R
import kotlinx.android.synthetic.main.search_result_layout_design.view.*

class SearchAdapter(var searchList: List<Venue>, function: () -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var searchResult: List<Venue> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val myView = LayoutInflater.from(parent.context).inflate(R.layout.search_result_layout_design, parent, false)

        return SearchViewHolder(myView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as SearchViewHolder).bind(searchResult[position])

    }

    override fun getItemCount(): Int {

        return searchResult.size

    }

    fun submitList(searchList: List<Venue>){
        searchResult = searchList
    }

    class SearchViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        private val placeName: TextView = itemView.textView_place_name
        private val placeDistance: TextView = itemView.textView_place_distance
        private val placeAddress: TextView = itemView.textView_place_address

        fun bind(item: Venue){
            placeName.text = item.name
            placeDistance.text = item.location.distance.toString() + " meters"
            placeAddress.text = item.location.formattedAddress[0]

        }
    }

}