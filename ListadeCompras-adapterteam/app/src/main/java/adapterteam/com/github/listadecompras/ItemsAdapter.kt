package adapterteam.com.github.listadecompras

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import carreiras.com.github.listadecompras.R

// Adaptador para exibir itens em um RecyclerView.
class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<ItemModel>()

    // Adiciona um novo item à lista, verificando se já existe um item com o mesmo nome.
    fun addItem(newItem: ItemModel) {
        if (!isItemNameExists(newItem.name)) {
            items.add(newItem)
            notifyDataSetChanged()
        }
    }

    // Remove um item da lista e notifica o RecyclerView para atualizar.
    fun removeItem(item: ItemModel) {
        items.remove(item)
        notifyDataSetChanged()
    }

    // Cria novas visualizações (ViewHolder) conforme necessário.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    // Retorna o número total de itens na lista.
    override fun getItemCount(): Int = items.size

    // Liga os dados do item aos Views dentro do ViewHolder.
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    // ViewHolder para representar cada item na lista.
    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.textViewItem)
        val button = view.findViewById<ImageButton>(R.id.imageButton)

        // Liga os dados do item às Views.
        fun bind(item: ItemModel) {
            textView.text = item.name

            // Define um listener para o botão de remoção do item.
            button.setOnClickListener {
                showConfirmationDialog(item)
            }
        }

        // Exibe um diálogo de confirmação antes de remover o item.
        private fun showConfirmationDialog(item: ItemModel) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Confirmar exclusão")
            builder.setMessage("Tem certeza de que deseja excluir este item?")
            builder.setPositiveButton("Sim") { _, _ ->
                removeItem(item)
            }
            builder.setNegativeButton("Não", null)
            builder.show()
        }
    }

    // Verifica se já existe um item com o mesmo nome na lista.
    private fun isItemNameExists(name: String): Boolean {
        for (item in items) {
            if (item.name == name) {
                return true
            }
        }
        return false
    }
}
