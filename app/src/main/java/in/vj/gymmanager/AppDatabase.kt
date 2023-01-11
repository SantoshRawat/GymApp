package `in`.vj.gymmanager

import `in`.vj.gymmanager.dao.UserDao
import `in`.vj.gymmanager.entities.User
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
   /* companion object {

        const val DATABASE_NAME = "userdb"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        *//*---------------------Create one (only one) instance of the Database--------------------------*//*

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                //Delete Database, when something changed
                .fallbackToDestructiveMigration()
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread

                    }
                })
                .build()

        *//*---------------------This runs a background task--------------------------*//*
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        *//**
         * Utility method to run blocks on a dedicated background thread, used for io/database work.
         *//*
        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }

    }*/
}