package com.lrgs18120163.sicedroid.data

import android.util.Log
import com.lrgs18120163.sicedroid.model.AccesoAlumno
import com.lrgs18120163.sicedroid.network.AccesoAlumnoApi
import com.lrgs18120163.sicedroid.network.DatosAlumnoApi
import com.lrgs18120163.sicedroid.model.AccesoAlumnoEnvelope
import com.lrgs18120163.sicedroid.model.CalificacionesFinalesEnvelope
import com.lrgs18120163.sicedroid.model.CalificacionesUnidadEnvelope
import com.lrgs18120163.sicedroid.model.CargaAcademicaEnvelope
import com.lrgs18120163.sicedroid.model.DatosAlumnoEnvelope
import com.lrgs18120163.sicedroid.model.KardexEnvelope
import com.lrgs18120163.sicedroid.network.CalificacionesFinalesApi
import com.lrgs18120163.sicedroid.network.CalificacionesUnidadApi
import com.lrgs18120163.sicedroid.network.CargaAcademicaApi
import com.lrgs18120163.sicedroid.network.KardexApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.simpleframework.xml.core.Persister
import java.io.IOException
import java.io.StringReader

private const val TAG_SUCCESS = "SUCCESS"
private const val TAG_ERROR = "ERROR"
class Repositorio(
    private val accesoAlumnoApi: AccesoAlumnoApi,
    private val datosAlumnoApi: DatosAlumnoApi,
    private val cargaAcademicaApi: CargaAcademicaApi,
    private val kardexApi: KardexApi,
    private val calificacionesUnidadApi: CalificacionesUnidadApi,
    private val calificacionesFinalesApi: CalificacionesFinalesApi,
) {

    suspend fun getAcceso(matricula: String, contrasenia: String, tipoUsuario: String): String? {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${matricula}</strMatricula>
                  <strContrasenia>${contrasenia}</strContrasenia>
                  <tipoUsuario>${tipoUsuario}</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody = xml.toRequestBody("application/soap+xml".toMediaType())
        return try {
            val response = accesoAlumnoApi.getAcceso(requestBody)
            val responseBodyString = response.string()

            val serializer = Persister()
            val reader = StringReader(responseBodyString)
            val envelope = serializer.read(AccesoAlumnoEnvelope::class.java, reader)
            var respuestaJson = envelope.body?.response?.result.toString()

            // Utiliza Gson para convertir el JSON a un objeto Kotlin
            respuestaJson
        } catch (e: IOException){
            Log.e(TAG_ERROR,"${e.message}")
            ""
        }
    }

    suspend fun getAlumnoAcademicoWithLineamiento(): String? {
        val xml =
            "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <getAlumnoAcademicoWithLineamiento xmlns=\"http://tempuri.org/\" />\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>".trimIndent()
        val requestBody = xml.toRequestBody("application/soap+xml".toMediaType())
        return try {
            val response = datosAlumnoApi.getAlumnoAcademicoWithLineamiento(requestBody)
            val responseBodyString = response.string()
            val serializer = Persister()
            val reader = StringReader(responseBodyString)
            val envelope = serializer.read(DatosAlumnoEnvelope::class.java, reader)

            val jsonString = envelope.body?.response?.result.toString()
            jsonString
        } catch (e: IOException) {
            Log.e(TAG_ERROR, "${e.message}")
            ""
        }
    }
    suspend fun getCargaAcademicaByAlumno(): String?{
        val xml = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <getCargaAcademicaByAlumno xmlns=\"http://tempuri.org/\" />\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>".trimIndent()
        val requestBody = xml.toRequestBody("application/soap+xml".toMediaType())
        return try {
            val response = cargaAcademicaApi.getCargaAcademicaByAlumno(requestBody)
            val responseBodyString = response.string()
            val serializer = Persister()
            val reader = StringReader(responseBodyString)
            val envelope = serializer.read(CargaAcademicaEnvelope::class.java, reader)

            val respuestaJson = envelope.body?.response?.result.toString()
            respuestaJson
        } catch (e: IOException) {
            Log.e(TAG_ERROR, "${e.message}")
            ""
        }
    }
    suspend fun getAllKardexConPromedioByAlumno(lineamiento: Int): String?{
        val xml = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <getAllKardexConPromedioByAlumno xmlns=\"http://tempuri.org/\">\n" +
                "      <aluLineamiento>${lineamiento}</aluLineamiento>\n" +
                "    </getAllKardexConPromedioByAlumno>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>".trimIndent()
        val requestBody = xml.toRequestBody("application/soap+xml".toMediaType())
        return try {
            val response = kardexApi.getAllKardexConPromedioByAlumno(requestBody)
            val responseBodyString = response.string()
            val serializer = Persister()
            val reader = StringReader(responseBodyString)
            val envelope = serializer.read(KardexEnvelope::class.java, reader)

            val respuestaJson = envelope.body?.response?.result.toString()
            respuestaJson
        } catch (e: IOException) {
            Log.e(TAG_ERROR, "${e.message}")
            ""
        }
    }
    suspend fun getCalifUnidadesByAlumno():String?{
        val xml = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <getCalifUnidadesByAlumno xmlns=\"http://tempuri.org/\" />\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>".trimIndent()
        val requestBody = xml.toRequestBody("application/soap+xml".toMediaType())
        return try {
            val response = calificacionesUnidadApi.getCalifUnidadesByAlumno(requestBody)
            val responseBodyString = response.string()
            val serializer = Persister()
            val reader = StringReader(responseBodyString)
            val envelope = serializer.read(CalificacionesUnidadEnvelope::class.java, reader)

            val respuestaJson = envelope.body?.response?.result.toString()
            Log.d(TAG_SUCCESS, respuestaJson)
            respuestaJson
        } catch (e: IOException) {
            Log.e(TAG_ERROR, "${e.message}")
            ""
        }
    }
    suspend fun getAllCalifFinalByAlumnos(modeloEducativo: Int):String?{
        val xml = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <getAllCalifFinalByAlumnos xmlns=\"http://tempuri.org/\">\n" +
                "      <bytModEducativo>${modeloEducativo}</bytModEducativo>\n" +
                "    </getAllCalifFinalByAlumnos>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>".trimIndent()
        val requestBody= xml.toRequestBody("application/soap+xml".toMediaType())
        return try {
            val response = calificacionesFinalesApi.getAllCalifFinalByAlumnos(requestBody)
            val responseBodyString = response.string()
            val serializer = Persister()
            val reader = StringReader(responseBodyString)
            val envelope = serializer.read(CalificacionesFinalesEnvelope::class.java, reader)

            val respuestaJson = envelope.body?.response?.result.toString()
            respuestaJson
        } catch (e: IOException) {
            Log.e(TAG_ERROR, "${e.message}")
            ""
        }
    }

}
