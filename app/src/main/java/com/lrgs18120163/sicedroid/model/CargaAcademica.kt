package com.lrgs18120163.sicedroid.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CargaAcademica (
    @SerialName("Semipresencial")
    val semipresencial : String = "",
    @SerialName("Observaciones")
    val observaciones : String = "",
    @SerialName("Docente")
    val docente : String = "",
    @SerialName("clvOficial")
    val clvOficial : String = "",
    @SerialName("Sabado")
    val sabado : String = "",
    @SerialName("Viernes")
    val viernes : String = "",
    @SerialName("Jueves")
    val jueves : String = "",
    @SerialName("Miercoles")
    val miercoles : String = "",
    @SerialName("Martes")
    val martes : String = "",
    @SerialName("Lunes")
    val lunes : String = "",
    @SerialName("EstadoMateria")
    val estadoMateria : String = "",
    @SerialName("CreditosMateria")
    val creditosMateria : Int = 0,
    @SerialName("Materia")
    val materia : String = "",
    @SerialName("Grupo")
    val grupo : String = ""
)