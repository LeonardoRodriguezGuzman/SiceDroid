package com.lrgs18120163.sicedroid.data

import android.content.Context
import com.lrgs18120163.sicedroid.network.AccesoAlumnoApi
import com.lrgs18120163.sicedroid.network.AddCookiesInterceptor
import com.lrgs18120163.sicedroid.network.DatosAlumnoApi
import com.lrgs18120163.sicedroid.network.ReceivedCookiesInterceptor
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lrgs18120163.sicedroid.network.CalificacionesFinalesApi
import com.lrgs18120163.sicedroid.network.CalificacionesUnidadApi
import com.lrgs18120163.sicedroid.network.CargaAcademicaApi
import com.lrgs18120163.sicedroid.network.KardexApi
import okhttp3.MediaType.Companion.toMediaType

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val repositorio: Repositorio
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(val applicationContext: Context
): AppContainer {
    private val baseUrl = "https://sicenet.surguanajuato.tecnm.mx"

    private var clienteOKHttp : OkHttpClient
    var matricula: String? = null

    init {
        clienteOKHttp = OkHttpClient()
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(AddCookiesInterceptor(applicationContext))
        builder.addInterceptor(ReceivedCookiesInterceptor(applicationContext))
        clienteOKHttp = builder.build()
    }
    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(clienteOKHttp)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitAccesoAlumno: AccesoAlumnoApi by lazy {
        retrofit.create(AccesoAlumnoApi::class.java)
    }
    private val retrofitDatosAlumno: DatosAlumnoApi by lazy {
        retrofit.create(DatosAlumnoApi::class.java)
    }
    private val retrofitCargaAcademica: CargaAcademicaApi by lazy {
        retrofit.create(CargaAcademicaApi::class.java)
    }
    private val retrofitKardexApi: KardexApi by lazy {
        retrofit.create(KardexApi::class.java)
    }
    private val retrofitCalificacionesUnidadApi: CalificacionesUnidadApi by lazy {
        retrofit.create(CalificacionesUnidadApi::class.java)
    }
    private val retrofitCalificacionesFinalesApi: CalificacionesFinalesApi by lazy {
        retrofit.create(CalificacionesFinalesApi::class.java)
    }
    override val repositorio: Repositorio by lazy {
        Repositorio(
            retrofitAccesoAlumno,
            retrofitDatosAlumno,
            retrofitCargaAcademica,
            retrofitKardexApi,
            retrofitCalificacionesUnidadApi,
            retrofitCalificacionesFinalesApi
        )
    }


    private val repositoriolocal: Repositorio by lazy {
        Repositorio(
            retrofitAccesoAlumno,
            retrofitDatosAlumno,
            retrofitCargaAcademica,
            retrofitKardexApi,
            retrofitCalificacionesUnidadApi,
            retrofitCalificacionesFinalesApi,
        )
    }



    /**
     * DI implementation for Mars photos repository
     */


}


