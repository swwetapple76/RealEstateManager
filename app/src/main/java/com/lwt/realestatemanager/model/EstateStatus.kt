package com.lwt.realestatemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class EstateStatus(val id: Int) : Parcelable {
	Available(1),
	Sold(2)
}
