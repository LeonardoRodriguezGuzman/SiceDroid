package com.lrgs18120163.sicedroid.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kardex (
    @SerialName("S3")
    val s3 : String?,
    @SerialName("P3")
    val p3 : String?,
    @SerialName("A3")
    val a3 : String?,
    @SerialName("ClvMat")
    val clvMat : String,
    @SerialName("ClvOfiMat")
    val clvOfiMat : String,
    @SerialName("Materia")
    val materia : String,
    @SerialName("Cdts")
    val cdts : Int,
    @SerialName("Calif")
    val calif : Int,
    @SerialName("Acred")
    val acred : String,
    @SerialName("S1")
    val s1 : String,
    @SerialName("P1")
    val p1 : String,
    @SerialName("A1")
    val a1 : String,
    @SerialName("S2")
    val s2 : String?,
    @SerialName("P2")
    val p2 : String?,
    @SerialName("A2")
    val a2 : String?
)
