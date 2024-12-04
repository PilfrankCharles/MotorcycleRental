import androidx.compose.foundation.layout.* // Compose UI foundation
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.* // Material3 components
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManageMotorcycleDetailsScreen() {
    var motorcycleList by remember { mutableStateOf(listOf(Motorcycle("Honda CBR500R"), Motorcycle("Yamaha R3"))) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedMotorcycle by remember { mutableStateOf<Motorcycle?>(null) }
    var newMotorcycleName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(motorcycleList) { motorcycle ->
                MotorcycleItem(
                    motorcycle = motorcycle,
                    onEdit = {
                        selectedMotorcycle = motorcycle
                        newMotorcycleName = motorcycle.name
                        showDialog = true
                    },
                    onDelete = {
                        motorcycleList = motorcycleList.filter { it != motorcycle }
                    }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Edit Motorcycle") },
            text = {
                Column {
                    TextField(
                        value = newMotorcycleName,
                        onValueChange = { newMotorcycleName = it },
                        label = { Text("Motorcycle Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    // Update existing motorcycle
                    motorcycleList = motorcycleList.map {
                        if (it == selectedMotorcycle) Motorcycle(newMotorcycleName) else it
                    }
                    showDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun MotorcycleItem(
    motorcycle: Motorcycle,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(motorcycle.name, style = MaterialTheme.typography.bodyLarge)
            Row {
                IconButton(onClick = onEdit) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

data class Motorcycle(var name: String)
