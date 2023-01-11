package `in`.vj.gymmanager

import `in`.vj.gymmanager.databinding.AddSignatureActicityBinding
import android.Manifest
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import com.jraska.falcon.Falcon
import java.io.*
import java.util.*


class SignatureActivity : AppCompatActivity() {
    private lateinit var binding: AddSignatureActicityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddSignatureActicityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)
        initialize()
    }

    private fun initialize() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        val calendar = Calendar.getInstance()
        binding.etjoindate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, p1, p2, p3 ->
                    binding.etjoindate.setText(
                        String.format(
                            "%d/%d/%d", p3, p2+1, p1
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        binding.saveButton.setOnClickListener {

            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show()
            } else if (binding.etjoindate.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Join Date", Toast.LENGTH_SHORT).show()
            } else {
                binding.saveButton.isVisible = false
                binding.clearButton.isVisible = false
                this.currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }

                val bitmap = getScreenShotFromView(binding.rlmain)

                if (bitmap != null) {
                    saveMediaToStorage(bitmap)
                }
                /*val myBitmap = getScreenShotFromView(binding.rlmain)
                if (myBitmap != null) {
                    storeImage(myBitmap, binding.etName.text.toString())
                }
                else
                {
                    Toast.makeText(this, "Screen shot error", Toast.LENGTH_SHORT).show()
                }*/
            }
        }


        binding.clearButton.setOnClickListener {
            binding.signaturePad.clear()
        }


    }

    fun getScreenShotFromView(v: View): Bitmap? {
        val bitmap = Falcon.takeScreenshotBitmap(this)
        return bitmap
    }

    fun storeImage(pictureBitmap: Bitmap, filename: String): Boolean {
        val wrapper = ContextWrapper(this)
        var file = wrapper.getDir("Images", Context.MODE_APPEND)
        file = File(file,"${binding.etName.text.toString()}.jpg")
        val stream: OutputStream = FileOutputStream(file)
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG,25,stream)
        stream.flush()
        stream.close()
        return true
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        // Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }
}