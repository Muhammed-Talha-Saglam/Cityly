package dev.bytecode.cityly

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bytecode.cityly.model.UrbanAreaInfo
import dev.bytecode.cityly.network.UrbanAreasService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val urbanAreasService: UrbanAreasService
) : ViewModel() {

    val listOfUrbanAreaNamesHrefs = mutableMapOf<String, String>()
    val listOfUrbanAreaInfo = mutableListOf<UrbanAreaInfo>()
    val TAG = "MainViewModel"
    var uiState = mutableStateOf(MainUiState())

    init {
        getUrbanAreas()
    }

    fun getUrbanAreas() {
        viewModelScope.launch {
            uiState.value = MainUiState(emptyList(), true)
            val urbanAreas = urbanAreasService.getUrbanAreas()
            urbanAreas.links.uaItem.forEach { uaItem ->
                val startIndex = uaItem.href.indexOfLast { it == ':' }
                val endIndex = uaItem.href.length - 1
                val href = uaItem.href.substring(startIndex + 1, endIndex)
                listOfUrbanAreaNamesHrefs[uaItem.name] = href

            }
            //    Log.d(TAG, listOfUrbanAreaNamesHrefs.toString())

            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(0))
            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(1))
            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(2))
            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(3))
            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(4))
            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(5))
            uiState.value = MainUiState(listOfUrbanAreaInfo, false)
        }
    }

    suspend fun getUrbanInfo(name: String) {
        val urbanAreaInfo = urbanAreasService.getUrbanAreaInfo(name)
        urbanAreaInfo.salaries = urbanAreasService.getUrbanAreaSalaries(name)
        urbanAreaInfo.scores = urbanAreasService.getUrbanAreaScores(name)
        listOfUrbanAreaInfo.add(urbanAreaInfo)

    }
}