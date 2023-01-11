package `in`.vj.gymmanager.ui

import `in`.vj.gymmanager.AppDatabase
import `in`.vj.gymmanager.GymApp
import `in`.vj.gymmanager.databinding.BackupActivityBinding
import `in`.vj.gymmanager.entities.User
import android.content.ContentValues.TAG
import android.content.Intent

import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.raphaelebner.roomdatabasebackup.core.RoomBackup


class BackUpActivity : AppCompatActivity() {
    private lateinit var binding: BackupActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BackupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)
        initialize()
    }

    private fun initialize() {
        val backup = RoomBackup(this)
        binding.backupButton.setOnClickListener {
            backup
                .database((application as GymApp).getAppDb())
                .enableLogDebug(true)
                .backupIsEncrypted(false)
                .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_EXTERNAL)
                .maxFileCount(5)
                .apply {
                    onCompleteListener { success, message, exitCode ->
                        Log.d(TAG, "success: $success, message: $message, exitCode: $exitCode")
                        if (success) restartApp(Intent(this@BackUpActivity, HomeActivity::class.java))
                    }
                }
                .backup()
        }
        binding.restoreButton.setOnClickListener {
            backup
                .database((application as GymApp).getAppDb())
                .enableLogDebug(true)
                .backupIsEncrypted(false)
                .backupLocation(RoomBackup.BACKUP_FILE_LOCATION_EXTERNAL)
                .apply {
                    onCompleteListener { success, message, exitCode ->
                        Log.d(TAG, "success: $success, message: $message, exitCode: $exitCode")
                        if (success) restartApp(Intent(this@BackUpActivity, HomeActivity::class.java))
                    }
                }
                .restore()
        }
    }
}