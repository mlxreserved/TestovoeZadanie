package com.example.data2.di

import android.app.Application

class MainApp: Application() {
    lateinit var networkComponent: NetworkComponent
    lateinit var databaseComponent: DatabaseComponent

    override fun onCreate() {
        super.onCreate()
        databaseComponent = initDagger(this)
        networkComponent = DaggerNetworkComponent.create()
    }
    private fun initDagger(context: MainApp): DatabaseComponent = DaggerDatabaseComponent.builder().databaseModule(DatabaseModule(context)).build()
}
