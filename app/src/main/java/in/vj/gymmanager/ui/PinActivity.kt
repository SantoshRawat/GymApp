package `in`.vj.gymmanager.ui

import `in`.vj.gymmanager.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        val etpin = findViewById<EditText>(R.id.etPin)
        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            val pin =etpin.text.toString()
            if (pin.equals("1234")){
            startActivity(Intent(this@PinActivity, HomeActivity::class.java))
            finish()}
            else
            {
                Toast.makeText(this,"Please enter correct pin",Toast.LENGTH_SHORT).show()
            }
        }
    }
}