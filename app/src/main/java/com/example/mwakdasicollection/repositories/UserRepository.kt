
import com.example.mwakdasicollection.model.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class UserRepository {
    private val usersRef = Firebase.firestore.collection("users")

    fun getUser(uid: String, onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {
        usersRef.document(uid).get()
            .addOnSuccessListener { snapshot ->
                snapshot.toObject(User::class.java)?.let(onSuccess)
            }
            .addOnFailureListener(onFailure)
    }
}
