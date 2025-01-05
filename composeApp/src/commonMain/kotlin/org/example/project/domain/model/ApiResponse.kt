package org.example.project.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse (
    val metadata : MetaData,
    val data : Map<String, Currency>
)

@Serializable
data class MetaData(
    @SerialName("last_updated_at") //name in api is in () , name we will use is variable name in app
    val lastUpdatedAt : String
)

@Serializable
data class Currency(
    val code :String,
    val value : Double
)