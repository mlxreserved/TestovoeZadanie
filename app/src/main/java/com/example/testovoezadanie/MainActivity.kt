package com.example.testovoezadanie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.favorite.screen.FavoriteFragment
import com.example.login.screen.CodeFragment
import com.example.login.screen.LoginFragment
import com.example.message.screen.MessageFragment
import com.example.profile.screen.ProfileFragment
import com.example.response.screen.ResponseFragment
import com.example.search.screen.MoreFragment
import com.example.search.screen.SearchFragment
import com.example.vacancy.screen.BottomSheetFragment
import com.example.vacancy.screen.VacancyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : AppCompatActivity(), LoginFragment.Callbacks,
    CodeFragment.Callbacks, SearchFragment.Callbacks,
    MoreFragment.Callbacks, VacancyFragment.Callbacks,
    FavoriteFragment.Callbacks{

    private lateinit var bottomNavBar: BottomNavigationView

    private fun badgeSetup(id: Int, alerts: Int){
        val badge = bottomNavBar.getOrCreateBadge(id)
        badge.isVisible = true
        badge.backgroundColor = this.getColor(R.color.red)
        badge.badgeTextColor = this.getColor(R.color.white)
        if(alerts<100) {
            badge.number = alerts
        } else {
            badge.text = "99+"
        }
    }
    private fun badgeClear(id: Int){
        val badgeDrawable = bottomNavBar.getBadge(id)
        if( badgeDrawable!= null){
            badgeDrawable.isVisible = false
            badgeDrawable.clearNumber()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            bottomNavBar.layoutParams.height = systemBars.bottom+195
            insets
        }

        bottomNavBar = findViewById(R.id.bottomNavigationView)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        window.statusBarColor = this.getColor(R.color.black)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
        }
        if(currentFragment == null){
            val fragment = LoginFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment, LoginFragment::class.java.name)
                .commit()
        }




        bottomNavBar.setOnItemSelectedListener {item->
            val frag = supportFragmentManager.findFragmentByTag(LoginFragment::class.java.name) == null
                    && supportFragmentManager.findFragmentByTag(CodeFragment::class.java.name) == null
            if(frag){
                val curFragment: Fragment = when(supportFragmentManager.fragments[supportFragmentManager.fragments.size-1]){
                    is SearchFragment -> SearchFragment()
                    is MoreFragment -> MoreFragment()
                    is VacancyFragment -> VacancyFragment()
                    is FavoriteFragment -> FavoriteFragment()
                    is ResponseFragment -> ResponseFragment()
                    is MessageFragment -> MessageFragment()
                    else -> ProfileFragment()
                }

                when(item.itemId){
                    R.id.search->{
                        if(curFragment::class.java.name!= SearchFragment::class.java.name) {
                            val fragList = supportFragmentManager.fragments
                            for(fragment in fragList){
                                if(fragment::class.java.name != SearchFragment::class.java.name){
                                    supportFragmentManager.beginTransaction().remove(fragment).commit()
                                }
                            }
//                            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        }
                        if(supportFragmentManager.findFragmentByTag(SearchFragment::class.java.name)==null){
                            supportFragmentManager.beginTransaction()
                                .add(R.id.fragment_container,
                                    SearchFragment(), SearchFragment::class.java.name)
                                .commit()
                        }
                    }
                    R.id.favorite->{
                        if(curFragment::class.java.name != FavoriteFragment::class.java.name){
                            val fragList = supportFragmentManager.fragments
                            for(fragment in fragList){
                                if(fragment::class.java.name != FavoriteFragment::class.java.name){
                                    supportFragmentManager.beginTransaction().remove(fragment).commit()
                                    supportFragmentManager.popBackStack()
                                }
                            }
                            val fragmentToAdd = FavoriteFragment()
                            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragmentToAdd, fragmentToAdd::class.java.name).commit()
                        }
                    }
                    R.id.responses->{
                        if(curFragment::class.java.name != ResponseFragment::class.java.name){
                            val fragList = supportFragmentManager.fragments
                            for(fragment in fragList){
                                if(fragment::class.java.name != ResponseFragment::class.java.name){
                                    supportFragmentManager.beginTransaction().remove(fragment).commit()
                                    supportFragmentManager.popBackStack()
                                }
                            }
                            val fragmentToAdd = ResponseFragment()
                            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragmentToAdd, fragmentToAdd::class.java.name).commit()
                        }
                    }
                    R.id.messages->{
                        if(curFragment::class.java.name != MessageFragment::class.java.name){
                            val fragList = supportFragmentManager.fragments
                            for(fragment in fragList){
                                if(fragment::class.java.name != MessageFragment::class.java.name){
                                    supportFragmentManager.beginTransaction().remove(fragment).commit()
                                    supportFragmentManager.popBackStack()
                                }
                            }
                            val fragmentToAdd = MessageFragment()
                            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragmentToAdd, fragmentToAdd::class.java.name).commit()
                        }
                    }
                    else-> {
                        if(curFragment::class.java.name != ProfileFragment::class.java.name){
                            val fragList = supportFragmentManager.fragments
                            for(fragment in fragList){
                                if(fragment::class.java.name != ProfileFragment::class.java.name){
                                    supportFragmentManager.beginTransaction().remove(fragment).commit()
                                    supportFragmentManager.popBackStack()
                                }
                            }
                            val fragmentToAdd = ProfileFragment()
                            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragmentToAdd, fragmentToAdd::class.java.name).commit()
                        }
                    }
                }
            }
            true
        }
    }

    override fun onNextClick() {
        val fragment = CodeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, CodeFragment::class.java.name)
            .addToBackStack(LoginFragment::class.java.name)
            .commit()
    }

    override fun onApplyClick() {
        supportFragmentManager.popBackStack()
        val fragment = SearchFragment()
        bottomNavBar.selectedItemId = R.id.search
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, SearchFragment::class.java.name).commit()
    }


    override fun onButtonPressed() {
        val fragment = MoreFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment, MoreFragment::class.java.name)
            .addToBackStack(SearchFragment::class.java.name)
            .commit()

    }

    override fun onVacancyClick(json: String) {
        val fragment = VacancyFragment()
        val bundle = Bundle()
        val curFragment: Fragment =
            when (supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1]) {
                is SearchFragment -> SearchFragment()
                is MoreFragment -> MoreFragment()
                else -> SearchFragment()
            }
        bundle.putString("json", json)
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment, fragment::class.java.name)
            .addToBackStack(curFragment::class.java.name)
            .commit()
    }

    override fun onBackButtonPressed() {
        supportFragmentManager
            .popBackStack()
    }

    override fun onVacancyBackClick() {
        supportFragmentManager
            .popBackStack()

    }

    override fun onResponseClick(string: String?) {
        val bottomSheetFragment = BottomSheetFragment()
        if(string!=null) {
            val bundle = Bundle()
            bundle.putString("string", string)
            bottomSheetFragment.arguments = bundle
        }
        bottomSheetFragment.show(supportFragmentManager, "bottomSheetFragment")
    }

    override fun refreshBadge(countFav: Int) {
        if(countFav!=0) {
            badgeSetup(R.id.favorite, countFav)
        } else {
            badgeClear(R.id.favorite)
        }
    }
}