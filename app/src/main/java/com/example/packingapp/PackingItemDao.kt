package com.example.packingapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PackingItemDao {

    @Query("SELECT * FROM packing_items")
    fun getAllItems(): Flow<List<PackingItem>>

    @Query("SELECT * FROM packing_items WHERE tripType = :tripType AND tripDuration <= :duration")
    fun getItemsForTrip(tripType: String, duration: Int): Flow<List<PackingItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: PackingItem)

    @Update
    suspend fun update(item: PackingItem)

    @Delete
    suspend fun delete(item: PackingItem)

    @Query("DELETE FROM packing_items")
    suspend fun deleteAll()
}