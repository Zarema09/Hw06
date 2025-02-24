package com.example.hw06.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}