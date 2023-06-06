package com.capstone.posturku.ui.history.temp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.capstone.posturku.R
import com.capstone.posturku.adapter.NewsAdapter

//class SingerAdapter(private val layoutInflater: LayoutInflater,
//                    private val singerList: List<Singer>,
//                    @param:LayoutRes private val rowLayout: Int) : RecyclerView.Adapter<SingerAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val v = layoutInflater.inflate(rowLayout,
//            parent,
//            false)
//        return ViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val singer = singerList[position]
//        holder.fullName.text = singer.name
//    }
//
//    override fun getItemCount(): Int = singerList.size
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val fullName: TextView = view.findViewById<View>(R.id.full_name_tv) as TextView
//
//    }
//}

class SingerAdapter2( private val singerList: List<Singer>): RecyclerView.Adapter<SingerAdapter2.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerAdapter2.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = singerList.size

    override fun onBindViewHolder(holder: SingerAdapter2.ViewHolder, position: Int) {
        val singer = singerList[position]
        holder.fullName.text = singer.name
        holder.time.text = "Start: ${singer.start}"
        holder.durationGood.text = "Good: ${singer.durationGood} Minutes"
        holder.durationBad.text = "Bad: ${singer.durationBad} Minutes"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById<View>(R.id.tv_date) as TextView
        val fullName: TextView = view.findViewById<View>(R.id.tv_name) as TextView
        val durationGood: TextView = view.findViewById<View>(R.id.tv_durationGood) as TextView
        val durationBad: TextView = view.findViewById<View>(R.id.tv_durationBad) as TextView
    }
}