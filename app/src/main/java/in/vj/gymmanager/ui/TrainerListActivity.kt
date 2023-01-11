package `in`.vj.gymmanager.ui

import `in`.vj.gymmanager.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TrainerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)
        findViewById<Button>(R.id.btnAddMember).setOnClickListener {
            startActivity(Intent(this, AddMemberActivity::class.java))
        }
        supportActionBar?.setHomeButtonEnabled(true)
    }
}