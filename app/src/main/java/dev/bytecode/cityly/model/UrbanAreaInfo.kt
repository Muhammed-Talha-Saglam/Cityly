package dev.bytecode.cityly.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class UrbanAreaInfo (
    val continent: String,
    val mayor: String,
    val name: String,

    @SerializedName("full_name")
    val fullName: String,

    var salaries: Salaries,
    var scores: Scores,
    var imgUrl: String
) {
    fun toJson(): String? {
      return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): UrbanAreaInfo? {
            return Gson().fromJson(json,UrbanAreaInfo::class.java)
        }
    }
}
