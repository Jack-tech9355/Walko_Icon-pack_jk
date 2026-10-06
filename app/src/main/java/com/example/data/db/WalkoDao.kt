package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.FavoriteIcon
import com.example.data.model.SavedCustomIcon
import kotlinx.coroutines.flow.Flow

@Dao
interface WalkoDao {
    // Custom icons created by user
    @Query("SELECT * FROM saved_custom_icons ORDER BY createdAt DESC")
    fun getAllCustomIcons(): Flow<List<SavedCustomIcon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomIcon(icon: SavedCustomIcon): Long

    @Query("DELETE FROM saved_custom_icons WHERE id = :id")
    suspend fun deleteCustomIconById(id: Long)

    // Favorite curated icons
    @Query("SELECT * FROM favorite_icons ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteIcon>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_icons WHERE iconId = :iconId)")
    fun isFavorite(iconId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteIcon)

    @Query("DELETE FROM favorite_icons WHERE iconId = :iconId")
    suspend fun removeFavorite(iconId: String)
}
