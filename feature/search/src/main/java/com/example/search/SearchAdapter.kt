package com.example.search

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vacancies.api.model.vacancy.Offer
import com.example.vacancies.api.model.vacancy.Vacancy
import java.text.SimpleDateFormat
import java.util.Locale


internal class SearchAdapter(private val items: List<ListItem>, private val secondRecItems: List<ListItem.RecItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val scrollStates = mutableMapOf<Int, Parcelable?>()
    var onItemClick: ((ListItem.RecItem) -> Unit)? = null
    var onButtonClick: (() -> Unit)? = null
    var onBackClick: (() -> Unit)? = null
    var onFavoriteClick:((String, Int, Boolean) -> Unit)? = null
    var onVacancyClick: ((ListItem.VacancyItem) -> Unit)? = null

    private val viewPool = RecyclerView.RecycledViewPool()

     inner class RecomHolder(view: View): RecyclerView.ViewHolder(view){
        val recyclerView: RecyclerView = itemView.findViewById(R.id.sub_recycler_view)

        fun bind(childLayoutManager: LinearLayoutManager){
            if(secondRecItems!=null) {
                recyclerView.layoutManager = childLayoutManager
                val adapter = RecommendationAdapter(secondRecItems)
                recyclerView.adapter = adapter
                adapter.onItemClick = onItemClick

                if (scrollStates.isEmpty()) {
                    recyclerView.addItemDecoration(RecomRecyclerDecoration())
                }
                recyclerView.setRecycledViewPool(viewPool)
            }
        }
    }

     inner class SearchHolder(view: View): RecyclerView.ViewHolder(view){
        private val editText = itemView.findViewById(R.id.editText) as EditText
        private val btn = itemView.findViewById(R.id.btn_back) as Button
        fun bind() {
            if(items.contains(ListItem.SortItem(items.size-2))) {
                var drawable: Drawable = itemView.context.getDrawable(R.drawable.search)!!
                drawable = DrawableCompat.wrap(drawable)
                DrawableCompat.setTint(drawable, itemView.context.getColor(R.color.grey2))
                DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                editText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                editText.isFocusable = false
                btn.visibility=View.VISIBLE
                btn.setOnClickListener {
                    onBackClick?.invoke()
                }
            } else {
                var drawable: Drawable = itemView.context.getDrawable(R.drawable.search)!!
                drawable = DrawableCompat.wrap(drawable)
                DrawableCompat.setTint(drawable, itemView.context.getColor(R.color.grey4))
                DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                editText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                editText.isFocusable = false
                btn.visibility=View.GONE
            }
        }

    }

     inner class VacancyHolder(view: View): RecyclerView.ViewHolder(view){
         private val lookingNow: TextView = itemView.findViewById(R.id.now_looking)
         private val title: TextView = itemView.findViewById(R.id.title)
         private val town: TextView = itemView.findViewById(R.id.town)
         private val nameCompany: TextView = itemView.findViewById(R.id.name_company)
         private val experience: TextView = itemView.findViewById(R.id.experience)
         private val posted: TextView = itemView.findViewById(R.id.posted)
         private val favoriteInactive: ImageView = itemView.findViewById(R.id.favorite_inactive)
         private val favoriteActive: ImageView = itemView.findViewById(R.id.favorite_active)

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

     inner class StringHolder(view: View): RecyclerView.ViewHolder(view){
        private val string: TextView = itemView.findViewById(R.id.text_string)
        fun bind(item: ListItem.StringItem){
            string.text = item.string
        }
    }

     inner class ButtonHolder(view: View): RecyclerView.ViewHolder(view){
        private val more: Button = itemView.findViewById(R.id.more)
        fun bind(item: ListItem.ButtonItem){
            val text = if(item.count%10 == 1 && item.count%100/10 != 1){
                "вакансия"
            } else if((item.count%10 == 2 || item.count%10 == 3 || item.count%10 == 4) && item.count%100/10 != 1){
                "вакансии"
            } else{
                "вакансий"
            }
            more.text = itemView.context.getString(R.string.more, item.count, text)

            more.setOnClickListener {
                onButtonClick?.invoke()
            }
        }
    }

     inner class SortHolder(view: View): RecyclerView.ViewHolder(view){
        private val vacancies: TextView = itemView.findViewById(R.id.vacancies)
        fun bind(item: ListItem.SortItem){
            val text = if(item.count%10 == 1 && item.count%100/10 != 1){
                "вакансия"
            } else if((item.count%10 == 2 || item.count%10 == 3 || item.count%10 == 4) && item.count%100/10 != 1){
                "вакансии"
            } else{
                "вакансий"
            }
            vacancies.text = itemView.context.getString(R.string.vacancies, item.count, text)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is ListItem.RecItem->0
            is ListItem.StringItem->1
            is ListItem.VacancyItem->2
            is ListItem.SearchItem->3
            is ListItem.ButtonItem->4
            is ListItem.SortItem -> 5
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> RecomHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_rec_recycler_view, parent, false)
            )
            1 -> StringHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_string, parent, false)
            )
            2 -> VacancyHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_vacancy, parent, false))
            3 -> SearchHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false))
            4-> ButtonHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false))
            else -> SortHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sort, parent, false))
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        val key = holder.layoutPosition
        when(holder.itemViewType){
            0 -> {
                scrollStates[key] = (holder as RecomHolder).recyclerView.layoutManager?.onSaveInstanceState()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = items[position]){
            is ListItem.RecItem -> {
                if(secondRecItems!=null) {
                    val key = holder.layoutPosition
                    val state = scrollStates[key]
                    val childLayoutManager = LinearLayoutManager(
                        (holder as RecomHolder).recyclerView.context, RecyclerView.HORIZONTAL, false
                    )

                    childLayoutManager.initialPrefetchItemCount = secondRecItems.size
                    if (state != null) {
                        childLayoutManager.onRestoreInstanceState(state)
                    } else {
                        childLayoutManager.scrollToPosition(0)
                    }

                    holder.bind(childLayoutManager)
                }
            }
            is ListItem.StringItem -> (holder as StringHolder).bind(item)
            is ListItem.VacancyItem -> (holder as VacancyHolder).bind(item)
            is ListItem.SearchItem -> (holder as SearchHolder).bind()
            is ListItem.ButtonItem -> (holder as ButtonHolder).bind(item)
            is ListItem.SortItem -> (holder as SortHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size


    fun formatDate(date: String): String{

        val dateFormatter = if(date.substring(date.length-2, date.length).toInt()<10){
            SimpleDateFormat("d MMMM", Locale("ru"))
        } else {
            SimpleDateFormat("dd MMMM", Locale("ru"))
        }

        val formatedDate = dateFormatter.format(SimpleDateFormat("yyyy-MM-dd").parse(date))

        return formatedDate
    }

}

internal sealed class ListItem{
    data class RecItem(val offer: Offer): ListItem()
    data class StringItem(val string: String): ListItem()
    data class VacancyItem(val vacancy: Vacancy): ListItem()
    data class ButtonItem(val count: Int): ListItem()
    data class SortItem(val count: Int): ListItem()
    object SearchItem: ListItem()
}