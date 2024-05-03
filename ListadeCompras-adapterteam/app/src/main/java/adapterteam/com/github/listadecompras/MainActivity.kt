package adapterteam.com.github.listadecompras

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import carreiras.com.github.listadecompras.R

// Activity principal que contém o RecyclerView e o formulário para adicionar itens.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemsAdapter()
        recyclerView.adapter = itemsAdapter

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)

        // Define um listener para o botão de adicionar item.
        button.setOnClickListener {
            // Verifica se o campo de texto está vazio.
            if (editText.text.isEmpty()) {
                editText.error = "Preencha um valor"
                return@setOnClickListener
            }

            // Cria um novo item com o texto do campo de texto e uma função de callback para remover o item.
            val item = ItemModel(
                name = editText.text.toString(),
                onRemove = {
                    itemsAdapter.removeItem(it)
                }
            )

            // Adiciona o item ao adapter e limpa o campo de texto.
            itemsAdapter.addItem(item)
            editText.text.clear()
        }
    }
}
