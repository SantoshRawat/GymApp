package `in`.vj.gymmanager.ui

import `in`.vj.gymmanager.GymApp
import `in`.vj.gymmanager.databinding.ActivityMemberListBinding
import `in`.vj.gymmanager.entities.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.ColumnInfo

class MemberListActivity : AppCompatActivity() {
    private var users: List<User>? = null
    private lateinit var binding: ActivityMemberListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)
        initialize()

    }

    private fun initialize() {
        binding.btnAddMember.setOnClickListener {
            startActivity(Intent(this, AddMemberActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        users = (application as GymApp).getAppDb().userDao().getAll()
        if (users.isNullOrEmpty()) {
            binding.rvMemberList.visibility = View.GONE
            binding.ivNoData.visibility = View.VISIBLE
        } else {
            binding.ivNoData.visibility = View.GONE
            binding.rvMemberList.visibility = View.VISIBLE

            addMembersList()
        }
    }

    fun addMembersList() {
        binding.rvMemberList.layoutManager = LinearLayoutManager(this)
        val memberListAdapter= MemberListAdapter(this, object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return false
            }
        })
        binding.rvMemberList.adapter = memberListAdapter
        memberListAdapter.submitList(users)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    fun editUserInfo(user: User) {
        val intent = Intent(applicationContext, EditMemberActivity::class.java)
        intent.putExtra("uid", user.uid)
        intent.putExtra("name", user.name)
        intent.putExtra("address", user.address)
        intent.putExtra("phone", user.phoneNumber)
        intent.putExtra("aadhar", user.aadhar)
        intent.putExtra("height", user.height)
        intent.putExtra("weight", user.weight)
        intent.putExtra("neck", user.neck)
        intent.putExtra("weist", user.weist)
        intent.putExtra("fatper", user.fatper)
        intent.putExtra("subscriptiondate", user.subscriptiondate)
        intent.putExtra("subscriptiontilldate", user.subscriptiontilldate)
        intent.putExtra("fees", user.fees)
        intent.putExtra("imageuri", user.imageuri)
        startActivity(intent)
    }

}