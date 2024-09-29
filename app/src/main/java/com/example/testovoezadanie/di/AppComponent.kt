package com.example.testovoezadanie.di

import android.content.Context
import com.example.testovoezadanie.app.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, DataModule::class, DomainModule::class, PresentationModule::class, FragmentModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent

    }

    fun inject(app: App)

}