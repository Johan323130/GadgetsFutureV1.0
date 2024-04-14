package com.example.gadgetsfuture.config

import android.content.SharedPreferences

class config {

    //crear una variable static
    companion object{
        lateinit var SharedPreferences: SharedPreferences
        //una variable para el token
        //una variable para el token
        var token:String = ""
        //crear una variable para almacenar la url base
        //puerto SENA:
        //const val urlBase="http://10.192.88.39:8000"
        //const val urlBase="http://192.168.42.32:8000"
        //puerto local:
        const val urlBase="http://192.168.1.12:8000"
        //const val urlBase="http://192.168.105.200:8000"

        const val urlTienda="${urlBase}/tienda/api/"
        const val urlCuenta="${urlBase}/cuenta/api/"
        const val urlCarrito="${urlBase}/carrito/api/"
    }
}