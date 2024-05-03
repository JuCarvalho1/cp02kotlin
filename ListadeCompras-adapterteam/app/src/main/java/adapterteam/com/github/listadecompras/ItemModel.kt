package adapterteam.com.github.listadecompras

// Modelo de item que representa um item na lista de compras.
data class ItemModel(val name: String, val onRemove: (ItemModel) -> Unit)
