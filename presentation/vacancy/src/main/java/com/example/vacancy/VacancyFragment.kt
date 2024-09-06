package com.example.vacancy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.data2.storage.model.vacancy.Vacancy
import com.example.domain.model.favorite.FavoriteVacancyDomain
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement

class VacancyFragment: Fragment() {

    interface Callbacks{
        fun onVacancyBackClick()
        fun onResponseClick(string: String?)
    }

    private lateinit var toolbar: Toolbar
    private lateinit var databaseState: StateFlow<DatabaseState>
    private lateinit var vacancyState: StateFlow<VacancyState>
    private lateinit var coordinateState: StateFlow<CoordinateState>
    private lateinit var title: TextView
    private lateinit var salary: TextView
    private lateinit var experience: TextView
    private lateinit var schedules: TextView
    private lateinit var responsed: CardView
    private lateinit var lookingNow: CardView
    private lateinit var responsedText: TextView
    private lateinit var lookingNowText: TextView
    private lateinit var company: TextView
    private lateinit var map: ImageView
    private lateinit var address: TextView
    private lateinit var description: TextView
    private lateinit var tasks: TextView
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var response: Button




    private var callbacks: Callbacks? = null

    private val vacancyViewModel: VacancyViewModel by activityViewModels{
        VacancyViewModel.Factory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseState = vacancyViewModel.databaseState
        vacancyState = vacancyViewModel.vacancyState
        coordinateState = vacancyViewModel.coordinateState
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vacancy, container, false)

        val bundle = arguments
        if(bundle!=null){
            val string: String = bundle.getString("json") ?: ""
            val vacancy = Json.decodeFromJsonElement<Vacancy>(Json.parseToJsonElement(string))
            vacancyViewModel.setVacancy(vacancy)
        }
        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        title = view.findViewById(R.id.header)
        salary = view.findViewById(R.id.salary)
        experience = view.findViewById(R.id.experience)
        schedules = view.findViewById(R.id.schedules)
        responsed = view.findViewById(R.id.card_responsed)
        lookingNow = view.findViewById(R.id.card_looking)
        responsedText = view.findViewById(R.id.responsed)
        lookingNowText = view.findViewById(R.id.looking)
        company = view.findViewById(R.id.title)
        map = view.findViewById(R.id.map)
        address = view.findViewById(R.id.address)
        description = view.findViewById(R.id.description)
        tasks = view.findViewById(R.id.tasks)
        button1 = view.findViewById(R.id.button1)
        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)
        button4 = view.findViewById(R.id.button4)
        button5 = view.findViewById(R.id.button5)
        response = view.findViewById(R.id.apply_button)



        response.setOnClickListener {
            callbacks?.onResponseClick(null)
        }

        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        activity.supportActionBar?.title = ""

        return view
    }

    private val menuHost: MenuHost get() = requireActivity()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vac = vacancyState.value.vacancy!!
        title.text = vac.title
        salary.text = vac.salary.full
        experience.text = view.context.getString(R.string.experince, vac.experience.text)
        val vacStr = vac.schedules.joinToString(", ")
        val scheduleString: List<String> = listOf(vac.schedules[0][0].uppercaseChar().toString(), vacStr.substring(1,vacStr.length))
        schedules.text = scheduleString.joinToString("")
        if(vac.appliedNumber != null){
            responsed.visibility = View.VISIBLE
            val peopleText = if((vac.appliedNumber!! % 10 == 2 || vac.appliedNumber!! % 10 == 3 || vac.appliedNumber!! %10 == 4) && vac.appliedNumber!! % 100 / 10 != 1){
                "человека"
            } else {
                "человек"
            }
            responsedText.text = view.context.getString(R.string.responsed, vac.appliedNumber, peopleText)
        } else {
            responsed.visibility = View.GONE
        }
        if(vac.lookingNumber!=null){
            lookingNow.visibility = View.VISIBLE
            val peopleText = if((vac.lookingNumber!! % 10 == 2 || vac.lookingNumber!! % 10 == 3 || vac.lookingNumber!! %10 == 4) && vac.lookingNumber!! % 100 / 10 != 1){
                "человека"
            } else {
                "человек"
            }
            lookingNowText.text = view.context.getString(R.string.looking_now, vac.lookingNumber, peopleText)
        } else {
            lookingNow.visibility = View.GONE
        }
        company.text = vac.company
        val textForAddress = listOf(vac.address.town, vac.address.street, vac.address.house).joinToString(", ")
        address.text = textForAddress
        vacancyViewModel.getCoordinates("${vac.address.town}+${vac.address.street}+${vac.address.house}")
        if(vac.description != null) {
            description.visibility = View.VISIBLE
            description.text = vac.description
        } else {
            description.visibility = View.GONE
        }
        if(vac.responsibilities != null) {
            tasks.visibility = View.VISIBLE
            tasks.text = vac.responsibilities
        } else {
            tasks.visibility = View.GONE
        }
        when(vac.questions.size) {
            1 -> {
                button1.visibility = View.VISIBLE
                button2.visibility = View.GONE
                button3.visibility = View.GONE
                button4.visibility = View.GONE
                button5.visibility = View.GONE
                button1.text = vac.questions[0]
            }
            2 -> {
                button1.visibility = View.VISIBLE
                button2.visibility = View.VISIBLE
                button3.visibility = View.GONE
                button4.visibility = View.GONE
                button5.visibility = View.GONE
                button1.text = vac.questions[0]
                button2.text = vac.questions[1]
            }
            3 -> {
                button1.visibility = View.VISIBLE
                button2.visibility = View.VISIBLE
                button3.visibility = View.VISIBLE
                button4.visibility = View.GONE
                button5.visibility = View.GONE
                button1.text = vac.questions[0]
                button2.text = vac.questions[1]
                button3.text = vac.questions[2]
            }
            4 -> {
                button1.visibility = View.VISIBLE
                button2.visibility = View.VISIBLE
                button3.visibility = View.VISIBLE
                button4.visibility = View.VISIBLE
                button5.visibility = View.GONE
                button1.text = vac.questions[0]
                button2.text = vac.questions[1]
                button3.text = vac.questions[2]
                button4.text = vac.questions[3]
            }
            5 -> {
                button1.visibility = View.VISIBLE
                button2.visibility = View.VISIBLE
                button3.visibility = View.VISIBLE
                button4.visibility = View.VISIBLE
                button5.visibility = View.VISIBLE
                button1.text = vac.questions[0]
                button2.text = vac.questions[1]
                button3.text = vac.questions[2]
                button4.text = vac.questions[3]
                button5.text = vac.questions[4]
            }
            else -> {
                button1.visibility = View.GONE
                button2.visibility = View.GONE
                button3.visibility = View.GONE
                button4.visibility = View.GONE
                button5.visibility = View.GONE
            }
        }

        button1.setOnClickListener {
            callbacks?.onResponseClick(string = button1.text.toString())
        }

        button2.setOnClickListener {
            callbacks?.onResponseClick(string = button2.text.toString())
        }

        button3.setOnClickListener {
            callbacks?.onResponseClick(string = button3.text.toString())
        }
        button4.setOnClickListener {
            callbacks?.onResponseClick(string = button4.text.toString())
        }
        button5.setOnClickListener {
            callbacks?.onResponseClick(string = button5.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                coordinateState.collect{ coord ->
                    if(coord.coordinate is Result.Success) {
                        val coordinates = coord.coordinate.data.response.GeoObjectCollection.featureMember[0].GeoObject.Point.pos
                        val request =
                            "https://static-maps.yandex.ru/v1?apikey=1af9037f-0073-4182-a85b-192e2f5261bd&ll=${coordinates}&pt=${coordinates},work&z=12&size=300,60&theme=dark"
                        map.load(request)
                    }
                }
            }
        }







        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_toolbar, menu)
                val heartActive: MenuItem = menu.findItem(R.id.favoriteActive)
                val heartInactive: MenuItem = menu.findItem(R.id.favoriteInactive)
                if(vacancyState.value.vacancy?.isFavorite == true){
                    heartActive.isVisible = true
                    heartInactive.isVisible = false
                } else {
                    heartActive.isVisible = false
                    heartInactive.isVisible = true
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                        databaseState.collect{ vacancies ->
                            if(vacancies.favorites.contains(FavoriteVacancyDomain(vacancyState.value.vacancy?.id!!))){
                                heartActive.isVisible = true
                                heartInactive.isVisible = false
                            } else {
                                heartActive.isVisible = false
                                heartInactive.isVisible = true
                            }
                        }
                    }
                }

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if(menuItem.itemId == android.R.id.home) {
                    callbacks?.onVacancyBackClick()
                    return true
                } else if(menuItem.itemId == R.id.favoriteActive){
                    vacancyViewModel.deleteFromDB(vacancyState.value.vacancy?.id!!)
                } else if(menuItem.itemId == R.id.favoriteInactive){
                    vacancyViewModel.addToDB(vacancyState.value.vacancy?.id!!)
                }
                return false
            }
        }, viewLifecycleOwner)

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun updateUi(){
    }

}