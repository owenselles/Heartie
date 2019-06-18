package app.heartie.heartie

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_setup2.*

class Setup2Activity : AppCompatActivity() {

    val PICK_IMAGE_REQUEST = 71

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup2)

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
            val selectedFile = data?.data //The uri with the location of the file
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedFile)
            PhotoView.setImageBitmap(bitmap)
        }
    }
}
