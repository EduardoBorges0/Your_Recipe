package com.app.receitas.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ImageFirebaseViewModel : ViewModel() {
     fun uploadImageToFirebase(uri: Uri, onSuccess: (String) -> Unit) {
        val userId = Firebase.auth.currentUser?.uid
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("images/$userId/${uri.lastPathSegment}")
        val uploadTask = storageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                onSuccess(downloadUri.toString())
                println("Imagem enviada com sucesso. URL: $downloadUri")
            }
        }.addOnFailureListener {
            println("Erro ao enviar imagem: ${it.message}")
        }
    }

}