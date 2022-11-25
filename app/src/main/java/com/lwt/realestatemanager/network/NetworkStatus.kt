package com.lwt.realestatemanager.network

sealed class NetworkStatus {
	object Available : NetworkStatus()
	object Unavailable : NetworkStatus()
}