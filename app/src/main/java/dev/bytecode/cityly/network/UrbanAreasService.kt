package dev.bytecode.cityly.network

import dev.bytecode.cityly.model.Salaries
import dev.bytecode.cityly.model.Scores
import dev.bytecode.cityly.model.UrbanAreaInfo
import dev.bytecode.cityly.model.UrbanAreas
import retrofit2.http.GET
import retrofit2.http.Path

interface UrbanAreasService {
    @GET(Constants.PATH_URBAN_AREAS)
    suspend fun getUrbanAreas(): UrbanAreas

    @GET(Constants.PATH_URBAN_AREA_INFO+"{name}/")
    suspend fun getUrbanAreaInfo(@Path("name") name: String): UrbanAreaInfo

    @GET(Constants.PATH_URBAN_AREA_INFO+"{name}/salaries/")
    suspend fun getUrbanAreaSalaries(@Path("name") name: String): Salaries

    @GET(Constants.PATH_URBAN_AREA_INFO+"{name}/scores/")
    suspend fun getUrbanAreaScores(@Path("name") name: String): Scores
}