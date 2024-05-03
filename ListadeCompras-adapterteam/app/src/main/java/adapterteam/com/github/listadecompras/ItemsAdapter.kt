package adapterteam.com.github.listadecompras

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import carreiras.com.github.listadecompras.R

// Adaptador para exibir itens em um RecyclerView.
class ItemsAdapter(private val context: Context) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<ItemModel>()

    // Adiciona um novo item à lista, verificando se já existe um item com o mesmo nome.
    fun addItem(newItem: ItemModel) {
        if (!isItemNameExists(newItem.name)) {
            items.add(newItem)
            notifyDataSetChanged()
            showNotification("Item adicionado: ${newItem.name}")
        } else {
            showNotification("O item ${newItem.name} já existe na lista.")
        }
    }

    // Remove um item da lista e notifica o RecyclerView para atualizar.
    fun removeItem(item: ItemModel) {
        items.remove(item)
        notifyDataSetChanged()
        showNotification("Item removido: ${item.name}")
    }

    // Limpa todos os itens da lista e notifica o RecyclerView para atualizar.
    fun clearAllItems() {
        // Exibe um diálogo de confirmação antes de limpar todos os itens.
        showClearConfirmationDialog()
    }

    // Exibe um diálogo de confirmação para limpar todos os itens.
    private fun showClearConfirmationDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar exclusão")
        builder.setMessage("Tem certeza de que deseja excluir todos os itens?")
        builder.setPositiveButton("Sim") { _, _ ->
            clearAllItemsConfirmed()
        }
        builder.setNegativeButton("Não", null)
        builder.show()
    }

    // Limpa todos os itens da lista após confirmação.
    private fun clearAllItemsConfirmed() {
        items.clear()
        notifyDataSetChanged()
    }

    // Cria novas visualizações (ViewHolder) conforme necessário.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
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
        val buttonDelete = view.findViewById<ImageButton>(R.id.imageButton)
        val buttonEdit = view.findViewById<ImageButton>(R.id.editButton)

        // Liga os dados do item às Views.
        fun bind(item: ItemModel) {
            textView.text = item.name

            // Define um listener para o botão de remoção do item.
            buttonDelete.setOnClickListener {
                showDeleteConfirmationDialog(item)
            }

            // Define um listener para o botão de edição do item.
            buttonEdit.setOnClickListener {
                showEditDialog(item)
            }
        }

        // Exibe um diálogo de confirmação antes de remover o item.
        private fun showDeleteConfirmationDialog(item: ItemModel) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmar exclusão")
            builder.setMessage("Tem certeza de que deseja excluir este item?")
            builder.setPositiveButton("Sim") { _, _ ->
                removeItem(item)
            }
            builder.setNegativeButton("Não", null)
            builder.show()
        }

        // Exibe um diálogo para editar o nome do item.
        private fun showEditDialog(item: ItemModel) {
            val editText = EditText(context)
            editText.setText(item.name)

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar Item")
            builder.setView(editText)
            builder.setPositiveButton("Salvar") { _, _ ->
                val newName = editText.text.toString()
                if (newName.isNotBlank()) {
                    // Verifica se já existe um item com o mesmo nome na lista, excluindo o item atual.
                    val existingItem = items.find { it != item && it.name == newName }
                    if (existingItem != null) {
                        // Remove o item existente com o mesmo nome.
                        removeItem(existingItem)
                    }
                    // Atualiza o nome do item atual.
                    item.name = newName
                    notifyDataSetChanged()
                }
            }
            builder.setNegativeButton("Cancelar", null)
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

    // Exibe uma notificação.
    private fun showNotification(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
