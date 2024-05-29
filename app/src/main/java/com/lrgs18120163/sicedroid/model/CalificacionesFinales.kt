package com.lrgs18120163.sicedroid.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class CalificacionesFinales (
    @SerialName("grupo")
    val grupo : String = "",
    @SerialName("materia")
    val materia : String = "",
    @SerialName("calif")
    val calif : Int = 0,
    @SerialName("acred")
    val acred : String = "",
    @SerialName("Observaciones")
    val observaciones : String = ""
)
