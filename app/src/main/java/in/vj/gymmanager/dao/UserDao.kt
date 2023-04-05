package `in`.vj.gymmanager.dao

import `in`.vj.gymmanager.entities.User
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT count(*) FROM user")
    fun getCount(): Int


    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): User

    @Query("UPDATE User SET subscriptiontilldate = (:name) where uid = (:id)")
    fun updateTillDate(name: String, id: String)

    @Query("UPDATE User SET name = (:name)  where uid = (:id)")
    fun updateUser(name: String,id:Int)

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}