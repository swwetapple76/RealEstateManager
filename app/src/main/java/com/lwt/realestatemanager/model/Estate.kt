package com.lwt.realestatemanager.model

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.lang.reflect.Type
import java.util.*

@Parcelize
@Entity
data class Estate(
	@PrimaryKey(autoGenerate = true)
	var uid: Long = 0,
	var added: Date = Date(),
	var type: EstateType = EstateType.Flat,
	var price: Int = 0,
	var surface: Int = 0,
	var description: String = "",
	var photos: List<Photo> = listOf(Photo(name = "Facade", onlineUrl = "https://picsum.photos/id/155/300/300", localUri = null, description = "")),
	var district: String = "",
	var sold: Date = Date(),
	var agent: String = "",
	var rooms: Int = 0,
	var bathrooms: Int = 0,
	var bedrooms: Int = 0,
	var address: String = "",
	var latitude: Double = 48.8584,
	var longitude: Double = 2.2945,
	var interest: String = "",
	var status: EstateStatus = EstateStatus.Available,
) : Parcelable {
	companion object {
		private var gson: Gson = Gson()
		fun fromContentValues(contentValues: ContentValues?): Estate {
			val estate = Estate()

			if (contentValues != null) {
				if (contentValues.containsKey("uid"))
					contentValues.getAsLong("uid").also { estate.uid = it }
				if (contentValues.containsKey("added"))
					contentValues.getAsLong("added").also { estate.added = Date(it) }
				if (contentValues.containsKey("type"))
					contentValues.getAsInteger("type").also { estate.type = enumValues<EstateType>()[it] }
				if (contentValues.containsKey("status"))
					contentValues.getAsInteger("status").also { estate.status = enumValues<EstateStatus>()[it] }
				if (contentValues.containsKey("surface"))
					contentValues.getAsInteger("surface").also { estate.surface = it }
				if (contentValues.containsKey("price"))
					contentValues.getAsInteger("price").also { estate.price = it }
				if (contentValues.containsKey("description"))
					contentValues.getAsString("description").also { estate.description = it }
				if (contentValues.containsKey("photos")) {
					contentValues.getAsString("photos").also {
						if (it == null) {
							estate.photos = Collections.emptyList()
						}
						val listType: Type = object : TypeToken<List<Photo?>?>() {}.type
						estate.photos = gson.fromJson(it, listType)
					}
				}
				if (contentValues.containsKey("district"))
					contentValues.getAsString("district").also { estate.district = it }
				if (contentValues.containsKey("sold"))
					contentValues.getAsLong("sold").also { estate.sold = Date(it) }
				if (contentValues.containsKey("agent"))
					contentValues.getAsString("agent").also { estate.agent = it }
				if (contentValues.containsKey("rooms"))
					contentValues.getAsInteger("rooms").also { estate.rooms = it }
				if (contentValues.containsKey("bathrooms"))
					contentValues.getAsInteger("bathrooms").also { estate.bathrooms = it }
				if (contentValues.containsKey("bedrooms"))
					contentValues.getAsInteger("bedrooms").also { estate.bedrooms = it }
				if (contentValues.containsKey("address"))
					contentValues.getAsString("address").also { estate.address = it }
				if (contentValues.containsKey("latitude"))
					contentValues.getAsDouble("latitude").also { estate.latitude = it }
				if (contentValues.containsKey("longitude"))
					contentValues.getAsDouble("longitude").also { estate.longitude = it }
				if (contentValues.containsKey("interest"))
					contentValues.getAsString("interest").also { estate.interest = it }
			}
			return estate
		}
	}
}
