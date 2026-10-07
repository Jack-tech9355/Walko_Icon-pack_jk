package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_icons")
data class FavoriteIcon(
    @PrimaryKey
    val iconId: String,
    val appName: String,
    val packageName: String,
    val category: String,
    val addedAt: Long = System.currentTimeMillis()
)
