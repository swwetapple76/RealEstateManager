package com.lwt.realestatemanager

import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.model.EstateStatus
import com.lwt.realestatemanager.model.EstateType
import com.lwt.realestatemanager.repository.dummy.EstateDummyData
import com.lwt.realestatemanager.screens.commons.FilterPropertyContainer
import com.lwt.realestatemanager.screens.home.filter.FilterSettings
import com.lwt.realestatemanager.screens.home.filter.FilterUtils
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import kotlin.random.Random

@RunWith(JUnit4::class)
class FilterInstrumentedTest {
	@Test
	fun testFilterPriveAndSurface() {
		repeat(1000)
		{
			val originalList = EstateDummyData.getRandomEstateList()
			val randomPriceTo = Random.nextInt(100000, 5500000)
			val randomPriceFrom = randomPriceTo / 2
			val randomSurfaceTo = Random.nextInt(25, 1000)
			val randomSurfaceFrom = randomSurfaceTo / 2
			var filteredList = originalList.toList()
			val filterSettings = FilterSettings.Default.copy()
			filterSettings.enabled = true
			filterSettings.mapOfProps = mutableMapOf(
				FilterPropertyContainer(intProps = Estate::price) to true,
				FilterPropertyContainer(intProps = Estate::surface) to true
			)
			filterSettings.from.price = randomPriceFrom
			filterSettings.to.price = randomPriceTo
			filterSettings.from.surface = randomSurfaceFrom
			filterSettings.to.surface = randomSurfaceTo
			filteredList = FilterUtils.filterList(filteredList, filterSettings)
			filteredList.forEach {
				assertTrue(it.price in randomPriceFrom..randomPriceTo)
				assertTrue(it.surface in randomSurfaceFrom..randomSurfaceTo)
			}
		}
	}

	@Test
	fun testFilterAgentAndDescription() {
		repeat(1000)
		{
			val originalList = EstateDummyData.getRandomEstateList()
			val randomName = EstateDummyData.randomName()
			val randomDescription = EstateDummyData.randomLoremIpsum()
			var filteredList = originalList.toList()
			val filterSettings = FilterSettings.Default.copy()
			filterSettings.enabled = true
			filterSettings.mapOfProps = mutableMapOf(
				FilterPropertyContainer(stringProps = Estate::description) to true,
				FilterPropertyContainer(stringProps = Estate::agent) to true
			)
			filterSettings.from.description = randomDescription
			filterSettings.from.agent = randomName
			filteredList = FilterUtils.filterList(filteredList, filterSettings)
			filteredList.forEach {
				assertTrue(it.description.contains(randomDescription))
				assertTrue(it.agent.contains(randomName))
			}
		}
	}

	@Test
	fun testFilterStatusAndType() {
		repeat(1000)
		{
			val originalList = EstateDummyData.getRandomEstateList()
			val randStatus = EstateDummyData.randomEnum<EstateStatus>()
			val randType = EstateDummyData.randomEnum<EstateType>()
			var filteredList = originalList.toList()
			val filterSettings = FilterSettings.Default.copy()
			filterSettings.enabled = true
			filterSettings.type = true
			filterSettings.from.type = randType
			filterSettings.status = true
			filterSettings.from.status = randStatus
			filteredList = FilterUtils.filterList(filteredList, filterSettings)
			filteredList.forEach {
				assertTrue(it.type == randType)
				assertTrue(it.status == randStatus)
			}
		}
	}

	@Test
	fun testFilterDateSold() {
		repeat(1000)
		{
			val originalList = EstateDummyData.getRandomEstateList()
			val dateSold = EstateDummyData.randomDate()
			var filteredList = originalList.toList()
			val filterSettings = FilterSettings.Default.copy()
			filterSettings.enabled = true
			filterSettings.mapOfProps = mutableMapOf(
				FilterPropertyContainer(dateProps = Estate::sold) to true
			)
			filterSettings.from.sold = dateSold
			filterSettings.to.sold = Date()
			filteredList = FilterUtils.filterList(filteredList, filterSettings)
			filteredList.forEach {
				assertTrue(it.sold.after(dateSold) && it.sold.before(Date()))
			}
		}
	}

	@Test
	fun testFilterDateAdded() {
		repeat(1000)
		{
			val originalList = EstateDummyData.getRandomEstateList()
			var filteredList = originalList.toList()
			val randomDate = EstateDummyData.randomDate()
			val filterSettings = FilterSettings.Default.copy()
			filterSettings.enabled = true
			filterSettings.mapOfProps = mutableMapOf(
				FilterPropertyContainer(dateProps = Estate::added) to true
			)
			filterSettings.from.added = randomDate
			filterSettings.to.added = Date()
			filteredList = FilterUtils.filterList(filteredList, filterSettings)
			filteredList.forEach {
				assertTrue(it.added.after(randomDate) && it.added.before(Date()))
			}
		}
	}
}