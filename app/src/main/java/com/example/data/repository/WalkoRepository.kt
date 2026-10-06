package com.example.data.repository

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.data.db.WalkoDao
import com.example.data.model.FavoriteIcon
import com.example.data.model.InstalledAppInfo
import com.example.data.model.LauncherInfo
import com.example.data.model.SavedCustomIcon
import com.example.data.model.ThemedIconItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class WalkoRepository(
    private val context: Context,
    private val dao: WalkoDao
) {
    // Curated icons with favorite status combined
    val iconsFlow: Flow<List<ThemedIconItem>> = dao.getAllFavorites().combine(
        kotlinx.coroutines.flow.flowOf(IconCatalog.allIcons)
    ) { favorites, catalogIcons ->
        val favMap = favorites.associateBy { it.iconId }
        catalogIcons.map { icon ->
            icon.copy(isFavorite = favMap.containsKey(icon.id))
        }
    }

    // Custom icons saved by user
    val customIconsFlow: Flow<List<SavedCustomIcon>> = dao.getAllCustomIcons()

    suspend fun toggleFavorite(item: ThemedIconItem) = withContext(Dispatchers.IO) {
        if (item.isFavorite) {
            dao.removeFavorite(item.id)
        } else {
            dao.addFavorite(
                FavoriteIcon(
                    iconId = item.id,
                    appName = item.name,
                    packageName = item.packageName,
                    category = item.category
                )
            )
        }
    }

    suspend fun saveCustomIcon(icon: SavedCustomIcon): Long = withContext(Dispatchers.IO) {
        dao.insertCustomIcon(icon)
    }

    suspend fun deleteCustomIcon(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteCustomIconById(id)
    }

    // Scan installed apps on device
    suspend fun getInstalledApps(): List<InstalledAppInfo> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0L))
        } else {
            @Suppress("DEPRECATION")
            pm.queryIntentActivities(intent, 0)
        }

        val themedPackages = IconCatalog.allIcons.map { it.packageName }.toSet()

        resolveInfos.mapNotNull { resolveInfo ->
            val pkg = resolveInfo.activityInfo.packageName
            val activity = resolveInfo.activityInfo.name
            val label = resolveInfo.loadLabel(pm).toString()
            val icon = resolveInfo.loadIcon(pm)
            InstalledAppInfo(
                label = label,
                packageName = pkg,
                activityName = activity,
                icon = icon,
                isThemed = themedPackages.contains(pkg)
            )
        }.distinctBy { it.packageName }.sortedBy { it.label.lowercase() }
    }

    // Detect Launcher support
    fun getSupportedLaunchers(): List<LauncherInfo> {
        val pm = context.packageManager

        fun isPkgInstalled(pkg: String): Boolean {
            return try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    pm.getPackageInfo(pkg, PackageManager.PackageInfoFlags.of(0L))
                } else {
                    @Suppress("DEPRECATION")
                    pm.getPackageInfo(pkg, 0)
                }
                true
            } catch (e: Exception) {
                false
            }
        }

        return listOf(
            LauncherInfo(
                id = "nova",
                name = "Nova Launcher",
                packageName = "com.teslacoilsw.launcher",
                applyAction = "com.teslacoilsw.launcher.APPLY_ICON_THEME",
                isInstalled = isPkgInstalled("com.teslacoilsw.launcher"),
                tutorialSteps = listOf("Open Nova Settings", "Tap Look & feel > Icon style", "Select 'Walko Icons'")
            ),
            LauncherInfo(
                id = "lawnchair",
                name = "Lawnchair",
                packageName = "ch.deletescape.lawnchair",
                applyAction = "ch.deletescape.lawnchair.APPLY_ICON_THEME",
                isInstalled = isPkgInstalled("ch.deletescape.lawnchair"),
                tutorialSteps = listOf("Long press Home screen > Home settings", "Tap General > Icon Pack", "Select 'Walko Icons'")
            ),
            LauncherInfo(
                id = "smart",
                name = "Smart Launcher 6",
                packageName = "ginlemon.flowerfree",
                applyAction = "ginlemon.flowerfree.apply",
                isInstalled = isPkgInstalled("ginlemon.flowerfree") || isPkgInstalled("com.smartlauncher.pro"),
                tutorialSteps = listOf("Open Smart Launcher settings", "Tap Global appearance > Icon appearance", "Select 'Walko Icons'")
            ),
            LauncherInfo(
                id = "niagara",
                name = "Niagara Launcher",
                packageName = "bitpit.launcher",
                applyAction = "bitpit.launcher.APPLY_ICONS",
                isInstalled = isPkgInstalled("bitpit.launcher"),
                tutorialSteps = listOf("Open Niagara settings", "Tap Look > Icon pack", "Select 'Walko Icons'")
            ),
            LauncherInfo(
                id = "hyperion",
                name = "Hyperion Launcher",
                packageName = "pranavpandey.hyperion",
                applyAction = "pranavpandey.hyperion.APPLY_ICON_THEME",
                isInstalled = isPkgInstalled("pranavpandey.hyperion"),
                tutorialSteps = listOf("Open Hyperion Settings", "Tap Iconography > Icon pack", "Select 'Walko Icons'")
            ),
            LauncherInfo(
                id = "action",
                name = "Action Launcher",
                packageName = "com.actionlauncher.playstore",
                applyAction = "com.actionlauncher.playstore.APPLY_ICON_THEME",
                isInstalled = isPkgInstalled("com.actionlauncher.playstore"),
                tutorialSteps = listOf("Open Action Settings", "Tap Appearance > Icon pack", "Select 'Walko Icons'")
            ),
            LauncherInfo(
                id = "microsoft",
                name = "Microsoft Launcher",
                packageName = "com.microsoft.launcher",
                applyAction = "com.microsoft.launcher.APPLY_ICON_THEME",
                isInstalled = isPkgInstalled("com.microsoft.launcher"),
                tutorialSteps = listOf("Open Launcher Settings > Home screen", "Tap Icon appearance > Icon pack", "Select 'Walko Icons'")
            ),
            LauncherInfo(
                id = "oneui",
                name = "Samsung One UI (Theme Park)",
                packageName = "com.samsung.android.themedesigner",
                applyAction = "",
                isInstalled = isPkgInstalled("com.samsung.android.themedesigner"),
                isDirectApplySupported = false,
                tutorialSteps = listOf(
                    "Install 'Good Lock' and 'Theme Park' from Galaxy Store",
                    "Open Theme Park > Icon tab > Create New",
                    "Tap Iconpack > Select 'Walko Icons' > Apply"
                )
            ),
            LauncherInfo(
                id = "hyperos",
                name = "Xiaomi HyperOS / MIUI",
                packageName = "com.android.thememanager",
                applyAction = "",
                isInstalled = isPkgInstalled("com.android.thememanager"),
                isDirectApplySupported = false,
                tutorialSteps = listOf(
                    "Open Themes app > Profile > Icons",
                    "Select 'Walko Icons' or use Shortcut Studio",
                    "Tap Apply"
                )
            ),
            LauncherInfo(
                id = "coloros",
                name = "OnePlus / Realme / ColorOS",
                packageName = "com.coloros.stylesheet",
                applyAction = "",
                isInstalled = false,
                isDirectApplySupported = false,
                tutorialSteps = listOf(
                    "Long press Home screen > Icons",
                    "Swipe left and select 'Walko Icons'",
                    "Tap Apply"
                )
            )
        )
    }

    // Apply icon pack to compatible launcher
    fun applyToLauncher(launcher: LauncherInfo): Boolean {
        return try {
            val intent = Intent("com.novalauncher.THEME")
            intent.setPackage(launcher.packageName)
            intent.putExtra("com.novalauncher.THEME_PACKAGE_NAME", context.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            try {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(launcher.packageName)
                if (launchIntent != null) {
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(launchIntent)
                    true
                } else false
            } catch (ex: Exception) {
                false
            }
        }
    }

    // Save bitmap to internal app storage
    suspend fun saveBitmapToFile(bitmap: Bitmap, prefix: String = "walko_icon"): String = withContext(Dispatchers.IO) {
        val fileName = "${prefix}_${UUID.randomUUID()}.png"
        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        file.absolutePath
    }

    // Native Pin Shortcut Creator (Works on all modern Android devices)
    suspend fun pinCustomShortcut(
        name: String,
        targetPackage: String,
        targetActivity: String,
        iconBitmap: Bitmap
    ): Boolean = withContext(Dispatchers.Main) {
        if (!ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            return@withContext false
        }

        // Ensure software ARGB_8888 bitmap to prevent HARDWARE bitmap crashes
        val softwareBitmap = if (iconBitmap.config != Bitmap.Config.ARGB_8888) {
            iconBitmap.copy(Bitmap.Config.ARGB_8888, false)
        } else {
            iconBitmap
        }

        val launchIntent = if (targetActivity.isNotEmpty()) {
            Intent(Intent.ACTION_MAIN).apply {
                component = ComponentName(targetPackage, targetActivity)
                addCategory(Intent.CATEGORY_LAUNCHER)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            }
        } else {
            context.packageManager.getLaunchIntentForPackage(targetPackage)?.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            } ?: Intent(Intent.ACTION_MAIN).apply {
                `package` = targetPackage
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }

        val shortcutId = "walko_${targetPackage}_${System.currentTimeMillis()}"
        val pinShortcutInfo = ShortcutInfoCompat.Builder(context, shortcutId)
            .setIcon(IconCompat.createWithBitmap(softwareBitmap))
            .setShortLabel(name)
            .setLongLabel(name)
            .setIntent(launchIntent)
            .build()

        ShortcutManagerCompat.requestPinShortcut(context, pinShortcutInfo, null)
    }
}
