package `in`.vj.gymmanager.ui

import `in`.vj.gymmanager.GymApp
import `in`.vj.gymmanager.R
import `in`.vj.gymmanager.SignatureActivity
import `in`.vj.gymmanager.databinding.ActivityHomeBinding
import `in`.vj.gymmanager.entities.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.llMembers.setOnClickListener {
            startActivity(Intent(this, MemberListActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.llSignature).setOnClickListener {
            startActivity(Intent(this, SignatureActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.llbackup).setOnClickListener {
            startActivity(Intent(this, BackUpActivity::class.java))
        }


    }
}