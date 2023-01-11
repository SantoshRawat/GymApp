package `in`.vj.gymmanager.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "phone") val phoneNumber: String?,
    @ColumnInfo(name = "aadhar") val aadhar: String?,
    @ColumnInfo(name = "height") val height: String?,
    @ColumnInfo(name = "weight") val weight: String?,
    @ColumnInfo(name = "neck") val neck: String?,
    @ColumnInfo(name = "weist") val weist: String?,
    @ColumnInfo(name = "fatper") val fatper: String?,
    @ColumnInfo(name = "subscriptiondate") val subscriptiondate: String?,
    @ColumnInfo(name = "subscriptiontilldate") val subscriptiontilldate: String?,
    @ColumnInfo(name = "fees") val fees: String?,
    @ColumnInfo(name = "imageuri") val imageuri: String?,

) {
    override fun toString(): String {
        return "User(uid=$uid, name=$name, address=$address, phoneNumber=$phoneNumber,aadhar=$aadhar, height=$height, weight=$weight,neck=$neck,weist=$weist,fatper=$fatper,subscriptiondate=$subscriptiondate,subscriptiontilldate=$subscriptiontilldate, fees=$fees)"
    }
}