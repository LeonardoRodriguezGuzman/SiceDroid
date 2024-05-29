package com.lrgs18120163.sicedroid.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.GET

interface AccesoAlumnoApi {
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: \"http://tempuri.org/accesoLogin\""
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getAcceso(@Body requestBody: RequestBody) : ResponseBody

}



interface DatosAlumnoApi {
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: \"http://tempuri.org/getAlumnoAcademicoWithLineamiento\""
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getAlumnoAcademicoWithLineamiento(@Body requestBody: RequestBody): ResponseBody
}

// Carga Academica
interface CargaAcademicaApi{
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: \"http://tempuri.org/getCargaAcademicaByAlumno\""
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getCargaAcademicaByAlumno(@Body requestBody: RequestBody) : ResponseBody
}
// Kardex

interface KardexApi {
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: \"http://tempuri.org/getAllKardexConPromedioByAlumno\""
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getAllKardexConPromedioByAlumno(@Body requestBody: RequestBody) : ResponseBody
}
// Calificación Unidad
interface CalificacionesUnidadApi {
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: \"http://tempuri.org/getCalifUnidadesByAlumno\""
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getCalifUnidadesByAlumno(@Body requestBody: RequestBody) : ResponseBody
}
// Calificacion Final
interface CalificacionesFinalesApi {
    @Headers(
        "Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: \"http://tempuri.org/getAllCalifFinalByAlumnos\""
    )
    @POST("/ws/wsalumnos.asmx")
    suspend fun getAllCalifFinalByAlumnos(@Body requestBody: RequestBody) : ResponseBody
}