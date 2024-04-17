package com.example.gadgetsfuture

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gadgetsfuture.config.config
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class Editar_datos : AppCompatActivity() {

    private lateinit var nombreCliente: TextView
    private lateinit var apellidosCliente: TextView
    private lateinit var numeroCliente: TextView
    private lateinit var txtName: EditText
    private lateinit var txtApe: EditText
    private lateinit var txtCel: EditText
    //private lateinit var txtPass: EditText
    //private lateinit var txtConfiPass: EditText
    private lateinit var nameError: TextView
    private lateinit var apeError: TextView
    private lateinit var celError: TextView
    //private lateinit var passError: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_datos)

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        nombreCliente = findViewById(R.id.nombreCliente)
        apellidosCliente = findViewById(R.id.apellidosCliente)
        numeroCliente = findViewById(R.id.numeroCLiente)

        txtName = findViewById(R.id.txtName)
        txtApe = findViewById(R.id.txtApe)
        txtCel = findViewById(R.id.txtCel)
        //txtPass = findViewById(R.id.txtPass)
        //txtConfiPass = findViewById(R.id.txtConfiPass)

        nameError = findViewById(R.id.nameError)
        apeError = findViewById(R.id.apeError)
        celError = findViewById(R.id.celError)
        //passError = findViewById(R.id.passError)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            validarCampos()
        }

        busca_cliente()
    }

    private fun validarCampos() {
        val nombre = txtName.text.toString()
        val apellido = txtApe.text.toString()
        val celular = txtCel.text.toString()
        //val contra = txtPass.text.toString()
        //val confirmContra = txtConfiPass.text.toString()

        var isValid = true

        if (nombre.isEmpty()) {
            nameError.visibility = View.VISIBLE
            nameError.text = "El nombre no es válido"
            isValid = false
        } else {
            nameError.visibility = View.VISIBLE
            nameError.text = ""
        }

        if (apellido.isEmpty()) {
            apeError.visibility = View.VISIBLE
            apeError.text = "El apellido es inválido"
            isValid = false
        } else {
            apeError.visibility = View.VISIBLE
            apeError.text = ""
        }

        if (celular.isEmpty()) {
            celError.visibility = View.VISIBLE
            celError.text = "El número celular es inválido"
            isValid = false
        } else {
            celError.visibility = View.VISIBLE
            celError.text = ""
        }

        /*if (contra.isEmpty()) {
            passError.visibility = View.VISIBLE
            passError.text = "La contraseña es requerida"
            isValid = false
        } else {
            passError.visibility = View.VISIBLE
            passError.text = ""
        }

        if (confirmContra.isEmpty()) {
            passError.visibility = View.VISIBLE
            passError.text = "La confirmación de contraseña es requerida"
            isValid = false
        } else if (contra != confirmContra) {
            passError.visibility = View.VISIBLE
            passError.text = "Las contraseñas no coinciden"
            isValid = false
        } else {
            passError.visibility = View.VISIBLE
            passError.text = ""
        }*/

        if (isValid) {
            // Aquí podré realizar el registro del usuario
            // llamar a un método en el ViewModel o Presenter
        }

    }

    fun busca_cliente(){
        GlobalScope.launch {
            try {
                peticion_cliente()
            }catch (error:Exception){
            }
        }
    }

    suspend fun peticion_cliente(){
        val url = config.urlCuenta + "v1/profile/"
        val queue = Volley.newRequestQueue(activity)
        val request = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            { response ->
                var nombre = response.getString("nombre")
                var apellido = response.getString("apellido")
                var numeroC = response.getString("numero")
                nombreCliente.setText(nombre)
                apellidosCliente.setText(apellido)
                numeroCliente.setText(numeroC)
            },
            { error ->
                Toast.makeText(
                    activity,
                    "Error en el servidor: $error.networkResponse.statusCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            // Aquí agregamos el token JWT a los encabezados de la solicitud
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Obtener el token JWT de tu configuración y agregarlo a los encabezados
                if (config.token.isNotEmpty()) {
                    headers["Authorization"] = "Bearer "+config.token
                }
                return headers
            }
        }
        queue.add(request)
    }
}