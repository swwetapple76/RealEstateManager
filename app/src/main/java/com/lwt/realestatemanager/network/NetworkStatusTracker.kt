package com.lwt.realestatemanager.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

@SuppressLint("MissingPermission") // Bug
class NetworkStatusTracker(context: Context) {

	private val connectivityManager =
		context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

	@ExperimentalCoroutinesApi
	val networkStatus = callbackFlow {
		val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {

			override fun onUnavailable() {
				trySend(NetworkStatus.Unavailable)
			}

			override fun onAvailable(network: Network) {
				trySend(NetworkStatus.Available)
			}

			override fun onLost(network: Network) {
				trySend(NetworkStatus.Unavailable)
			}
		}

		val request = NetworkRequest.Builder()
			.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
			.build()
		connectivityManager.registerNetworkCallback(request, networkStatusCallback)

		awaitClose {
			connectivityManager.unregisterNetworkCallback(networkStatusCallback)
		}
	}
}

inline fun <Result> Flow<NetworkStatus>.map(
	crossinline onUnavailable: suspend () -> Result,
	crossinline onAvailable: suspend () -> Result,
): Flow<Result> = map { status ->
	when (status) {
		NetworkStatus.Unavailable -> onUnavailable()
		NetworkStatus.Available -> onAvailable()
	}
}