package com.example.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data2.storage.model.vacancy.Vacancy
import com.example.domain.model.vacancy.VacancyDomain
import java.text.SimpleDateFormat
import java.util.Locale

internal class FavoriteAdapter(private val items: List<ListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onFavoriteClick:((String, Int, Boolean) -> Unit)? = null
    var onVacancyClick: ((ListItem.VacancyItem) -> Unit)? = null

    inner class VacancyHolder(view: View): RecyclerView.ViewHolder(view){
        private val lookingNow: TextView = itemView.findViewById(R.id.now_looking1)
        private val title: TextView = itemView.findViewById(R.id.title1)
        private val town: TextView = itemView.findViewById(R.id.town1)
        private val nameCompany: TextView = itemView.findViewById(R.id.name_company1)
        private val experience: TextView = itemView.findViewById(R.id.experience1)
        private val posted: TextView = itemView.findViewById(R.id.posted1)
        private val favoriteInactive: ImageView = itemView.findViewById(R.id.favorite_inactive1)
        private val favoriteActive: ImageView = itemView.findViewById(R.id.favorite_active1)

        init {
            itemView.setOnClickListener {
                onVacancyClick?.invoke(items[adapterPosition] as ListItem.VacancyItem)
            }
        }

        fun bind(item: ListItem.VacancyItem){
            val count = item.vacancy.lookingNumber
            lookingNow.visibility = View.GONE
            if(count!=null){
                lookingNow.visibility = View.VISIBLE

                val peopleText = if((count % 10 == 2 || count % 10 == 3 || count%10 == 4) && count % 100 / 10 != 1){
                    "человека"
                } else {
                    "человек"
                }
                lookingNow.text = itemView.context.getString(R.string.now_looking, count, peopleText)
            }
            title.text = item.vacancy.title
            town.text = item.vacancy.address.town
            nameCompany.text = item.vacancy.company
            experience.text = item.vacancy.experience.previewText
            posted.text = itemView.context.getString(R.string.published,formatDate(date = item.vacancy.publishedDate))
            if(item.vacancy.isFavorite){
                favoriteActive.visibility = View.VISIBLE
                favoriteInactive.visibility = View.GONE
                favoriteActive.setOnClickListener {
                    item.vacancy.isFavorite = false
                    onFavoriteClick?.invoke(item.vacancy.id, adapterPosition, true)
                }
                favoriteInactive.setOnClickListener {
                    item.vacancy.isFavorite = true
                    onFavoriteClick?.invoke(item.vacancy.id, adapterPosition, false)
                }
            } else {
                favoriteActive.visibility = View.GONE
                favoriteInactive.visibility = View.VISIBLE
                favoriteActive.setOnClickListener {
                    item.vacancy.isFavorite = false
                    onFavoriteClick?.invoke(item.vacancy.id, adapterPosition, true)
                }
                favoriteInactive.setOnClickListener {
                    item.vacancy.isFavorite = true
                    onFavoriteClick?.invoke(item.vacancy.id, adapterPosition, false)
                }
            }

        }
    }

    inner class HeaderHolder(view: View): RecyclerView.ViewHolder(view){
       private val textView: TextView = itemView.findViewById(R.id.favorite_vacancies)
        fun bind(item: ListItem.HeaderItem){
            val text = if(item.count%10 == 1 && item.count%100/10 != 1){
                "вакансия"
            } else if((item.count%10 == 2 || item.count%10 == 3 || item.count%10 == 4) && item.count%100/10 != 1){
                "вакансии"
            } else{
                "вакансий"
            }
            textView.text = itemView.context.getString(R.string.vacancies, item.count, text)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is ListItem.HeaderItem ->0
            is ListItem.VacancyItem ->1
        }
    }

    fun formatDate(date: String): String{

        val dateFormatter = if(date.substring(date.length-2, date.length).toInt()<10){
            SimpleDateFormat("d MMMM", Locale("ru"))
        } else {
            SimpleDateFormat("dd MMMM", Locale("ru"))
        }

        val formatedDate = dateFormatter.format(SimpleDateFormat("yyyy-MM-dd").parse(date))

        return formatedDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0->HeaderHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.text, parent, false)
            )
            else ->VacancyHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.card_of_vacancy, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = items[position]){
            is ListItem.HeaderItem -> (holder as HeaderHolder).bind(item)
            is ListItem.VacancyItem -> (holder as VacancyHolder).bind(item)
        }
    }
}

internal sealed class ListItem{
    data class HeaderItem(val count: Int): ListItem()
    data class VacancyItem(val vacancy: VacancyDomain): ListItem()
}