package com.lwt.realestatemanager.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.repository.dummy.EstateDummyData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Estate::class], version = 1)
@TypeConverters(EstateDatabaseConverter::class)
abstract class EstateDatabase : RoomDatabase() {

	abstract fun estateDao(): EstateDao

	companion object {
		@Volatile
		private var INSTANCE: EstateDatabase? = null

		private const val NUMBER_OF_THREADS = 4
		val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

		private val roomDatabaseCallback: Callback = object : Callback() {
			override fun onCreate(db: SupportSQLiteDatabase) {
				super.onCreate(db)
				databaseWriteExecutor.execute {
					INSTANCE?.estateDao()?.insert(*(EstateDummyData.getRandomEstateList().toTypedArray()))
				}
			}
		}

		fun getInstance(context: Context): EstateDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					EstateDatabase::class.java,
					"realEstateOffline.SQLite.db"
				).addCallback(roomDatabaseCallback).build()
				INSTANCE = instance
				instance
			}
		}
	}
}