package `in`.vj.gymmanager

import android.app.Application
import androidx.room.Room

class GymApp : Application() {

    private lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        createDatabase()
    }

    private fun createDatabase() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "gym-manager"
        ).allowMainThreadQueries().build()
    }

    fun getAppDb(): AppDatabase {
        return db
    }
}