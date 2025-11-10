package com.example.uvgenius.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [UsuarioEntity::class, TutoriaEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UVDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        fun build(context: Context): UVDatabase =
            Room.databaseBuilder(context, UVDatabase::class.java, "uvgenius.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}

object DatabaseProvider {
    @Volatile private var INSTANCE: UVDatabase? = null
    fun get(context: Context): UVDatabase =
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: UVDatabase.build(context.applicationContext).also { INSTANCE = it }
        }
}
