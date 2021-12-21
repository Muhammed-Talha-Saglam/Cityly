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
import dev.bytecode.cityly.data.network.UrbanAreaService
import dev.bytecode.cityly.utilities.NetworkUtils
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val urbanAreaService: UrbanAreaService,
    app: Application
) : AndroidViewModel(app) {
    val TAG = "MainViewModel"

    val questionRatingMap = mutableMapOf("Q1" to 1, "Q2" to 1,  "Q3" to 1, "Q4" to 1, "Q5" to 1, "Q6" to 1, "Q7" to 1 )

    private val listOfUrbanAreaNamesHrefs = mutableMapOf<String, String>()
    val listOfUrbanAreaInfo = mutableListOf<UrbanAreaInfo>()
    var selectedUrbanAreaInfo = mutableStateOf<UrbanAreaInfo?>(null)

    var result = MutableLiveData<Result<List<UrbanAreaInfo>>>()

    init {
        Log.d(TAG, "init")
        getUrbanAreas()
    }

    fun updateQuestionRating(question: String, rating: Int) {
        questionRatingMap[question] = rating
        Log.d(TAG, questionRatingMap.toString())
    }

    fun getUrbanAreas() {
        viewModelScope.launch {
            result.value = Result.Loading()
            if (!NetworkUtils.isOnline(getApplication())) {
                result.value = Result.Error("No Network Connection")
                return@launch
            }

            try {
                // Get urban area href urls
                val urbanAreas = urbanAreaService.getAllUrbanAreas()
                urbanAreas!!.links.uaItem.forEach { uaItem ->
                    val startIndex = uaItem.href.indexOfLast { it == ':' }
                    val endIndex = uaItem.href.length - 1
                    val href = uaItem.href.substring(startIndex + 1, endIndex)
                    listOfUrbanAreaNamesHrefs[uaItem.name] = href

                }

                // Launch all urban area requests in parallel for faster UX
                val job = launch {
                    repeat(6) { i ->
                        launch {
                            getUrbanInfo(listOfUrbanAreaNamesHrefs.values.elementAt(i))
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