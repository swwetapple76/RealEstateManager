package com.lwt.realestatemanager.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NetworkStatusViewModel(
    networkStatusTracker: NetworkStatusTracker,
) : ViewModel() {
    val networkState =
        networkStatusTracker.networkStatus.map(
            onUnavailable = { NetworkStatus.Unavailable },
            onAvailable = { NetworkStatus.Available },
        ).asLiveData(Dispatchers.IO)
}