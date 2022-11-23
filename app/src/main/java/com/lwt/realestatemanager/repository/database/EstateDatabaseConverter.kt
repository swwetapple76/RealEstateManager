package com.lwt.realestatemanager.repository.database

import androidx.room.TypeConverter
import com.lwt.realestatemanager.model.EstateStatus
import com.lwt.realestatemanager.model.EstateType
import com.lwt.realestatemanager.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class EstateDatabaseConverter {
	// Gson serializer
	private var gson: Gson = Gson()

	// ----------------------
	// Timestamp (Date)
	// ----------------------
	@TypeConverter
	fun timestampFromDate(date: Date): Long {
		return date.time
	}

	@TypeConverter
	fun dateFromTimestamp(timestamp: Long): Date {
		return Date(timestamp)
	}

	// ----------------------
	// EstateType
	// ----------------------
	@TypeConverter
	fun toEstateType(value: Int) = enumValues<EstateType>()[value]

	@TypeConverter
	fun fromEstateType(value: EstateType) = value.ordinal

	// ----------------------
	// EstateStatus
	// ----------------------
	@TypeConverter
	fun toEstateStatus(value: Int) = enumValues<EstateStatus>()[value]

	@TypeConverter
	fun fromEstateStatus(value: EstateStatus) = value.ordinal

	// ----------------------
	// Photo List
	// ----------------------
	@TypeConverter
	fun toPhotoList(data: String?): List<Photo> {
		if (data == null) {
			return Collections.emptyList()
		}
		val listType: Type = object : TypeToken<List<Photo?>?>() {}.type
		return gson.fromJson(data, listType)
	}

	@TypeConverter
	fun fromPhotoList(someObjects: List<Photo?>?): String {
		return gson.toJson(someObjects)
	}
}