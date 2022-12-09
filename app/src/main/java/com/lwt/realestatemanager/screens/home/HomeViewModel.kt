package com.lwt.realestatemanager.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lwt.realestatemanager.model.Estate
import com.lwt.realestatemanager.repository.EstateRepository
import com.lwt.realestatemanager.repository.database.EstateDatabase
import com.lwt.realestatemanager.screens.home.filter.FilterSettings
import com.lwt.realestatemanager.screens.home.filter.FilterUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class HomeViewModel : ViewModel() {
    // ----------------------------
    // Lazy Database Init
    // ----------------------------
    fun initDatabase(context: Context) {
        EstateRepository.db = EstateDatabase.getInstance(context)
    }

    // ----------------------------
    // LiveData
    // ----------------------------
    private val estateList: MutableLiveData<List<Estate>?> = MutableLiveData(listOf())
    private var estateLists: List<Estate>? = listOf()

    var estateStatus = MutableLiveData<EstateUIState>()

    @Composable
    fun observeEstate(): State<EstateUIState?> {
        return estateStatus.observeAsState()
    }

    @Composable
    fun rememberEstateList(): State<List<Estate>?> {
        return estateList.observeAsState()
    }

    private val estateSelected: MutableLiveData<Long> = MutableLiveData(-1)

    @Composable
    fun rememberEstateSelected(): State<Long?> {
        return estateSelected.observeAsState()
    }

    @Composable
    fun ObserveEstateSelected(lifecycleOwner: LifecycleOwner, onUpdate: (Long) -> (Unit)) {
        estateSelected.observe(lifecycleOwner, onUpdate)
    }

    private val filterSetting = MutableLiveData(FilterSettings.Default.copy())

    fun setFilterSetting(fs: FilterSettings) {
        filterSetting.postValue(fs)
        getEstatesFromDd()
    }

    fun getFilterSetting(): FilterSettings {
        return filterSetting.value ?: FilterSettings.Default.copy()
    }

    // ----------------------------
    // Set/Get Selected Estate
    // ----------------------------
    fun setSelectedEstate(uid: Long) {
        estateSelected.postValue(uid)
    }

    fun getSelectedEstate(): Estate {
        return estateLists?.find {
            it.uid == estateSelected.value
        } ?: getFirstEstate()
    }

    private fun getFirstEstate(): Estate {
        return Estate()
    }

    // ----------------------------------------------------------------------------------------------------
    // DATABASE METHODS
    // ----------------------------


    // ----------------------------
    // DB Pull Data
    // ----------------------------
    fun updateEstateListFromDB() {
        thread {
            val list = EstateRepository.db?.estateDao()?.getAll()
                ?.let { filterSetting.value?.let { it1 -> FilterUtils.filterList(it, it1) } }
            estateList.postValue(list)
        }
    }

    fun getEstatesFromDd() {
        CoroutineScope(Dispatchers.IO).launch {
            estateStatus.postValue(EstateUIState.Loading)
            val list = EstateRepository
                .db
                ?.estateDao()
                ?.getAll()
                ?.let { estates ->
                    Log.d("tagii", "estates: ${estates.size}")
                    filterSetting.value?.let { settings ->
                        FilterUtils.filterList(estates, settings)
                    }
                }
            Log.d("tagii", "list: ${list?.size}")
            estateLists = list
            estateStatus.postValue(
                if (list != null) {
                    EstateUIState.Ready(list)
                } else {
                    EstateUIState.Failure
                }
            )
        }
    }

    // ----------------------------
    // Update
    // ----------------------------
    fun addEstate(estate: Estate) {
        EstateRepository.callGeocoderService(estate.address) { lat, lng ->
            estate.latitude = lat
            estate.longitude = lng
            thread() {
                EstateRepository.db?.estateDao()?.insert(estate)
                setSelectedEstate(estate.uid)
                updateEstateListFromDB()
            }
        }
    }


    fun addEstates(estateList: List<Estate>) {
        thread {
            EstateRepository.db?.estateDao()?.insert(*(estateList.toTypedArray()))
            getEstatesFromDd()
        }
    }

    // ----------------------------
    // Update
    // ----------------------------
    fun updateEstate(estate: Estate) {
        EstateRepository.callGeocoderService(estate.address) { lat, lng ->
            estate.latitude = lat
            estate.longitude = lng
            thread() {
                EstateRepository.db?.estateDao()?.update(estate)
                updateEstateListFromDB()
            }
        }
    }

    // ----------------------------
    // Delete
    // ----------------------------
    fun deleteEstate(uid: Long) {
        thread {
            EstateRepository.db?.estateDao()?.delete(uid)
            updateEstateListFromDB()
        }
    }

    fun deleteEstate(estate: Estate) {
        thread {
            EstateRepository.db?.estateDao()?.delete(estate)
            updateEstateListFromDB()
        }
    }

    fun deleteEstate(estateList: List<Estate>) {
        thread {
            EstateRepository.db?.estateDao()?.delete(*(estateList.toTypedArray()))
            updateEstateListFromDB()
        }
    }

    fun deleteAllEstate() {
        thread {
            EstateRepository.db?.estateDao()?.deleteAll()
            getEstatesFromDd()
        }
    }

    // ----------------------------
    // State
    // ----------------------------
    sealed class EstateUIState {
        object Loading : EstateUIState()
        object Failure : EstateUIState()
        data class Ready(val estates: List<Estate>) : EstateUIState()
    }
}