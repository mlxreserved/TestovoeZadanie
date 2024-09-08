package com.example.data2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite (
    @PrimaryKey
    val id: String
    )