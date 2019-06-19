package app.heartie.heartie

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_setup2.*

class Setup2Activity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71

    private var selectedFile: Uri? = null

    val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup2)

        ContinueButton.setOnClickListener {
            if (PhotoView.drawable == null) {
                UploadButton.error = "Please upload a photo first!"
            } else {
                val currentUser = FirebaseAuth.getInstance().currentUser

                // Create a storage reference from our app
                val storageRef = storage.reference
                var spaceRef = storageRef.child("images/" + currentUser?.uid.toString())
                var uploadTask = selectedFile?.let { it1 -> spaceRef.putFile(it1) }

                if (uploadTask != null) {
                    uploadTask.addOnFailureListener {
                        Toast.makeText(this, "Uknown error when uploading!", Toast.LENGTH_LONG).show()
                    }.addOnSuccessListener {
                        //TODO next page here
                    }
                }
            }
        }

        UploadButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            selectedFile = data?.data //The uri with the location of the file
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedFile)
            PhotoView.setImageBitmap(bitmap)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, SetupActivity::class.java)
        startActivity(intent)
    }
}
