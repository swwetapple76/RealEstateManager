package com.lwt.realestatemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class EstateType(val id: Int) : Parcelable {
	Flat(1),
	House(2),
	Duplex(3),
	Penthouse(4)
}