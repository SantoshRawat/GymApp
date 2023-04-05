package `in`.vj.gymmanager.ui

import `in`.vj.gymmanager.GymApp
import `in`.vj.gymmanager.R
import `in`.vj.gymmanager.databinding.ItemUserListBinding
import `in`.vj.gymmanager.entities.User
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Build
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class MemberListAdapter internal constructor(
    val context: Context, diffUtilCallback: DiffUtil.ItemCallback<User>
) : ListAdapter<User, MemberListAdapter.UserListViewHolder>(diffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val itemUserBinding = ItemUserListBinding.inflate(
            LayoutInflater.from(
                context
            ), parent, false
        )
        return UserListViewHolder(itemUserBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserListViewHolder(private val itemUserListBinding: ItemUserListBinding) :
        RecyclerView.ViewHolder(itemUserListBinding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(user: User) {
            val calendar = Calendar.getInstance()
            itemUserListBinding.tvUserName.text = user.name
            itemUserListBinding.tvHeightWeight.text = "${user.height} INCH , ${user.weight} KG"
            itemUserListBinding.tvSubsriptionDate.text =
                "Subscription Date : ${user.subscriptiondate}"
            itemUserListBinding.tvSubsriptionTillDate.text =
                "Subscription Till : ${user.subscriptiontilldate}"
            itemUserListBinding.ivStory.setImageURI(Uri.parse("${user.imageuri}"))
            val strDate = SimpleDateFormat("dd/MM/yyyy")
            val date = Date()
            val datemain = strDate.parse("${user.subscriptiontilldate}")
            if (datemain.toInstant().isBefore(date.toInstant())) {
                itemUserListBinding.tvSubsriptionTillDate.setBackgroundResource(R.color.tomato)
                itemUserListBinding.ivMessage.isVisible = true
                itemUserListBinding.ivUpdate.isVisible = true

            } else {
                itemUserListBinding.ivMessage.isVisible = false
                itemUserListBinding.ivUpdate.isVisible = false
            }

            itemUserListBinding.ivMessage.setOnClickListener {
                //sendSMS("${user.phoneNumber}","Pleasehello")
                sendSMS(
                    " ${user.phoneNumber}",
                    "Hello This message is form Total Fitness Gym .Your Gym fees is pending please pay As soon as possible Thank You!!"
                )
            }
            itemUserListBinding.ivUpdate.setOnClickListener {
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, p1, p2, p3 ->
                        (context.applicationContext as GymApp).getAppDb().userDao().updateTillDate(
                            String.format(
                                "%d/%d/%d", p3, p2 + 1, p1
                            ), user.uid
                        )
                        val ac = context as MemberListActivity
                        ac.onBackPressed()
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
                //Toast.makeText(context,user.uid.toString(),Toast.LENGTH_SHORT).show()


            }

            itemUserListBinding.ivDelete.setOnClickListener {
                (context.applicationContext as GymApp).getAppDb().userDao().delete(user)
                (context as MemberListActivity).onBackPressed()

            }
            itemUserListBinding.ivUpdateUser.setOnClickListener {
                (context as MemberListActivity).editUserInfo(user)
            }




        }
    }


    fun sendSMS(phoneNo: String?, msg: String?) {
        try {

            // on below line we are initializing sms manager.
            //as after android 10 the getDefault function no longer works
            //so we have to check that if our android version is greater
            //than or equal toandroid version 6.0 i.e SDK 23
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                //if SDK is greater that or equal to 23 then
                //this is how we will initialize the SmsManager
                smsManager = context.getSystemService(SmsManager::class.java)
            } else {
                //if user's SDK is less than 23 then
                //SmsManager will be initialized like this
                smsManager = SmsManager.getDefault()
            }

            // on below line we are sending text message.
            smsManager.sendTextMessage(phoneNo, null, msg, null, null)

            // on below line we are displaying a toast message for message send,
            Toast.makeText(context.applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {

            // on catch block we are displaying toast message for error.
            Toast.makeText(
                context.applicationContext,
                "Please enter all the data.." + e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}