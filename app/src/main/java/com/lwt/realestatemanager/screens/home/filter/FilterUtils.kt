package com.lwt.realestatemanager.screens.home.filter

import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.screens.commons.FilterPropertyContainer

object FilterUtils {
	private fun filterByProperty(estate: Estate, from: Estate, to: Estate, prop: FilterPropertyContainer): Boolean {
		when {
			prop.intProps != null -> {
				val value = prop.intProps?.get(estate)
				val valueFrom = prop.intProps?.get(from)
				val valueTo = prop.intProps?.get(to)
				if (value != null && valueFrom != null && valueTo != null) {
					if (value >= valueFrom && value <= valueTo)
						return true
				}
			}

			prop.stringProps != null -> {
				val value = prop.stringProps?.get(estate)
				val valueFrom = prop.stringProps?.get(from)

				if (value != null && valueFrom != null) {
					return value.contains(valueFrom, ignoreCase = true)
				}
			}

			prop.dateProps != null -> {
				val value = prop.dateProps?.get(estate)
				val valueFrom = prop.dateProps?.get(from)
				val valueTo = prop.dateProps?.get(to)
				if (value != null && valueFrom != null && valueTo != null) {
					return (value.after(valueFrom) && value.before(valueTo))
				}
			}
		}
		return false
	}

	fun filterList(list: List<Estate>, filterSetting: FilterSettings): List<Estate> {

		var returnedList: List<Estate> = list

		if (filterSetting.enabled) {
			// Properties
			filterSetting.mapOfProps.keys.forEach { prop ->
				if (filterSetting.mapOfProps[prop] == true)
					returnedList = returnedList.filter {
						filterByProperty(it, filterSetting.from, filterSetting.to, prop)
					}
			}

			// List
			if (filterSetting.status) {
				returnedList = returnedList.filter {
					it.status == filterSetting.from.status
				}
			}

			if (filterSetting.type) {
				returnedList = returnedList.filter {
					it.type == filterSetting.from.type
				}
			}
		}

		returnedList = returnedList.sortedBy { it.status }

		return returnedList.toList();
	}
}
