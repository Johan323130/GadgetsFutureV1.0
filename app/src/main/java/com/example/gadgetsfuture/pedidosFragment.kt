package com.example.principal

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gadgetsfuture.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [pedidosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class pedidosFragment<T> : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)

        // Configuración para limitar los números en el EditText
        val telefono = view.findViewById<EditText>(R.id.numerotelefonico)
        telefono.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))

        // Configuración para el texto con subrayado
        val textView = view.findViewById<TextView>(R.id.agregarA)
        val texto = "Agregar apartamento, local (Opcional)"
        val spannableString = SpannableString(texto)
        spannableString.setSpan(UnderlineSpan(), 0, texto.length, 0)
        textView.text = spannableString

        // Configuración para mostrar u ocultar el EditText al hacer clic en el TextView
        val textA = view.findViewById<TextView>(R.id.agregarA)
        val editA = view.findViewById<EditText>(R.id.editocultar)
        textA.setOnClickListener {
            if (editA.visibility == View.VISIBLE) {
                editA.visibility = View.GONE
            } else {
                editA.visibility = View.VISIBLE
            }
        }

        // Configuración del Spinner de Departamentos
        val spinnerDepartamentos = view.findViewById<Spinner>(R.id.spinner)
        val spinnerCiudades = view.findViewById<Spinner>(R.id.spinnerciudad)

        val departamentos = arrayOf("Seleccionar Departamentos", "Huila", "Caldas", "Antioquia")
        val ciudadesDepartamento1 = arrayOf("Seleccionar", "Neiva", "Rivera", "Yaguará")
        val ciudadesDepartamento2 = arrayOf("Seleccionar", "Manizales", "La dorada", "Villa María")
        val ciudadesDepartamento3 = arrayOf("Seleccionar", "Medellin", "Caucasia", "Apartado")

        val largeTextSize = 60f // Tamaño de texto más grande

        val adapterDepartamentos = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, departamentos)
        adapterDepartamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDepartamentos.adapter = adapterDepartamentos

        spinnerDepartamentos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val departamentoSeleccionado = departamentos[position]

                val ciudadesArray = when (departamentoSeleccionado) {
                    "Huila" -> ciudadesDepartamento1
                    "Caldas" -> ciudadesDepartamento2
                    "Antioquia" -> ciudadesDepartamento3
                    else -> emptyArray()
                }

                val adapterCiudades = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ciudadesArray)
                adapterCiudades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCiudades.adapter = adapterCiudades
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No es necesario hacer nada en este caso
            }
        }

        // Configuración del botón de continuar pago
        val btnContinuarPago = view.findViewById<Button>(R.id.btnContinuarPago)
        btnContinuarPago.setOnClickListener {
            // Obtener referencias a los EditTexts
            val txtNombre = view.findViewById<EditText>(R.id.nombre)
            val txtApellido = view.findViewById<EditText>(R.id.apellido)
            val txtDireccion = view.findViewById<EditText>(R.id.direccion)
            val txtEmail = view.findViewById<EditText>(R.id.editTextEmail)
            val txtTelefono = view.findViewById<EditText>(R.id.numerotelefonico)
            val txtCodigoPostal = view.findViewById<EditText>(R.id.codigopostal)
            // Variable para verificar si todos los campos son válidos


            var valido = true
            // Verificar si algún campo está vacío
            if (txtNombre.text.toString().isEmpty()) {
                txtNombre.error = "El nombre es requerido"
                valido = false
            }
            if (txtApellido.text.toString().isEmpty()) {
                txtApellido.error = "El apellido es requerido"
                valido = false
            }
            if (txtEmail.text.toString().isEmpty()) {
                txtEmail.error = "El email es requerido"
                valido = false
            }
            if (txtDireccion.text.toString().isEmpty()) {
                txtDireccion.error = "El dirección es requerido"
                valido = false
            }
            if (txtCodigoPostal.text.toString().isEmpty()) {
                txtCodigoPostal.error = "El codigo postal es requerido"
                valido = false
            }
            if (txtTelefono.text.toString().isEmpty()) {
                txtTelefono.error = "El teléfono es requerido"
                valido = false
            }
            // Verificar si el campo de correo electrónico está vacío o no cumple con el formato requerido
            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-z]+"
            val email = txtEmail.text.toString()
            if (email.isEmpty() || !email.matches(emailPattern.toRegex())) {
                txtEmail.error = "Ingrese un correo electrónico válido"
                valido = false
            }





            // Si todos los campos son válidos, iniciar la siguiente actividad
            if (valido) {
                val transaction = requireFragmentManager().beginTransaction()
                var fragment=pedidos2Fragment<Button>()
                transaction.replace(R.id.container, fragment).commit()
            }


            // Configuración del ImageButton para redireccionar a otra vista


        }

        return view
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment pedidosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pedidosFragment<Any>().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}