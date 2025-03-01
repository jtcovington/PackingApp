// app/src/main/java/com/example/packingapp/PackingItemDao.kt
package com.example.packingapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PackingItemDao {
    // Get all packing items for a specific list with their categories
    @Transaction
    @Query("SELECT * FROM packing_items WHERE listId = :listId")
    fun getItemsForList(listId: Int): Flow<List<PackingItemAndCategory>>

    // Get a specific packing list by ID
    @Query("SELECT * FROM packing_lists WHERE listId = :listId")
    suspend fun getPackingListById(listId: Int): PackingList?

    // Get all packing lists
    @Query("SELECT * FROM packing_lists ORDER BY listId ASC")
    fun getAllPackingLists(): Flow<List<PackingList>>

    // Insert a new packing item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: PackingItem)

    // Insert a new category and return its ID
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category): Long

    // Insert a new packing list and return its ID
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(packingList: PackingList): Long

    // Update a packing list
    @Update
    suspend fun updatePackingList(packingList: PackingList)

    // Update a packing item
    @Update
    suspend fun update(item: PackingItem)

    // Delete a packing item
    @Delete
    suspend fun delete(item: PackingItem)

    // Delete a packing list
    @Delete
    suspend fun delete(packingList: PackingList)

    // Get all categories for a specific list
    @Query("SELECT * FROM categories WHERE listId = :listId ORDER BY categoryName ASC")
    fun getCategoriesForList(listId: Int): Flow<List<Category>>

    // Get all items for a specific category within a list
    @Transaction
    @Query("SELECT * FROM packing_items WHERE listId = :listId AND categoryId = :categoryId")
    fun getItemsByCategoryAndList(listId: Int, categoryId: Int): Flow<List<PackingItemAndCategory>>
}