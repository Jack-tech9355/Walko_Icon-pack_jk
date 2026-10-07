package com.example.data.repository

import com.example.R
import com.example.data.model.ThemedIconItem

object IconCatalog {
    val categories = listOf(
        "All",
        "Minimal AMOLED",
        "Cyber Neon",
        "Pure Glyphs",
        "Pastel 3D",
        "Gradient Glass",
        "Retro Vintage",
        "Gaming & Tech"
    )

    val allIcons: List<ThemedIconItem> = listOf(
        // Popular Social & Chat
        ThemedIconItem("whatsapp", "WhatsApp", "com.whatsapp", "com.whatsapp.Main", "Cyber Neon", R.drawable.ic_walko_whatsapp, 0xFF25D366, listOf("chat", "message", "social")),
        ThemedIconItem("instagram", "Instagram", "com.instagram.android", "com.instagram.mainactivity.MainActivity", "Cyber Neon", R.drawable.ic_walko_instagram, 0xFFE1306C, listOf("social", "photo", "reels")),
        ThemedIconItem("telegram", "Telegram", "org.telegram.messenger", "org.telegram.ui.LaunchActivity", "Pure Glyphs", R.drawable.ic_walko_telegram, 0xFF0088CC, listOf("chat", "channel", "social")),
        ThemedIconItem("twitter", "X / Twitter", "com.twitter.android", "com.twitter.android.MainActivity", "Minimal AMOLED", R.drawable.ic_walko_twitter, 0xFF231F1C, listOf("social", "news", "feed")),
        ThemedIconItem("snapchat", "Snapchat", "com.snapchat.android", "com.snapchat.android.LandingPageActivity", "Pastel 3D", R.drawable.ic_walko_snapchat, 0xFFFFFC00, listOf("social", "photo", "chat")),
        ThemedIconItem("discord", "Discord", "com.discord", "com.discord.main.MainActivity", "Gaming & Tech", R.drawable.ic_walko_discord, 0xFF5865F2, listOf("gaming", "chat", "voice")),
        ThemedIconItem("reddit", "Reddit", "com.reddit.frontpage", "com.reddit.frontpage.MainActivity", "Cyber Neon", R.drawable.ic_walko_reddit, 0xFFFF4500, listOf("forum", "community", "social")),
        ThemedIconItem("facebook", "Facebook", "com.facebook.katana", "com.facebook.katana.LoginActivity", "Minimal AMOLED", R.drawable.ic_walko_facebook, 0xFF1877F2, listOf("social", "meta")),
        ThemedIconItem("tiktok", "TikTok", "com.zhiliaoapp.musically", "com.ss.android.ugc.aweme.splash.SplashActivity", "Cyber Neon", R.drawable.ic_walko_tiktok, 0xFF00E5FF, listOf("video", "social")),
        ThemedIconItem("github", "GitHub", "com.github.android", "com.github.android.MainActivity", "Gaming & Tech", R.drawable.ic_walko_github, 0xFF24292E, listOf("developer", "code", "git")),

        // Streaming & Entertainment
        ThemedIconItem("youtube", "YouTube", "com.google.android.youtube", "com.google.android.youtube.HomeActivity", "Gradient Glass", R.drawable.ic_walko_youtube, 0xFFFF0000, listOf("video", "google", "stream")),
        ThemedIconItem("spotify", "Spotify", "com.spotify.music", "com.spotify.music.MainActivity", "Minimal AMOLED", R.drawable.ic_walko_spotify, 0xFF1DB954, listOf("music", "podcast", "audio")),
        ThemedIconItem("netflix", "Netflix", "com.netflix.mediaclient", "com.netflix.mediaclient.ui.launch.UIWebViewActivity", "Minimal AMOLED", R.drawable.ic_walko_netflix, 0xFFE50914, listOf("movies", "shows", "video")),
        ThemedIconItem("prime_video", "Prime Video", "com.amazon.avod.thirdpartyclient", "com.amazon.avod.thirdpartyclient.Launcher", "Gradient Glass", R.drawable.ic_walko_prime_video, 0xFF00A8E1, listOf("movies", "stream")),
        ThemedIconItem("hotstar", "Disney+ Hotstar", "in.startv.hotstar", "in.startv.hotstar.SplashActivity", "Gradient Glass", R.drawable.ic_walko_hotstar, 0xFF0C2461, listOf("stream", "cricket", "movies")),
        ThemedIconItem("twitch", "Twitch", "tv.twitch.android.app", "tv.twitch.android.app.core.LandingActivity", "Gaming & Tech", R.drawable.ic_walko_twitch, 0xFF9146FF, listOf("gaming", "stream", "live")),
        ThemedIconItem("apple_music", "Apple Music", "com.apple.android.music", "com.apple.android.music.MainActivity", "Cyber Neon", R.drawable.ic_walko_apple_music, 0xFFFA243C, listOf("music", "audio")),
        ThemedIconItem("vlc", "VLC Player", "org.videolan.vlc", "org.videolan.vlc.gui.MainActivity", "Retro Vintage", R.drawable.ic_walko_vlc, 0xFFFF8800, listOf("media", "video", "player")),

        // Google & Core Utilities
        ThemedIconItem("chrome", "Google Chrome", "com.android.chrome", "com.google.android.apps.chrome.Main", "Pure Glyphs", R.drawable.ic_walko_chrome, 0xFF4285F4, listOf("browser", "web", "internet")),
        ThemedIconItem("gmail", "Gmail", "com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail", "Pure Glyphs", R.drawable.ic_walko_gmail, 0xFFEA4335, listOf("mail", "email", "google")),
        ThemedIconItem("maps", "Google Maps", "com.google.android.apps.maps", "com.google.android.maps.MapsActivity", "Minimal AMOLED", R.drawable.ic_walko_maps, 0xFF34A853, listOf("navigation", "gps", "travel")),
        ThemedIconItem("photos", "Google Photos", "com.google.android.apps.photos", "com.google.android.apps.photos.home.HomeActivity", "Pastel 3D", R.drawable.ic_walko_photos, 0xFFFBBC04, listOf("gallery", "cloud", "backup")),
        ThemedIconItem("gpay", "Google Pay", "com.google.android.apps.nbu.paisa.user", "com.google.android.apps.nbu.paisa.user.LauncherActivity", "Cyber Neon", R.drawable.ic_walko_gpay, 0xFF00E5FF, listOf("upi", "payment", "finance")),
        ThemedIconItem("files", "Files by Google", "com.google.android.apps.nbu.files", "com.google.android.apps.nbu.files.home.HomeActivity", "Pure Glyphs", R.drawable.ic_walko_files, 0xFF4285F4, listOf("storage", "cleaner", "manager")),
        ThemedIconItem("keep", "Google Keep", "com.google.android.keep", "com.google.android.keep.activities.BrowseActivity", "Pastel 3D", R.drawable.ic_walko_keep, 0xFFFBC02D, listOf("notes", "todo", "lists")),
        ThemedIconItem("drive", "Google Drive", "com.google.android.apps.docs", "com.google.android.apps.docs.app.NewMainProxyActivity", "Gradient Glass", R.drawable.ic_walko_drive, 0xFF0066DA, listOf("cloud", "storage", "documents")),
        ThemedIconItem("playstore", "Google Play Store", "com.android.vending", "com.android.vending.AssetBrowserActivity", "Cyber Neon", R.drawable.ic_walko_playstore, 0xFF00E5FF, listOf("store", "apps", "games")),

        // Indian Top Apps & Payments
        ThemedIconItem("paytm", "Paytm", "net.one97.paytm", "net.one97.paytm.landingpage.activity.AJRMainActivity", "Cyber Neon", R.drawable.ic_walko_paytm, 0xFF002970, listOf("upi", "wallet", "payments")),
        ThemedIconItem("phonepe", "PhonePe", "com.phonepe.app", "com.phonepe.app.v4.gui.NavHostActivity", "Cyber Neon", R.drawable.ic_walko_phonepe, 0xFF5F259F, listOf("upi", "finance", "payments")),
        ThemedIconItem("jio", "MyJio", "com.jio.myjio", "com.jio.myjio.dashboard.activities.DashboardActivity", "Gradient Glass", R.drawable.ic_walko_jio, 0xFFE21A22, listOf("recharge", "telecom", "5g")),
        ThemedIconItem("flipkart", "Flipkart", "com.flipkart.android", "com.flipkart.android.SplashActivity", "Pastel 3D", R.drawable.ic_walko_flipkart, 0xFF2874F0, listOf("shopping", "ecommerce", "deals")),
        ThemedIconItem("amazon", "Amazon India", "com.amazon.mShop.android.shopping", "com.amazon.mShop.home.HomeActivity", "Minimal AMOLED", R.drawable.ic_walko_amazon, 0xFFFF9900, listOf("shopping", "prime", "store")),
        ThemedIconItem("swiggy", "Swiggy", "in.swiggy.android", "in.swiggy.android.activities.HomeActivity", "Pastel 3D", R.drawable.ic_walko_swiggy, 0xFFFC8019, listOf("food", "delivery", "instamart")),
        ThemedIconItem("zomato", "Zomato", "com.application.zomato", "com.application.zomato.activities.Splash", "Minimal AMOLED", R.drawable.ic_walko_zomato, 0xFFE23744, listOf("food", "dining", "order")),
        ThemedIconItem("cred", "CRED", "com.dreamplug.androidapp", "com.dreamplug.androidapp.ui.HomeActivity", "Minimal AMOLED", R.drawable.ic_walko_cred, 0xFF231F1C, listOf("credit", "bills", "luxury")),
        ThemedIconItem("zerodha", "Zerodha Kite", "com.zerodha.kite3", "com.zerodha.kite3.MainActivity", "Minimal AMOLED", R.drawable.ic_walko_zerodha, 0xFFFF5722, listOf("stocks", "invest", "trading")),
        ThemedIconItem("groww", "Groww", "com.nextbillion.groww", "com.nextbillion.groww.ui.main.MainActivity", "Pure Glyphs", R.drawable.ic_walko_groww, 0xFF00D09C, listOf("mutual funds", "stocks", "sip")),
        ThemedIconItem("bhim", "BHIM UPI", "in.org.npci.upiapp", "in.org.npci.upiapp.HomeActivity", "Pure Glyphs", R.drawable.ic_walko_bhim, 0xFF00796B, listOf("upi", "npci", "money")),
        ThemedIconItem("airtel", "Airtel Thanks", "com.myairtelapp", "com.myairtelapp.activity.MainActivity", "Gradient Glass", R.drawable.ic_walko_airtel, 0xFFED1C24, listOf("telecom", "bills", "bank")),

        // Device System Essentials
        ThemedIconItem("phone", "Phone / Dialer", "com.google.android.dialer", "com.google.android.dialer.extensions.GoogleDialtactsActivity", "Pure Glyphs", R.drawable.ic_walko_phone, 0xFF00E676, listOf("call", "contacts", "dialer")),
        ThemedIconItem("messages", "Messages", "com.google.android.apps.messaging", "com.google.android.apps.messaging.ui.ConversationListActivity", "Pure Glyphs", R.drawable.ic_walko_messages, 0xFF29B6F6, listOf("sms", "chat", "rcs")),
        ThemedIconItem("camera", "Camera", "com.google.android.GoogleCamera", "com.android.camera.CameraLauncher", "Minimal AMOLED", R.drawable.ic_walko_camera, 0xFFAB47BC, listOf("photo", "video", "shutter")),
        ThemedIconItem("settings", "Settings", "com.android.settings", "com.android.settings.Settings", "Minimal AMOLED", R.drawable.ic_walko_settings, 0xFF78909C, listOf("system", "configuration", "preferences")),
        ThemedIconItem("clock", "Clock & Alarm", "com.google.android.deskclock", "com.android.deskclock.DeskClock", "Minimal AMOLED", R.drawable.ic_walko_clock, 0xFF26A69A, listOf("time", "alarm", "timer")),
        ThemedIconItem("calculator", "Calculator", "com.google.android.calculator", "com.android.calculator2.Calculator", "Retro Vintage", R.drawable.ic_walko_calculator, 0xFFFF7043, listOf("math", "finance", "tool")),
        ThemedIconItem("contacts", "Contacts", "com.google.android.contacts", "com.android.contacts.activities.PeopleActivity", "Pure Glyphs", R.drawable.ic_walko_contacts, 0xFF1976D2, listOf("people", "addressbook", "phone")),
        ThemedIconItem("calendar", "Google Calendar", "com.google.android.calendar", "com.android.calendar.AllInOneActivity", "Pastel 3D", R.drawable.ic_walko_calendar, 0xFF4285F4, listOf("events", "schedule", "planner")),
        ThemedIconItem("gallery", "System Gallery", "com.sec.android.gallery3d", "com.sec.android.gallery3d.app.GalleryActivity", "Gradient Glass", R.drawable.ic_walko_gallery, 0xFFFF4081, listOf("photos", "albums", "camera")),
        ThemedIconItem("sound_recorder", "Audio Recorder", "com.google.android.soundrecorder", "com.google.android.soundrecorder.MainActivity", "Cyber Neon", R.drawable.ic_walko_sound_recorder, 0xFFFF1744, listOf("voice", "mic", "record")),
        ThemedIconItem("compass", "Digital Compass", "com.example.compass", "com.example.compass.MainActivity", "Pure Glyphs", R.drawable.ic_walko_compass, 0xFF00E5FF, listOf("direction", "sensors", "tool")),
        ThemedIconItem("flashlight", "Torch Flashlight", "com.example.flashlight", "com.example.flashlight.MainActivity", "Cyber Neon", R.drawable.ic_walko_flashlight, 0xFFFFD600, listOf("light", "torch", "hardware")),
        ThemedIconItem("notes", "Samsung Notes / Notes", "com.samsung.android.app.notes", "com.samsung.android.app.notes.main.MainActivity", "Pure Glyphs", R.drawable.ic_walko_notes, 0xFFFFB300, listOf("notes", "memo", "write")),

        // Productivity & Dev Tools
        ThemedIconItem("notion", "Notion", "notion.id", "notion.id.MainActivity", "Minimal AMOLED", R.drawable.ic_walko_notion, 0xFF231F1C, listOf("notes", "docs", "workspace")),
        ThemedIconItem("slack", "Slack", "com.Slack", "com.Slack.ui.HomeActivity", "Pure Glyphs", R.drawable.ic_walko_slack, 0xFF4A154B, listOf("work", "chat", "team")),
        ThemedIconItem("zoom", "Zoom Workplace", "us.zoom.videomeetings", "com.zipow.videobox.LauncherActivity", "Gradient Glass", R.drawable.ic_walko_zoom, 0xFF2D8CFF, listOf("meetings", "video", "call")),
        ThemedIconItem("teams", "Microsoft Teams", "com.microsoft.teams", "com.microsoft.teams.MainActivity", "Gradient Glass", R.drawable.ic_walko_teams, 0xFF505AC9, listOf("chat", "meetings", "office")),
        ThemedIconItem("trello", "Trello", "com.trello", "com.trello.home.HomeActivity", "Pure Glyphs", R.drawable.ic_walko_trello, 0xFF0079BF, listOf("kanban", "tasks", "project")),
        ThemedIconItem("todoist", "Todoist", "com.todoist", "com.todoist.activity.HomeActivity", "Pastel 3D", R.drawable.ic_walko_todoist, 0xFFE44332, listOf("todo", "planner", "tasks")),
        ThemedIconItem("bitwarden", "Bitwarden Password", "com.x8bit.bitwarden", "com.x8bit.bitwarden.MainActivity", "Gaming & Tech", R.drawable.ic_walko_bitwarden, 0xFF175DDC, listOf("passwords", "vault", "security")),
        ThemedIconItem("chatgpt", "ChatGPT", "com.openai.chatgpt", "com.openai.chatgpt.MainActivity", "Minimal AMOLED", R.drawable.ic_walko_chatgpt, 0xFF10A37F, listOf("ai", "assistant", "openai")),
        ThemedIconItem("termux", "Termux Terminal", "com.termux", "com.termux.app.TermuxActivity", "Gaming & Tech", R.drawable.ic_walko_termux, 0xFF00FF66, listOf("linux", "terminal", "bash")),
        ThemedIconItem("vscode", "VS Code Mobile", "com.coder.codebrowser", "com.coder.codebrowser.MainActivity", "Gaming & Tech", R.drawable.ic_walko_vscode, 0xFF007ACC, listOf("code", "editor", "developer")),

        // Gaming, Anime & Lifestyle
        ThemedIconItem("genshin", "Genshin Impact", "com.miHoYo.GenshinImpact", "com.miHoYo.GetMobile.MainActivity", "Gaming & Tech", R.drawable.ic_walko_genshin, 0xFFFFC107, listOf("game", "rpg", "anime")),
        ThemedIconItem("pubg", "BGMI / PUBG Mobile", "com.pubg.imobile", "com.epicgames.ue4.SplashActivity", "Gaming & Tech", R.drawable.ic_walko_pubg, 0xFFFF9800, listOf("battle royale", "fps", "game")),
        ThemedIconItem("freefire", "Free Fire MAX", "com.dts.freefiremax", "com.dts.freefireth.FFMainActivity", "Gaming & Tech", R.drawable.ic_walko_freefire, 0xFFFF5722, listOf("battle royale", "game", "action")),
        ThemedIconItem("roblox", "Roblox", "com.roblox.client", "com.roblox.client.ActivityProtocolLaunch", "Pastel 3D", R.drawable.ic_walko_roblox, 0xFF231F1C, listOf("sandbox", "game", "kids")),
        ThemedIconItem("minecraft", "Minecraft", "com.mojang.minecraftpe", "com.mojang.minecraftpe.MainActivity", "Retro Vintage", R.drawable.ic_walko_minecraft, 0xFF538D4E, listOf("craft", "building", "sandbox")),
        ThemedIconItem("steam", "Steam Mobile", "com.valvesoftware.android.steam.community", "com.valvesoftware.android.steam.community.MainActivity", "Gaming & Tech", R.drawable.ic_walko_steam, 0xFF171A21, listOf("valve", "pc", "gaming")),
        ThemedIconItem("crunchyroll", "Crunchyroll Anime", "com.crunchyroll.crunchyroid", "com.crunchyroll.crunchyroid.MainActivity", "Gaming & Tech", R.drawable.ic_walko_crunchyroll, 0xFFF47521, listOf("anime", "manga", "streaming")),
        ThemedIconItem("pinterest", "Pinterest", "com.pinterest", "com.pinterest.activity.PinterestActivity", "Pastel 3D", R.drawable.ic_walko_pinterest, 0xFFE60023, listOf("design", "aesthetic", "ideas")),
        ThemedIconItem("duolingo", "Duolingo", "com.duolingo", "com.duolingo.splash.SplashActivity", "Pastel 3D", R.drawable.ic_walko_duolingo, 0xFF58CC02, listOf("language", "learn", "green")),
        ThemedIconItem("uber", "Uber", "com.ubercab", "com.ubercab.presidio.app.core.root.RootActivity", "Minimal AMOLED", R.drawable.ic_walko_uber, 0xFF231F1C, listOf("cab", "travel", "ride")),
        ThemedIconItem("ola", "Ola Cabs", "com.olacabs.customer", "com.olacabs.customer.ui.SplashActivity", "Minimal AMOLED", R.drawable.ic_walko_ola, 0xFFC6D92D, listOf("cab", "travel", "auto")),
        ThemedIconItem("rapido", "Rapido Bike Taxi", "com.rapido.passenger", "com.rapido.passenger.ui.views.SplashActivity", "Cyber Neon", R.drawable.ic_walko_rapido, 0xFFF9D615, listOf("bike", "cab", "travel")),
        ThemedIconItem("canva", "Canva Graphic Design", "com.canva.editor", "com.canva.editor.MainActivity", "Gradient Glass", R.drawable.ic_walko_canva, 0xFF00C4CC, listOf("graphic", "posters", "design")),
        ThemedIconItem("lightroom", "Adobe Lightroom", "com.adobe.lrmobile", "com.adobe.lrmobile.MainActivity", "Gradient Glass", R.drawable.ic_walko_lightroom, 0xFF31A8FF, listOf("photo", "preset", "editor")),
        ThemedIconItem("snapseed", "Snapseed", "com.niksoftware.snapseed", "com.niksoftware.snapseed.MainActivity", "Pure Glyphs", R.drawable.ic_walko_snapseed, 0xFF43A047, listOf("editing", "google", "photos")),
        ThemedIconItem("vsco", "VSCO Cam", "com.vsco.cam", "com.vsco.cam.ui.activities.SplashActivity", "Minimal AMOLED", R.drawable.ic_walko_vsco, 0xFF231F1C, listOf("aesthetic", "filters", "vsco")),
        ThemedIconItem("medium", "Medium", "com.medium.reader", "com.medium.reader.MainActivity", "Minimal AMOLED", R.drawable.ic_walko_medium, 0xFF231F1C, listOf("articles", "blog", "reading")),
        ThemedIconItem("quora", "Quora", "com.quora.android", "com.quora.android.MainActivity", "Retro Vintage", R.drawable.ic_walko_quora, 0xFFA82400, listOf("questions", "answers", "forum")),
        ThemedIconItem("pocket_casts", "Pocket Casts", "au.com.shiftyjelly.pocketcasts", "au.com.shiftyjelly.pocketcasts.ui.MainActivity", "Cyber Neon", R.drawable.ic_walko_pocket_casts, 0xFFF43E37, listOf("podcast", "audio", "radio")),
        ThemedIconItem("audiomack", "Audiomack", "com.audiomack", "com.audiomack.ui.home.HomeActivity", "Retro Vintage", R.drawable.ic_walko_audiomack, 0xFFFFA200, listOf("music", "hiphop", "songs"))
    )
}
