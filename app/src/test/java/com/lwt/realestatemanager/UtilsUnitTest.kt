package com.lwt.realestatemanager

import com.lwt.realestatemanager.utils.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilsUnitTest {
	@Test
	fun testMoneyConvert() {

		// Euro to Dollars

		assertEquals(72439, Utils.convertDollarToEuro(85553))
		assertEquals(914456, Utils.convertDollarToEuro(1080000))



		// Dollars to Euro

		assertEquals(101041, Utils.convertEuroToDollar(85553))
		assertEquals(1275512, Utils.convertEuroToDollar(1080000))
	}

	@Test
	fun testDateFormat() {

		// Today Date Format

		assertEquals(SimpleDateFormat("dd/MM/yyyy").format(Date()), Utils.getTodayDate())
	}
}