package com.lwt.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lwt.realestatemanager.provider.EstateContentProvider
import com.lwt.realestatemanager.repository.database.EstateDatabase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random.Default.nextLong

@RunWith(AndroidJUnit4::class)
class EstateContentProviderInstrumentedTest {/
	companion object {
		private const val description: String = "Lorem Ipsum Blabla"
		private const val latitude: Double = 5.0
		private const val type: Int = 1
		private const val surface: Int = 500
	}

	private var mContentResolver: ContentResolver? = null

	private fun makeEstate(uid: Long): ContentValues? {
		val values = ContentValues()
		values.put("description", description)
		values.put("surface", surface)
		values.put("latitude", latitude)
		values.put("type", type)
		values.put("uid", uid)
		return values
	}

	@Before
	fun setUp() {
		Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,
			EstateDatabase::class.java)
			.allowMainThreadQueries()
			.build()
		mContentResolver = InstrumentationRegistry.getInstrumentation().targetContext.contentResolver
	}

	@Test
	fun tryGetNonExistingItem() {
		val cursor: Cursor? = mContentResolver!!.query(ContentUris.withAppendedId(EstateContentProvider.URI_ITEM, 5556000), null, null, null, null)
		assertThat(cursor, notNullValue())
		assertThat(cursor?.count, `is`(0))
		cursor?.close()
	}

	@Test
	fun insertEstateAndTest() {
		val uid: Long = nextLong()
		// Insert Estate
		mContentResolver!!.insert(EstateContentProvider.URI_ITEM, makeEstate(uid))
		// TEST

		val cursor: Cursor? = mContentResolver!!.query(ContentUris.withAppendedId(EstateContentProvider.URI_ITEM, uid), null, null, null, null)
		assertThat(cursor, notNullValue())
		if (cursor != null) {
			assertThat(cursor.count, `is`(1))
			assertThat(cursor.moveToFirst(), `is`(true))
			assertThat(cursor.getString(cursor.getColumnIndexOrThrow("description")), `is`(description))
			assertThat(cursor.getInt(cursor.getColumnIndexOrThrow("surface")), `is`(surface))
			assertThat(cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")), `is`(latitude))
			assertThat(cursor.getInt(cursor.getColumnIndexOrThrow("type")), `is`(type))
		}
	}
}