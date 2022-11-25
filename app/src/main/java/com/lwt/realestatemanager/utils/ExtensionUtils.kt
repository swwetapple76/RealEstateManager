package com.lwt.realestatemanager.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object ExtensionUtils {
	@SuppressLint("SimpleDateFormat")
	fun Date.estateFormat(): String {
		return SimpleDateFormat("dd/MM/yyyy").format(this)
	}

	fun Double.truncat(digits: Int) = "%.${digits}f".format(this)
}