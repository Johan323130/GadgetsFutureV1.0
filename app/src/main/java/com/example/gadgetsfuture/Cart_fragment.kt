package com.example.gadgetsfuture;

import adapterCarrito
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gadgetsfuture.config.config
import com.example.principal.pedidosFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Cart_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cart_fragment : Fragment() {

    lateinit var recyclerCarrito: RecyclerView
    lateinit var btnActualizar: Button
    lateinit var ID: TextView
    lateinit var cantidad: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        recyclerCarrito = view.findViewById(R.id.RVCart)
        btnActualizar = view.findViewById(R.id.btnActualizarCart)
        peticionMostarCarrito()

        view.findViewById<Button>(R.id.btnRealizarPedido).setOnClickListener {
            // Obtener una referencia al NavController y navegar al destino deseado
            val transaction = requireFragmentManager().beginTransaction()
            val fragment = pedidosFragment<Button>()
            transaction.replace(R.id.container, fragment).commit()
        }

        /*btnActualizar.setOnClickListener {
            GlobalScope.launch {
                try {
                    actualizarCarrito()
                } catch (error: Exception)    {
                    Toast.makeText(activity, "Error en la petición: {$error}", Toast.LENGTH_SHORT).show()
                }
            }
        }*/



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment cart.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Cart_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun peticionMostarCarrito() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                mostrarCarrito()
            } catch (error: Exception) {
                Toast.makeText(
                    activity,
                    "Error en el servidor, por favor conectate a internet",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    suspend fun mostrarCarrito() {
        var url = config.urlCarrito + "v2/mostrar_carrito/"
        var queue = Volley.newRequestQueue(activity)
        var request = object : JsonArrayRequest(
            Method.GET,
            url,
            null,
            { response ->
                cargarListaCarrito(response)
            },
            { error ->
                Toast.makeText(activity, "Error: $error", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                // Agregar el token de autenticación a los encabezados si está disponible en config
                if (config.token.isNotEmpty()) {
                    headers["Authorization"] = "Bearer ${config.token}"
                }
                return headers
            }
        }
        queue.add(request)

    }


    /** Corregir */
    suspend fun eliminarCarrito(id: Int) {
        //http://192.168.1.10:8000/carrito/api/v1/eliminar_carrito/
        var url = config.urlCarrito + "v1/eliminar_carrito/"
        var queue = Volley.newRequestQueue(activity)
        val parametro = JSONObject().apply {
            put("id", id)
        }

        var request = object : JsonObjectRequest(
            Method.DELETE,
            url,
            parametro,
            { response ->
                val message = response.getString("message")
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(activity, "Error: $error", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                if (config.token.isNotEmpty()) {
                    headers["Authorization"] = "Bearer ${config.token}"
                }
                return headers
            }
        }
        queue.add(request)

    }

    /*suspend fun actualizarCarrito(id: Int, cantidad: Int) {
        var url = config.urlCarrito + "v1/actualizar_carrito/"
        var queue = Volley.newRequestQueue(context)
        val parametros = JSONObject().apply {
            put("id", id)
            put("cantidad", cantidad)
        }
        var request = object : JsonObjectRequest(
            Request.Method.PUT,
            url,
            parametros,
            { response ->
                Toast.makeText(activity, "Actualizado", Toast.LENGTH_LONG).show()
                cargarListaCarrito(response.getJSONArray("listaCarrito"))

                // Cargar el carrito
                //var lista = JSONArray()
                //mostrarCarrito()
                //cargarListaCarrito(lista)
            },
            { error ->
                Toast.makeText(activity, "Error: $error", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                if (config.token.isNotEmpty()) {
                    headers["Authorization"] = "Bearer ${config.token}"
                }
                return headers
            }
        }
        queue.add(request)

    }*/

    suspend fun actualizarCarrito(id: Int, nuevaCantidad: Int) {
        // Construye la URL para la solicitud de actualización del carrito
        val url = config.urlCarrito + "v1/actualizar_carrito/"

        // Crea un objeto JSONObject con los parámetros de la solicitud
        val parametros = JSONObject().apply {
            put("id", id)
            put("cantidad", nuevaCantidad)
        }

        try {
            // Realiza la solicitud de actualización del carrito
            val response = withContext(Dispatchers.IO) {
                // Crea una nueva cola de solicitudes de Volley
                val queue = Volley.newRequestQueue(context)

                // Crea una solicitud JsonObjectRequest para la actualización del carrito
                val request = JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    parametros,
                    { response ->
                        // Procesa la respuesta del servidor aquí
                        // Por ejemplo, puedes mostrar un mensaje de éxito
                        Toast.makeText(activity, "Actualizado", Toast.LENGTH_LONG).show()
                        //cargarListaCarrito(response)
                    },
                    { error ->
                        // Manejo de errores en caso de que la solicitud falle
                        Toast.makeText(activity, "Error: $error", Toast.LENGTH_LONG).show()
                    }
                )

                // Añade la solicitud a la cola de solicitudes de Volley
                queue.add(request)
            }
        } catch (e: Exception) {
            // Manejo de errores en caso de que ocurra una excepción
            Toast.makeText(activity, "Error: $e", Toast.LENGTH_LONG).show()
        }
    }


    fun cargarListaCarrito(listaCarrito: JSONArray) {
        recyclerCarrito.layoutManager = LinearLayoutManager(activity)
        var adapter = adapterCarrito(activity, listaCarrito)

        /*adapter.onclick = { carrito ->
            var idCart = carrito.getInt("id")
            var canti = carrito.getInt("cantidad")
            //idCarrito = carrito.getInt("id")
            //cantidad = carrito.getInt("cantidad")

            /*GlobalScope.launch {
                try {
                    eliminarCarrito(idCart)
                } catch (error: Exception) {
                    Toast.makeText(
                        activity,
                        "Error en la petición: {$error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }*/

            /*btnActualizar.setOnClickListener {
                GlobalScope.launch {
                    try {
                        actualizarCarrito(idCart, canti)
                    } catch (error: Exception) {
                        Toast.makeText(
                            activity,
                            "Error en la petición: {$error}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }*/

        }*/
        //adapter.notifyDataSetChanged()
        recyclerCarrito.adapter = adapter
    }
}

    /*fun cargarListaCarrito(listaCarrito: JSONArray){
        recyclerCarrito.layoutManager= LinearLayoutManager(activity)
        var adapter= com.example.gadgetsfuture.adapterCarrito(activity, listaCarrito)
        adapter.onclick={carrito ->
            idCarrito=carrito.getInt("id")
            cantidad=carrito.getInt("cantidad")

            /*val bundle=Bundle().apply {
                putInt("idCarrito", idCarrito)
            }*/

            /*GlobalScope.launch {
                    try {
                        actualizarCarrito(idCarrito, cantidad)
                    } catch (error: Exception)    {
                        Toast.makeText(activity, "Error en la petición: {$error}", Toast.LENGTH_SHORT).show()
                    }
                }
            */

            /*GlobalScope.launch {
             try {
                 eliminarCarrito(idCarrito)
                 //val intent = Intent(activity, Cart_fragment::class.java)
                 //startActivity(intent)
             } catch (error: Exception)    {
                 Toast.makeText(activity, "Error en la petición: {$error}", Toast.LENGTH_SHORT).show()
             }
         }*/

        }
        recyclerCarrito.adapter=adapter
    }*/

    /*adapter.onclick= {
           val bundle=Bundle()
           bundle.putInt("id_carrito",it.getInt("id"))
           val transaction=requireFragmentManager().beginTransaction()
           var fragmento=detalle_producto()
           fragmento.arguments=bundle
           transaction.replace(R.id.container, fragmento).commit()
           transaction.addToBackStack(null)
       }*/

