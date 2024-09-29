package com.example.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.search.R

internal class RecommendationAdapter(private val items: List<ListItem.RecItem>): RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    var onItemClick: ((ListItem.RecItem) -> Unit)? = null

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val icon: ImageView = itemView.findViewById(R.id.image_view)
        private val hardText: TextView = itemView.findViewById(R.id.hard_text_view)
        private val btnText: TextView = itemView.findViewById(R.id.but_text_view)

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(items[adapterPosition])
            }
        }

        fun bind(itemList: ListItem.RecItem){
            val item = itemList.offer
            if(item.id != null) {
                icon.visibility = View.VISIBLE
                icon.setImageResource(
                    when (item.id) {
                        "near_vacancies" -> R.drawable.near_vacancies
                        "level_up_resume" -> R.drawable.level_up_resume
                        else -> R.drawable.temporary_job

                    }
                )
            }

            val string = item.title.split(" ").toMutableList()

            while(string[0]==" " || string[0] == ""){
                string.removeFirst()
            }
            if(string[0][0] == ' ') {
                string[0] = string[0].substring(1, string[0].length)
            }
            hardText.text = string.joinToString(" ")
            if(item.button!=null){
                btnText.visibility = View.VISIBLE
                btnText.text = item.button?.text
                hardText.maxLines = 2
            } else {
                hardText.maxLines = 3
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card_recycler_view, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}

