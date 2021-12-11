package dev.bytecode.cityly

import android.util.Log
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
    val mapOfUrbanAreaInfo = mutableListOf<UrbanAreaInfo>()
    val TAG = "MainViewModel"

    fun getUrbanAreas() {
        viewModelScope.launch {
            val urbanAreas = urbanAreasService.getUrbanAreas()
            urbanAreas.links.uaItem.forEach { uaItem ->
                val startIndex = uaItem.href.indexOfLast { it == ':' }
                val endIndex = uaItem.href.length - 1
                val href = uaItem.href.substring(startIndex + 1, endIndex)
                listOfUrbanAreaNamesHrefs[uaItem.name] = href

            }
        //    Log.d(TAG, listOfUrbanAreaNamesHrefs.toString())

                getUrbanInfo(listOfUrbanAreaNamesHrefs.values.first())
        }
    }

    fun getUrbanInfo(name: String) {
        viewModelScope.launch {
            val urbanAreaInfo = urbanAreasService.getUrbanAreaInfo(name)
            urbanAreaInfo.salaries = urbanAreasService.getUrbanAreaSalaries(name)
            urbanAreaInfo.scores = urbanAreasService.getUrbanAreaScores(name)
            Log.d("$TAG-salaries", Gson().toJson(urbanAreaInfo.salaries))
            Log.d("$TAG-scores", Gson().toJson(urbanAreaInfo.scores))
            Log.d("$TAG-urbaninfo", urbanAreaInfo.toString())
        }
    }
}