package com.lwt.realestatemanager

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.repository.dummy.EstateDummyData
import com.lwt.realestatemanager.repository.database.EstateDatabase
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EstateDatabaseInstrumentedTest {/
	private var db: EstateDatabase? = null

	@Before
	fun before() {
		db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,
			EstateDatabase::class.java)
			.allowMainThreadQueries()
			.build()
	}

	@After
	fun after() {
		db?.close()
	}

	@Test
	fun getOne() {
		val estates: Estate? = db?.estateDao()?.get(5223372000000000000)
		assertNull(estates)
	}

	@Test
	fun insertOne() {
		val estate = EstateDummyData.randomEstate()
		val uid = db?.estateDao()?.insert(estate)
		assertNotNull(uid)
		if (uid != null) {
			val tryGetItem: Estate? = db?.estateDao()?.get(uid)
			assertNotNull(tryGetItem)
			if (tryGetItem != null)
				assertTrue(tryGetItem.description == estate.description)
		}
	}

	@Test
	fun deleteOne() {
		val estate = EstateDummyData.randomEstate()
		val uid = db?.estateDao()?.insert(estate)
		assertNotNull(uid)
		if (uid != null) {
			db?.estateDao()?.delete(uid)
			val tryGetItem: Estate? = db?.estateDao()?.get(uid)
			assertNull(tryGetItem)
		}
	}

	@Test
	fun insertMultiple() {
		val mutableList = mutableListOf<Long>()
		repeat(50)
		{
			val estate = EstateDummyData.randomEstate()
			val uid = db?.estateDao()?.insert(estate)
			uid?.let { it1 -> mutableList.add(it1) }
		}
		mutableList.forEach {
			val tryGetItem: Estate? = db?.estateDao()?.get(it)
			assertNotNull(tryGetItem)
		}
	}

	@Test
	fun deleteMultiple() {
		val mutableList = mutableListOf<Long>()
		repeat(50)
		{
			val estate = EstateDummyData.randomEstate()
			val uid = db?.estateDao()?.insert(estate)
			uid?.let { it1 -> mutableList.add(it1) }
		}

		db?.estateDao()?.delete(mutableList)

		mutableList.forEach {
			val tryGetItem: Estate? = db?.estateDao()?.get(it)
			assertNull(tryGetItem)
		}
	}

	@Test
	fun updateOne() {
		val estate = EstateDummyData.randomEstate()
		val uid = db?.estateDao()?.insert(estate)
		assertNotNull(uid)
		if (uid != null) {
			val firstTryGetItem: Estate? = db?.estateDao()?.get(uid)
			assertNotNull(firstTryGetItem)
			assertTrue(firstTryGetItem?.description ?: "" == estate.description)
			firstTryGetItem?.description = "New ahaha"
			if (firstTryGetItem != null) {
				db?.estateDao()?.update(firstTryGetItem)
				val secondTryGetItem: Estate? = db?.estateDao()?.get(uid)
				assertNotNull(secondTryGetItem)
				assertTrue(secondTryGetItem?.description ?: "" == "New ahaha")
			}
		}
	}
}