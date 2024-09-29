package com.example.testovoezadanie.app

import android.app.Application
import com.example.testovoezadanie.di.AppComponent
import com.example.testovoezadanie.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this@App)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}