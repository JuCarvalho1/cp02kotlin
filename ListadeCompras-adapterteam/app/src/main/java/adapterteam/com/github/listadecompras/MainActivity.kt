package adapterteam.com.github.listadecompras
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import adapterteam.com.github.listadecompras.ItemsAdapter
import carreiras.com.github.listadecompras.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemsAdapter(this)
        recyclerView.adapter = itemsAdapter

        // Adiciona o LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addButton = findViewById<Button>(R.id.addButton)
        val editText = findViewById<EditText>(R.id.editText)
        val deleteAllButton = findViewById<Button>(R.id.deleteAllButton)

        addButton.setOnClickListener {
            if (editText.text.isEmpty()) {
                editText.error = "Preencha um valor"
                return@setOnClickListener
            }

            val item = ItemModel(
                name = editText.text.toString(),
                onRemove = {
                    itemsAdapter.removeItem(it)
                }
            )

            itemsAdapter.addItem(item)
            editText.text.clear()
        }

        deleteAllButton.setOnClickListener {
            itemsAdapter.clearAllItems()
        }
    }
}
