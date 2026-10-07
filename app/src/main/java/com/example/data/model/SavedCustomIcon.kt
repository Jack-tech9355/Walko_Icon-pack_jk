package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_custom_icons")
data class SavedCustomIcon(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val targetPackage: String,
    val targetActivity: String,
    val shapeMask: String, // CIRCLE, SQUIRCLE, ROUNDED_SQUARE, HEXAGON, TEARDROP, PEBBLE, DIAMOND
    val bgType: String, // SOLID, GRADIENT_NEON, GRADIENT_SUNSET, GRADIENT_CYAN, TRANSPARENT
    val bgColorHex: String,
    val borderColorHex: String,
    val borderWidthDp: Float,
    val shadowElevation: Float,
    val scalePercent: Float,
    val rotationDeg: Float,
    val imageFilePath: String? = null,
    val badgeSymbol: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
