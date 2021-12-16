package dev.bytecode.cityly.data.network

import dev.bytecode.cityly.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface UrbanAreasService {
    @GET(Connectivity.PATH_URBAN_AREAS)
    suspend fun getUrbanAreas(): UrbanAreas

    @GET(Connectivity.PATH_URBAN_AREA_INFO+"{name}/")
    suspend fun getUrbanAreaInfo(@Path("name") name: String): UrbanAreaInfo

    @GET(Connectivity.PATH_URBAN_AREA_INFO+"{name}/salaries/")
    suspend fun getUrbanAreaSalaries(@Path("name") name: String): Salaries

    @GET(Connectivity.PATH_URBAN_AREA_INFO+"{name}/scores/")
    suspend fun getUrbanAreaScores(@Path("name") name: String): Scores

    @GET(Connectivity.PATH_URBAN_AREA_INFO+"{name}/images/")
    suspend fun getUrbanAreaImage(@Path("name") name: String): Photos
}
