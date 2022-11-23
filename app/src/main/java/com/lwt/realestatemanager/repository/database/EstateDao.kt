package com.lwt.realestatemanager.repository.database

import android.database.Cursor
import androidx.room.*
import com.lwt.realestatemanager.model.Estate

@Dao
interface EstateDao {
	@Query("SELECT * FROM Estate")
	fun getAll(): List<Estate>

	@Query("SELECT * FROM Estate")
	fun getAllCursor(): Cursor

	@Query("SELECT * FROM Estate WHERE Estate.uid = :uid")
	fun getCursor(uid: Long): Cursor

	@Query("SELECT * FROM Estate WHERE Estate.uid = :uid")
	fun get(uid: Long): Estate

	@Insert
	fun insert(Estate: Estate): Long

	@Insert
	fun insert(vararg Estates: Estate): LongArray

	@Delete
	fun delete(vararg Estates: Estate)

	@Query("DELETE FROM estate WHERE uid = :uid")
	fun delete(uid: Long): Int

	@Query("delete from estate where uid in (:idList)")
	fun delete(idList: List<Long>)

	@Query("DELETE FROM estate")
	fun deleteAll()

	@Update
	fun update(vararg Estates: Estate): Int

}