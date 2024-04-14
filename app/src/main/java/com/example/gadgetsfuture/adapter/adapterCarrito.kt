import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gadgetsfuture.Cart_fragment
import com.example.gadgetsfuture.R
import com.example.gadgetsfuture.config.config
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.text.NumberFormat
import java.util.Locale

class adapterCarrito (var context: Context?, var listaCarrito: JSONArray)
    : RecyclerView.Adapter<adapterCarrito.MyHolder>() {

    inner class MyHolder(Item: View) : RecyclerView.ViewHolder(Item) {
        lateinit var lblNombre: TextView
        lateinit var imgProducto: ImageView
        lateinit var txtCantidad: TextView
        lateinit var lblPrecio: TextView
        lateinit var lblSubtotal: TextView
        lateinit var btnEliminarCarrito: ImageButton

        init {
            lblNombre = itemView.findViewById(R.id.lblNombreCart)
            imgProducto = itemView.findViewById(R.id.imgProductoCart)
            txtCantidad = itemView.findViewById(R.id.txtCantidad)
            lblPrecio = itemView.findViewById(R.id.lblPrecioCart)
            lblSubtotal = itemView.findViewById(R.id.lblSubtotalCart)
            btnEliminarCarrito = itemView.findViewById(R.id.btnEliminarCart)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_carro, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val carrito = listaCarrito.getJSONObject(position)

        var nombre = carrito.getString("producto")
        var imagen = config.urlBase + carrito.getString("imagen")
        var cantidad = carrito.getInt("cantidad")
        val precio = carrito.getDouble("precio")
        var subtotal = cantidad * precio

        if (nombre.length >= 40) {
            nombre = nombre.substring(0, 39) + "..."
        }

        val formato = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        formato.maximumFractionDigits = 0
        val formatoPrecio = formato.format(precio)
        val formatoSubtotal = formato.format(subtotal)

        holder.lblNombre.text = nombre
        holder.txtCantidad.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val nuevaCantidad = s?.toString()?.toIntOrNull() ?: 0
                val idCarrito = carrito.getInt("id")
                GlobalScope.launch {
                    //actu(idCarrito, nuevaCantidad)
                }
            }
        })

        holder.lblPrecio.text = "$formatoPrecio"
        holder.lblSubtotal.text = "$formatoSubtotal"
        Glide.with(holder.itemView.context).load(imagen).into(holder.imgProducto)

        holder.btnEliminarCarrito.setOnClickListener {
            val carrito = listaCarrito.getJSONObject(position)
            val idCarrito = carrito.getInt("id")
            eliminarItemDelCarrito(idCarrito)
        }
    }

    private fun eliminarItemDelCarrito(id: Int) {
        GlobalScope.launch {
            try {
                (context as? Cart_fragment)?.eliminarCarrito(id)
            } catch (error: Exception) {
                Toast.makeText(context, "Error en la petición: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return listaCarrito.length()
    }
}