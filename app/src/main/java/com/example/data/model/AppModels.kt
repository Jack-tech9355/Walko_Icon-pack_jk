package com.example.data.model

import android.graphics.drawable.Drawable

data class InstalledAppInfo(
    val label: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable? = null,
    val isThemed: Boolean = false,
    val isSelectedForRequest: Boolean = false
)

data class LauncherInfo(
    val id: String,
    val name: String,
    val packageName: String,
    val applyAction: String,
    val isInstalled: Boolean,
    val isDirectApplySupported: Boolean = true,
    val tutorialSteps: List<String> = emptyList()
)
