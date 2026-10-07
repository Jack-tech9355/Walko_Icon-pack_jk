package com.example.data.model

data class ThemedIconItem(
    val id: String,
    val name: String,
    val packageName: String,
    val activityName: String,
    val category: String,
    val drawableResId: Int,
    val accentColor: Long,
    val tags: List<String> = emptyList(),
    val isFavorite: Boolean = false
)
