# 🚀 MASTER SPECIFICATION & INSTRUCTION PROMPT: "Walko Icons - Ultra 4K Icon Pack & DIY Custom Icon Studio"

Build a complete, standalone, production-ready Android application using Kotlin and Jetpack Compose named "Walko Icons".
This app is a premium Icon Pack & Custom Icon Studio with 1-Click Launcher Apply, an interactive DIY Custom App Icon Creator, and zero-lag 60fps performance.

---

### 📱 1. APP BRANDING & DEVELOPER METADATA
- **App Name:** Walko Icons (or Jack Icons)
- **Tagline:** Ultra-HD Custom Icon Studio & Launcher Themer
- **Developer Credit (MANDATORY IN ABOUT SECTION):**
  - Developer: **JackTech**
  - Tagline: **"Made with ❤️ in India" / "Love from India 🇮🇳"**
  - Version: 1.0.0 (Ultra Edition)
  - Clean card with social/support links, feedback button, and app license info.

---

### 🎨 2. CORE FEATURES & SCREENS

#### SCREEN 1: 🌟 HOME & CURATED ICON PACKS (BROWSE)
- **Categories Row:** Minimal AMOLED, Cyber Neon, Pure Glyphs, Pastel 3D, Gradient Glass, Retro Vintage, Gaming Anime.
- **Icon Grid:** Fast Staggered/Adaptive Grid displaying crisp 512x512 vector & PNG icons.
- **Search & Filter:** Real-time instant search bar with filter by category or app name (WhatsApp, Instagram, YouTube, etc.).
- **Icon Detail Dialog:** Tap any icon to view in 4K resolution, check supported package name, test on light/dark background, or export to gallery.

#### SCREEN 2: 🛠️ DIY CUSTOM ICON MAKER (USER PHOTO TO APP ICON)
*User can create their own custom app icon from any image in their phone storage:*
1. **Photo Picker:** Pick any image from Gallery or Camera using zero-permission `ActivityResultContracts.PickVisualMedia`.
2. **Icon Shape Masks:** Circle, Squircle, Rounded Square, Hexagon, Tear Drop, Pebble, Diamond.
3. **Studio Styling Tools:**
   - Background Fill (AMOLED Solid, Dual Gradient, Neon Glow, or Transparent).
   - Border Thickness & Border Color Picker.
   - Inner Shadow & 3D Drop Shadow slider.
   - Scale, Zoom, Pan & Rotate image inside the shape mask.
   - Overlay App Badge / Mini Symbol (optional).
4. **App Linking:**
   - App automatically scans installed user apps (WhatsApp, Instagram, Gallery, Camera, etc.) with their real names and package IDs.
   - User picks which app this custom icon will launch.
5. **1-Click Home Screen Placement:**
   - Uses Android's native `ShortcutManager.requestPinShortcut()` to create an instant 1-tap shortcut on the user's home screen.
   - Works on ALL phones (Realme, Samsung, Xiaomi, Vivo, OnePlus) WITHOUT needing any 3rd party launcher!
6. **Save to "My Creations":** Saves custom designs locally in Room Database with instant re-apply and export.

#### SCREEN 3: ⚡ 1-CLICK LAUNCHER APPLIER
- Supports standard intent integration for all major Android launchers:
  - Nova Launcher (`com.teslacoilsw.launcher`)
  - Lawnchair (`ch.deletescape.lawnchair`)
  - Smart Launcher (`ginlemon.flowerfree`)
  - Niagara Launcher (`bitpit.launcher`)
  - Hyperion Launcher
  - Action Launcher
  - OnePlus / Nothing / Motorola Launchers (where supported)
- Detects which compatible launcher is currently installed and displays an "Apply Now" button with green active status.
- Step-by-step interactive visual tutorial for system launchers (OneUI, HyperOS, ColorOS).

#### SCREEN 4: 📩 SMART ICON REQUEST TOOL
- Automatically scans installed apps on the user's phone that do not have a themed icon yet.
- Displays app name, package name, and current icon.
- User selects apps and taps "Send Icon Request" -> creates a ready-to-send email or zip to the developer with package components (`ComponentInfo{pkg/activity}`).

#### SCREEN 5: 🖼️ WALLPAPER & SETUP TESTER (MOCKUP PREVIEW)
- Lets the user test icons against different wallpaper backgrounds (AMOLED Dark, Nature, Anime, Neon) with simulated home screen dock and clock widgets.

#### SCREEN 6: ℹ️ ABOUT & DEVELOPER SECTION
- Dedicated card: **"Developer: JackTech"**
- Subtext: **"Love from India 🇮🇳"**
- Clean, stylish UI with app info, rate on Play Store button, and community/contact links.

---

### ⚙️ 3. TECHNICAL ARCHITECTURE & PERFORMANCE
- **UI Framework:** Jetpack Compose + Material Design 3 (Dark AMOLED theme default with Neon Cyan & Vibrant Violet accents).
- **Navigation:** Navigation Compose with type-safe routing.
- **State Management:** MVVM Architecture with `ViewModel` and `StateFlow`.
- **Database:** Android Room Database for saving user-created icons and favorite icons.
- **Image Pipeline:** Coil 3 for high-speed caching and sub-millisecond vector/PNG decoding.
- **Hardware Acceleration:** Avoid `HARDWARE` bitmap bugs; convert to `ARGB_8888` software bitmaps for shortcut creation.
- **Android Standards:** Full edge-to-edge support with `enableEdgeToEdge()` and proper WindowInsets.
- **Android Manifest:**
  - `com.novalauncher.THEME` and standard icon pack action filters (`android.intent.action.MAIN` + `org.adw.launcher.THEMES`).
  - Standard `appfilter.xml`, `appmap.xml`, and `theme_resources.xml` placed in `res/xml/` with mapping for top 100+ popular apps.

---

### 🎨 4. PRE-BUNDLED ICONS & ASSETS
- Provide a curated set of 80+ stylish vector icons for top global and Indian apps:
  - Phone, Messages, Camera, Settings, Gallery, WhatsApp, Instagram, YouTube, Telegram, Chrome, Spotify, Netflix, Paytm, PhonePe, GPay, Jio, Flipkart, Amazon, Twitter/X, Snapchat, Gmail, Maps, Clock, Calculator, Notes, Files.
- All icons cleanly rendered with smooth geometry and sharp contrast.
