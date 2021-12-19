package dev.bytecode.cityly.data.network

import dev.bytecode.cityly.data.model.*
import io.ktor.client.*
import io.ktor.client.request.*

class UrbanAreaServiceImpl(
    private val client: HttpClient
): UrbanAreaService {

    override suspend fun getAllUrbanAreas(): UrbanAreas? {
        return try {
            client.get<UrbanAreas>(UrbanAreaService.Endpoints.GetAllUrbanAreas.url)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUrbanAreaInfo(name: String): UrbanAreaInfo? {
        return try {
            client.get<UrbanAreaInfo>(UrbanAreaService.Endpoints.GetUrbanAreaInfo.url+"$name/")
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUrbanAreaSalaries(name: String): Salaries? {
        return try {
            client.get<Salaries>(UrbanAreaService.Endpoints.GetUrbanAreaInfo.url + "$name/salaries/")
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUrbanAreaScores(name: String): Scores? {
        return try {
            client.get<Scores>(UrbanAreaService.Endpoints.GetUrbanAreaInfo.url + "$name/scores/")
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUrbanAreaImage(name: String): Photos? {
        return try {
            client.get<Photos>(UrbanAreaService.Endpoints.GetUrbanAreaInfo.url + "$name/images/")
        } catch (e: Exception) {
            null
        }
    }
}