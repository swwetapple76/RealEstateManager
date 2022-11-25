package com.lwt.realestatemanager.screens.home.filter

import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.screens.commons.FilterPropertyContainer

data class FilterSettings(
	var from: Estate,
	var to: Estate,
	var enabled: Boolean,
	var type: Boolean,
	var status: Boolean,
	var mapOfProps: MutableMap<FilterPropertyContainer, Boolean>,
) {
	companion object {
		val Default = FilterSettings(
			from = (Estate(agent = "", surface = 50, rooms = 0, bedrooms = 0, bathrooms = 1, price = 0)),
			to = Estate(agent = "", surface = 250, rooms = 7, bedrooms = 2, bathrooms = 5, price = 200000),
			enabled = false,
			type = false,
			status = false,
			mapOfProps = mutableMapOf(
				FilterPropertyContainer(stringProps = Estate::agent) to false,
				FilterPropertyContainer(stringProps = Estate::district) to false,
				FilterPropertyContainer(stringProps = Estate::address) to false,
				FilterPropertyContainer(dateProps = Estate::added) to false,
				FilterPropertyContainer(dateProps = Estate::sold) to false,
				FilterPropertyContainer(intProps = Estate::price) to false,
				FilterPropertyContainer(intProps = Estate::surface) to false,
				FilterPropertyContainer(intProps = Estate::rooms) to false,
				FilterPropertyContainer(intProps = Estate::bathrooms) to false,
				FilterPropertyContainer(intProps = Estate::bedrooms) to false,
				FilterPropertyContainer(intProps = Estate::surface) to false,
				FilterPropertyContainer(stringProps = Estate::interest) to false,
				FilterPropertyContainer(stringProps = Estate::description) to false,
			)
		)
	}
}