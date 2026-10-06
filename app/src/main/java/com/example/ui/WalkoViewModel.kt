package com.example.ui

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.WalkoDatabase
import com.example.data.model.InstalledAppInfo
import com.example.data.model.LauncherInfo
import com.example.data.model.SavedCustomIcon
import com.example.data.model.ThemedIconItem
import com.example.data.repository.IconCatalog
import com.example.data.repository.WalkoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class WalkoTab {
    HOME,
    STUDIO,
    APPLY,
    REQUEST,
    MOCKUP,
    ABOUT
}

enum class IconShapeMask(val displayName: String) {
    CIRCLE("Circle"),
    SQUIRCLE("Squircle"),
    ROUNDED_SQUARE("Rounded"),
    HEXAGON("Hexagon"),
    TEARDROP("Tear Drop"),
    PEBBLE("Pebble"),
    DIAMOND("Diamond")
}

enum class BackgroundPreset(val displayName: String) {
    SOLID_AMOLED("AMOLED Solid"),
    GRADIENT_CYAN_VIOLET("Cyber Neon"),
    GRADIENT_SUNSET("Sunset Fire"),
    GRADIENT_EMERALD("Emerald Glow"),
    GLASS_DARK("Dark Glass"),
    TRANSPARENT("Transparent")
}

data class UiNotification(
    val message: String,
    val isError: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

class WalkoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = WalkoDatabase.getDatabase(application)
    private val repository = WalkoRepository(application, db.walkoDao())

    // Active Tab
    private val _currentTab = MutableStateFlow(WalkoTab.HOME)
    val currentTab: StateFlow<WalkoTab> = _currentTab.asStateFlow()

    // Notification toast
    private val _notification = MutableStateFlow<UiNotification?>(null)
    val notification: StateFlow<UiNotification?> = _notification.asStateFlow()

    fun dismissNotification() {
        _notification.value = null
    }

    fun showMessage(msg: String, isError: Boolean = false) {
        _notification.value = UiNotification(msg, isError)
    }

    fun selectTab(tab: WalkoTab) {
        _currentTab.value = tab
    }

    // --- HOME SCREEN STATE ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _showOnlyFavorites = MutableStateFlow(false)
    val showOnlyFavorites: StateFlow<Boolean> = _showOnlyFavorites.asStateFlow()

    private val _selectedIconDetail = MutableStateFlow<ThemedIconItem?>(null)
    val selectedIconDetail: StateFlow<ThemedIconItem?> = _selectedIconDetail.asStateFlow()

    // Background test preview in detail sheet (0: Amoled, 1: Glass Light, 2: Neon)
    private val _detailBgMode = MutableStateFlow(0)
    val detailBgMode: StateFlow<Int> = _detailBgMode.asStateFlow()

    val filteredIcons: StateFlow<List<ThemedIconItem>> = combine(
        repository.iconsFlow,
        _searchQuery,
        _selectedCategory,
        _showOnlyFavorites
    ) { icons, query, category, onlyFavs ->
        icons.filter { icon ->
            val matchesQuery = query.isBlank() ||
                    icon.name.contains(query, ignoreCase = true) ||
                    icon.packageName.contains(query, ignoreCase = true) ||
                    icon.tags.any { it.contains(query, ignoreCase = true) }
            val matchesCategory = (category == "All") || (icon.category == category)
            val matchesFav = !onlyFavs || icon.isFavorite
            matchesQuery && matchesCategory && matchesFav
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), IconCatalog.allIcons)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun toggleFavoritesFilter() {
        _showOnlyFavorites.value = !_showOnlyFavorites.value
    }

    fun openIconDetail(item: ThemedIconItem) {
        _selectedIconDetail.value = item
    }

    fun closeIconDetail() {
        _selectedIconDetail.value = null
    }

    fun cycleDetailBgMode() {
        _detailBgMode.value = (_detailBgMode.value + 1) % 3
    }

    fun toggleFavorite(item: ThemedIconItem) {
        viewModelScope.launch {
            repository.toggleFavorite(item)
            _selectedIconDetail.value?.let { current ->
                if (current.id == item.id) {
                    _selectedIconDetail.value = current.copy(isFavorite = !current.isFavorite)
                }
            }
        }
    }

    // --- DIY STUDIO STATE ---
    private val _installedApps = MutableStateFlow<List<InstalledAppInfo>>(emptyList())
    val installedApps: StateFlow<List<InstalledAppInfo>> = _installedApps.asStateFlow()

    private val _studioSubTab = MutableStateFlow(0) // 0: Creator, 1: My Creations
    val studioSubTab: StateFlow<Int> = _studioSubTab.asStateFlow()

    private val _customIconName = MutableStateFlow("My Custom App")
    val customIconName: StateFlow<String> = _customIconName.asStateFlow()

    private val _selectedTargetApp = MutableStateFlow<InstalledAppInfo?>(null)
    val selectedTargetApp: StateFlow<InstalledAppInfo?> = _selectedTargetApp.asStateFlow()

    private val _pickedImageUri = MutableStateFlow<Uri?>(null)
    val pickedImageUri: StateFlow<Uri?> = _pickedImageUri.asStateFlow()

    private val _pickedBitmap = MutableStateFlow<Bitmap?>(null)
    val pickedBitmap: StateFlow<Bitmap?> = _pickedBitmap.asStateFlow()

    private val _selectedShapeMask = MutableStateFlow(IconShapeMask.SQUIRCLE)
    val selectedShapeMask: StateFlow<IconShapeMask> = _selectedShapeMask.asStateFlow()

    private val _selectedBgPreset = MutableStateFlow(BackgroundPreset.SOLID_AMOLED)
    val selectedBgPreset: StateFlow<BackgroundPreset> = _selectedBgPreset.asStateFlow()

    private val _borderWidth = MutableStateFlow(2f) // dp
    val borderWidth: StateFlow<Float> = _borderWidth.asStateFlow()

    private val _borderColorHex = MutableStateFlow("#00E5FF")
    val borderColorHex: StateFlow<String> = _borderColorHex.asStateFlow()

    private val _scalePercent = MutableStateFlow(100f) // 50 - 200
    val scalePercent: StateFlow<Float> = _scalePercent.asStateFlow()

    private val _rotationDeg = MutableStateFlow(0f) // -180 - 180
    val rotationDeg: StateFlow<Float> = _rotationDeg.asStateFlow()

    private val _shadowElevation = MutableStateFlow(4f)
    val shadowElevation: StateFlow<Float> = _shadowElevation.asStateFlow()

    private val _selectedBadge = MutableStateFlow<String?>("⚡")
    val selectedBadge: StateFlow<String?> = _selectedBadge.asStateFlow()

    val savedCreations: StateFlow<List<SavedCustomIcon>> = repository.customIconsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadInstalledApps()
    }

    fun loadInstalledApps() {
        viewModelScope.launch {
            val apps = repository.getInstalledApps()
            _installedApps.value = apps
            if (_selectedTargetApp.value == null && apps.isNotEmpty()) {
                _selectedTargetApp.value = apps.first()
                _customIconName.value = apps.first().label
            }
        }
    }

    fun setStudioSubTab(tab: Int) {
        _studioSubTab.value = tab
    }

    fun setCustomIconName(name: String) {
        _customIconName.value = name
    }

    fun selectTargetApp(app: InstalledAppInfo) {
        _selectedTargetApp.value = app
        _customIconName.value = app.label
    }

    fun setPickedImageUri(uri: Uri?) {
        _pickedImageUri.value = uri
        if (uri != null) {
            viewModelScope.launch {
                try {
                    val context = getApplication<Application>()
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        val bmp = BitmapFactory.decodeStream(stream)
                        _pickedBitmap.value = bmp
                    }
                } catch (e: Exception) {
                    showMessage("Error reading image: ${e.message}", true)
                }
            }
        } else {
            _pickedBitmap.value = null
        }
    }

    fun setShapeMask(mask: IconShapeMask) {
        _selectedShapeMask.value = mask
    }

    fun setBgPreset(preset: BackgroundPreset) {
        _selectedBgPreset.value = preset
    }

    fun setBorderWidth(w: Float) {
        _borderWidth.value = w
    }

    fun setBorderColorHex(hex: String) {
        _borderColorHex.value = hex
    }

    fun setScalePercent(scale: Float) {
        _scalePercent.value = scale
    }

    fun setRotationDeg(deg: Float) {
        _rotationDeg.value = deg
    }

    fun setShadowElevation(elev: Float) {
        _shadowElevation.value = elev
    }

    fun setBadge(badge: String?) {
        _selectedBadge.value = badge
    }

    fun saveCurrentCreation(renderedBitmap: Bitmap) {
        val app = _selectedTargetApp.value
        val pkg = app?.packageName ?: "com.custom.app"
        val act = app?.activityName ?: ""
        viewModelScope.launch {
            try {
                val filePath = repository.saveBitmapToFile(renderedBitmap, "creation")
                val item = SavedCustomIcon(
                    name = _customIconName.value,
                    targetPackage = pkg,
                    targetActivity = act,
                    shapeMask = _selectedShapeMask.value.name,
                    bgType = _selectedBgPreset.value.name,
                    bgColorHex = "#07090E",
                    borderColorHex = _borderColorHex.value,
                    borderWidthDp = _borderWidth.value,
                    shadowElevation = _shadowElevation.value,
                    scalePercent = _scalePercent.value,
                    rotationDeg = _rotationDeg.value,
                    imageFilePath = filePath,
                    badgeSymbol = _selectedBadge.value
                )
                repository.saveCustomIcon(item)
                showMessage("Saved '${_customIconName.value}' to My Creations!")
            } catch (e: Exception) {
                showMessage("Failed to save: ${e.message}", true)
            }
        }
    }

    fun deleteCreation(id: Long) {
        viewModelScope.launch {
            repository.deleteCustomIcon(id)
            showMessage("Deleted from My Creations")
        }
    }

    fun pinShortcutToHomeScreen(renderedBitmap: Bitmap) {
        val app = _selectedTargetApp.value
        val pkg = app?.packageName ?: "com.custom.app"
        val act = app?.activityName ?: ""
        val name = _customIconName.value

        viewModelScope.launch {
            try {
                val success = repository.pinCustomShortcut(name, pkg, act, renderedBitmap)
                if (success) {
                    showMessage("Shortcut requested! Check your Home screen.")
                } else {
                    showMessage("Launcher did not allow pin shortcut. Try Lawnchair/Nova.", true)
                }
            } catch (e: Exception) {
                showMessage("Pin shortcut error: ${e.message}", true)
            }
        }
    }

    // --- LAUNCHER APPLIER STATE ---
    private val _launchers = MutableStateFlow<List<LauncherInfo>>(emptyList())
    val launchers: StateFlow<List<LauncherInfo>> = _launchers.asStateFlow()

    private val _selectedLauncherTutorial = MutableStateFlow<LauncherInfo?>(null)
    val selectedLauncherTutorial: StateFlow<LauncherInfo?> = _selectedLauncherTutorial.asStateFlow()

    fun loadLaunchers() {
        _launchers.value = repository.getSupportedLaunchers()
    }

    fun applyLauncher(launcher: LauncherInfo) {
        val ok = repository.applyToLauncher(launcher)
        if (ok) {
            showMessage("Launched ${launcher.name} apply screen!")
        } else {
            _selectedLauncherTutorial.value = launcher
        }
    }

    fun openLauncherTutorial(launcher: LauncherInfo) {
        _selectedLauncherTutorial.value = launcher
    }

    fun closeLauncherTutorial() {
        _selectedLauncherTutorial.value = null
    }

    // --- SMART ICON REQUEST STATE ---
    private val _requestSearchQuery = MutableStateFlow("")
    val requestSearchQuery: StateFlow<String> = _requestSearchQuery.asStateFlow()

    private val _selectedRequestApps = MutableStateFlow<Set<String>>(emptySet())
    val selectedRequestApps: StateFlow<Set<String>> = _selectedRequestApps.asStateFlow()

    val unthemedApps: StateFlow<List<InstalledAppInfo>> = combine(
        _installedApps,
        _requestSearchQuery
    ) { apps, query ->
        apps.filter { !it.isThemed }
            .filter { query.isBlank() || it.label.contains(query, ignoreCase = true) || it.packageName.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setRequestSearchQuery(q: String) {
        _requestSearchQuery.value = q
    }

    fun toggleAppForRequest(packageName: String) {
        val current = _selectedRequestApps.value.toMutableSet()
        if (current.contains(packageName)) {
            current.remove(packageName)
        } else {
            current.add(packageName)
        }
        _selectedRequestApps.value = current
    }

    fun selectAllUnthemedApps() {
        val all = unthemedApps.value.map { it.packageName }.toSet()
        _selectedRequestApps.value = all
    }

    fun clearSelectedRequestApps() {
        _selectedRequestApps.value = emptySet()
    }

    // --- MOCKUP SCREEN STATE ---
    enum class MockupWallpaper(val displayName: String, val bgGradientColors: List<Long>) {
        AMOLED_MIDNIGHT("AMOLED Dark", listOf(0xFF07090E, 0xFF0E131F, 0xFF000000)),
        CYBER_NEON("Cyberpunk Neon", listOf(0xFF0A0724, 0xFF1F0D45, 0xFF002233)),
        DEEP_SPACE("Deep Space", listOf(0xFF050510, 0xFF140D2B, 0xFF041226)),
        SUNSET_PEAKS("Sunset Fire", listOf(0xFF2C0B1E, 0xFF4A1525, 0xFF1A0A26)),
        PASTEL_DUNES("Pastel Glass", listOf(0xFF1A2634, 0xFF243348, 0xFF121B27))
    }

    private val _selectedWallpaper = MutableStateFlow(MockupWallpaper.AMOLED_MIDNIGHT)
    val selectedWallpaper: StateFlow<MockupWallpaper> = _selectedWallpaper.asStateFlow()

    fun selectWallpaper(wp: MockupWallpaper) {
        _selectedWallpaper.value = wp
    }
}
