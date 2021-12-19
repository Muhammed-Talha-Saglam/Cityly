package dev.bytecode.cityly.data.network

import dev.bytecode.cityly.data.model.*

interface UrbanAreaService {

    suspend fun getAllUrbanAreas(): UrbanAreas?
    suspend fun getUrbanAreaInfo(name: String): UrbanAreaInfo?
    suspend fun getUrbanAreaSalaries(name: String): Salaries?
    suspend fun getUrbanAreaScores(name: String): Scores?
    suspend fun getUrbanAreaImage(name: String): Photos?

    sealed class Endpoints(val url: String) {
        object GetAllUrbanAreas: Endpoints("${Connectivity.BASE_URL}${Connectivity.PATH_URBAN_AREAS}")
        object GetUrbanAreaInfo: Endpoints("${Connectivity.BASE_URL}${Connectivity.PATH_URBAN_AREA_INFO}")
    }

}