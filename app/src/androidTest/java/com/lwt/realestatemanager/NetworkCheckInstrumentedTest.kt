package com.lwt.realestatemanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lwt.realestatemanager.utils.Utils
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworkCheckInstrumentedTest {/
	@Test
	fun checkNotInternet() {
		InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
		Thread.sleep(5000)
		assertFalse(Utils.isInternetAvailable())
		InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
	}

	@Test
	fun checkInternet() {
		InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
		Thread.sleep(5000)
		assertTrue(Utils.isInternetAvailable())
		InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
	}
}