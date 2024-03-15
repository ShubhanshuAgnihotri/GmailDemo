package android.com.example.gmaildemo

data class Venues(
    val id: String,
    val name: String,
    val categories: List<Category>,
    var verified: Boolean
)