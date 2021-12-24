package dev.bytecode.cityly.viewModels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.bytecode.cityly.data.Cities
import dev.bytecode.cityly.data.model.City
import dev.bytecode.cityly.data.model.Result
import dev.bytecode.cityly.data.model.UrbanAreaInfo
import dev.bytecode.cityly.data.network.UrbanAreaService
import dev.bytecode.cityly.utilities.NetworkUtils
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class MainViewModel @Inject constructor(
    private val urbanAreaService: UrbanAreaService,
    app: Application
) : AndroidViewModel(app) {

    val TAG = "MainViewModel"

    val isLoading = mutableStateOf(true)
    val listOfUrbanAreaInfo = mutableListOf<UrbanAreaInfo>()
    var selectedUrbanAreaInfo = mutableStateOf<UrbanAreaInfo?>(null)
    lateinit var cityHrefs: Array<City>

    var result = MutableLiveData<Result<List<UrbanAreaInfo>>>()

    init {
        viewModelScope.launch {
            isLoading.value = true
            val gson = Gson()
            val arrayCityType = object : TypeToken<Array<City>>() {}.type
            cityHrefs = gson.fromJson(Cities.jsonCities, arrayCityType)
            isLoading.value = false
        }
    }


    fun getUrbanAreas() {
        viewModelScope.launch {
            result.value = Result.Loading()
            if (!NetworkUtils.isOnline(getApplication())) {
                result.value = Result.Error("No Network Connection")
                return@launch
            }
            listOfUrbanAreaInfo.clear()
            try {
                // Launch all urban area requests in parallel for faster performance
                val job = launch {
                    repeat(6) { i ->
                        launch {
                            val index = Random.nextInt(cityHrefs.indices)
                            val href = cityHrefs[index].name.lowercase().replace(" ", "-")
                            getUrbanInfo(href)
                        }
                    }
                }

                // Wait until all request calls finish
                job.join()
                result.value = Result.Success(listOfUrbanAreaInfo)
            } catch (e: Error) {
                result.value = Result.Error("Error while fetching from api")

            }

        }
    }

    suspend fun getUrbanInfo(name: String)  {
        Log.d("getUrbanInfo", name)
        val urbanAreaInfo = urbanAreaService.getUrbanAreaInfo(name)
        urbanAreaInfo?.salaries = urbanAreaService.getUrbanAreaSalaries(name)
        urbanAreaInfo?.scores = urbanAreaService.getUrbanAreaScores(name)
        urbanAreaInfo?.imgUrl = urbanAreaService.getUrbanAreaImage(name)?.photos?.get(0)?.image?.mobile
        urbanAreaInfo?.let {
            listOfUrbanAreaInfo.add(urbanAreaInfo)
        }
    }
}