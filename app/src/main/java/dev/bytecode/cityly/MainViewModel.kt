package dev.bytecode.cityly

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.bytecode.cityly.core.util.Resource
import dev.bytecode.cityly.model.UrbanAreaInfo
import dev.bytecode.cityly.model.states.MainUiState
import dev.bytecode.cityly.network.UrbanAreasService
import dev.bytecode.cityly.utilities.NetworkUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val urbanAreasService: UrbanAreasService,
    app: Application
) : AndroidViewModel(app) {
    val TAG = "MainViewModel"

    val listOfUrbanAreaNamesHrefs = mutableMapOf<String, String>()
    val listOfUrbanAreaInfo = mutableListOf<UrbanAreaInfo>()
    var uiState = mutableStateOf(MainUiState())
    var resource = MutableLiveData<Resource<List<UrbanAreaInfo>>>()

    init {
        getUrbanAreas()
    }

    fun getUrbanAreas() {
        viewModelScope.launch {
            resource.value = Resource.Loading()
            if (!NetworkUtils.isOnline(getApplication())) {
                delay(2000)
                resource.value = Resource.Error("No Network Connection")
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
                getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(0))
                getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(1))
                getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(2))
                getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(3))
                getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(4))
                getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(5))
                resource.value = Resource.Success(listOfUrbanAreaInfo)
            } catch (e: Error) {
                resource.value = Resource.Error("Error while fetching from api")

            }

        }
    }

    suspend fun getUrbanInfo(name: String) {
        val urbanAreaInfo = urbanAreasService.getUrbanAreaInfo(name)
        urbanAreaInfo.salaries = urbanAreasService.getUrbanAreaSalaries(name)
        urbanAreaInfo.scores = urbanAreasService.getUrbanAreaScores(name)
        listOfUrbanAreaInfo.add(urbanAreaInfo)
    }
}