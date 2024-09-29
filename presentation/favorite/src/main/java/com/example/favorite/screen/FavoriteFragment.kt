package com.example.favorite.screen

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.favorite.FavoriteVacancyDomain
import com.example.favorite.R
import com.example.favorite.adapter.FavoriteAdapter
import com.example.favorite.adapter.ListItem
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

class  FavoriteFragment: Fragment() {


    interface Callbacks{
        fun onVacancyClick(json: String)
        fun refreshBadge(countFav: Int)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var networkState: StateFlow<NetworkState>
    private lateinit var databaseState: StateFlow<DatabaseState>
    private lateinit var lastFirstVisiblePosition: Parcelable
    private var adapter: FavoriteAdapter? = null
    private var callbacks: Callbacks? = null

    private var posToNotify = 0

    @Inject
    private lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by activityViewModels{
        favoriteViewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        networkState = favoriteViewModel.networkState
        databaseState = favoriteViewModel.databaseState

    }

    override fun onPause() {
        super.onPause()
        lastFirstVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).onSaveInstanceState()!!
    }

    override fun onResume() {
        super.onResume()
        (recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(lastFirstVisiblePosition)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)



        recyclerView = view.findViewById(R.id.favorite_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var result: Result = Result.Loading

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                networkState.collect{ res ->
                    result = res.result
                    when(result){
                        is Result.Success ->
                            updateUi(result as Result.Success)
                        is Result.Loading -> {}
                        is Result.Error -> {}
                    }

                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                databaseState.collect{ list ->
                    lastFirstVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).onSaveInstanceState()!!
                    when(result){
                        is Result.Success -> updateUi((result as Result.Success))
                        is Result.Loading -> {}
                        is Result.Error -> {}
                    }
                    callbacks?.refreshBadge(list.favorites.size)
                    (recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(lastFirstVisiblePosition)
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUi(res: Result.Success){

        val list: MutableList<ListItem> = mutableListOf(ListItem.HeaderItem(databaseState.value.favorites.size))
        for(i in 0..res.data.vacancies.size-1){
            val item = res.data.vacancies[i]
            item.isFavorite = databaseState.value.favorites.contains(FavoriteVacancyDomain(item.id))
            if(item.isFavorite) {
                list.add(ListItem.VacancyItem(item))
            }
        }
        adapter = FavoriteAdapter(list)
        recyclerView.adapter = adapter

        adapter?.onFavoriteClick = { id, pos, toDelete ->

            posToNotify = pos
            if(toDelete){
                favoriteViewModel.deleteFromDB(id)
            }

        }

        adapter?.onVacancyClick = { vacancyItem ->
            val string = Json.encodeToJsonElement(vacancyItem.vacancy).toString()
            callbacks?.onVacancyClick(string)
        }

    }
}