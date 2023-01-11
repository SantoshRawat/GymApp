package `in`.vj.gymmanager.ui

import `in`.vj.gymmanager.GymApp
import `in`.vj.gymmanager.R
import `in`.vj.gymmanager.databinding.ActivityAddMemberBinding
import `in`.vj.gymmanager.databinding.ActivityMainBinding
import `in`.vj.gymmanager.entities.User
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import java.util.Calendar
import java.util.Date

class AddMemberActivity : AppCompatActivity() {
    var imageuri = ""
    private lateinit var binding: ActivityAddMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)

        initialize()
    }

    private fun initialize() {
        val calendar = Calendar.getInstance()
        binding.tvSubsriptionDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, p1, p2, p3 ->
                    binding.tvSubsriptionDate.setText(
                        String.format(
                            "%d/%d/%d", p3, p2+1, p1
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.tvSubsriptionTillDate.setOnClickListener {
            DatePickerDialog(
                this,
                { _, p1, p2, p3 ->
                    binding.tvSubsriptionTillDate.setText(
                        String.format(
                            "%d/%d/%d", p3, p2+1, p1
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.ivStory.setOnClickListener {
            ImagePicker.with(this).cameraOnly()    //User can only capture image using Camera
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        binding.btnAdd.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Name", Toast.LENGTH_SHORT).show()
            } else if (binding.etAddress.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Address", Toast.LENGTH_SHORT).show()
            } else if (binding.etPhoneNumber.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Phone No", Toast.LENGTH_SHORT).show()
            } else if (binding.etAdhar.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Aadhar no", Toast.LENGTH_SHORT).show()
            } else if (binding.etHeight.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Height", Toast.LENGTH_SHORT).show()
            } else if (binding.etWeight.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Weight", Toast.LENGTH_SHORT).show()
            } else if (binding.etNack.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Nack", Toast.LENGTH_SHORT).show()
            } else if (binding.etWeist.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Weist", Toast.LENGTH_SHORT).show()
            } else if (binding.etFatPer.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter FatPer", Toast.LENGTH_SHORT).show()
            } else if (binding.tvSubsriptionDate.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Subsription Date", Toast.LENGTH_SHORT).show()
            } else if (binding.tvSubsriptionTillDate.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Subsription Till Date", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etFees.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Fees", Toast.LENGTH_SHORT).show()
            } else {


                binding.apply {
                    val name = etName.text.toString()
                    val address = etAddress.text.toString()
                    val phone = etPhoneNumber.text.toString()
                    val aadhar = etAdhar.text.toString()
                    val height = etHeight.text.toString()
                    val weight = etWeight.text.toString()
                    val neck = etNack.text.toString()
                    val weist = etWeist.text.toString()
                    val fatPer = etFatPer.text.toString()
                    val date = tvSubsriptionDate.text.toString()
                    val datetill = tvSubsriptionTillDate.text.toString()
                    val fees = etFees.text.toString()
                    val imageuri = imageuri


                    val user = User(
                        name = name,
                        address = address,
                        phoneNumber = phone,
                        aadhar = aadhar,
                        height = height,
                        weight = weight,
                        neck = neck,
                        weist = weist,
                        fatper = fatPer,
                        subscriptiondate = date,
                        subscriptiontilldate = datetill,
                        fees = fees,
                        imageuri = imageuri

                    )
                    addUserInDb(user)
                }
            }

        }
    }

    private fun addUserInDb(user: User) {
        (application as GymApp).getAppDb().userDao().insertAll(user)
        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                imageuri = fileUri.toString()
                //mProfileUri = fileUri
                binding.ivStory.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}