package dev.bytecode.cityly.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bytecode.cityly.data.model.Result
import dev.bytecode.cityly.data.model.UrbanAreaInfo
import dev.bytecode.cityly.data.network.UrbanAreasService
import dev.bytecode.cityly.utilities.NetworkUtils
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val urbanAreasService: UrbanAreasService,
    app: Application
) : AndroidViewModel(app) {
    val TAG = "MainViewModel"

    private val listOfUrbanAreaNamesHrefs = mutableMapOf<String, String>()
    val listOfUrbanAreaInfo = mutableListOf<UrbanAreaInfo>()
    var selectedUrbanAreaInfo = mutableStateOf<UrbanAreaInfo?>(null)

    var result = MutableLiveData<Result<List<UrbanAreaInfo>>>()

    init {
        Log.d(TAG, "init")
        getUrbanAreas()
    }

    fun getUrbanAreas() {
        viewModelScope.launch {
            result.value = Result.Loading()
            if (!NetworkUtils.isOnline(getApplication())) {
                result.value = Result.Error("No Network Connection")
                return@launch
            }

            try {
                val urbanAreas = urbanAreasService.getUrbanAreas()
                urbanAreas.links.uaItem.forEach { uaItem ->
                    val startIndex = uaItem.href.indexOfLast { it == ':' }
                    val endIndex = uaItem.href.length - 1
                    val href = uaItem.href.substring(startIndex + 1, endIndex)
                    listOfUrbanAreaNamesHrefs[uaItem.name] = href

                }

                val deferred = async {
                    repeat(6) { i ->
                        async {
                            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(i))
                        }
                    }
                }
                awaitAll(deferred)
                result.value = Result.Success(listOfUrbanAreaInfo)
            } catch (e: Error) {
                result.value = Result.Error("Error while fetching from api")

            }

        }
    }

    suspend fun getUrbanInfo(name: String) {
        val urbanAreaInfo = urbanAreasService.getUrbanAreaInfo(name)
        urbanAreaInfo.salaries = urbanAreasService.getUrbanAreaSalaries(name)
        urbanAreaInfo.scores = urbanAreasService.getUrbanAreaScores(name)
        urbanAreaInfo.imgUrl = urbanAreasService.getUrbanAreaImage(name).photos[0].image.mobile
        listOfUrbanAreaInfo.add(urbanAreaInfo)
    }
}