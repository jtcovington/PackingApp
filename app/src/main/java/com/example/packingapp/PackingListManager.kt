// app/src/main/java/com/example/packingapp/PackingListManager.kt
package com.example.packingapp

import kotlinx.coroutines.flow.Flow

/**
 * Helper class to manage packing lists and their items.
 * This class provides methods for common operations on packing lists,
 * abstracting away the direct database interactions.
 */
class PackingListManager(private val packingItemDao: PackingItemDao) {

    /**
     * Get all packing lists from the database.
     *
     * @return A Flow of List<PackingList> that will emit updates when data changes
     */
    fun getAllPackingLists(): Flow<List<PackingList>> {
        return packingItemDao.getAllPackingLists()
    }

    /**
     * Create a new packing list with default categories.
     *
     * @param name The name of the new packing list
     * @return The ID of the newly created packing list
     */
    suspend fun createNewPackingList(name: String): Int {
        // Create and insert a new packing list
        val listId = packingItemDao.insert(
            PackingList(
                listName = name,
                tripStartDate = null,
                tripEndDate = null
            )
        ).toInt()

        // Create default categories for this list
        createDefaultCategories(listId)

        return listId
    }

    /**
     * Create default categories for a new packing list.
     *
     * @param listId The ID of the packing list to create categories for
     */
    private suspend fun createDefaultCategories(listId: Int) {
        // Insert standard categories for Navy packing lists
        packingItemDao.insert(Category(categoryName = "Clothing", listId = listId))
        packingItemDao.insert(Category(categoryName = "Uniforms", listId = listId))
        packingItemDao.insert(Category(categoryName = "PT Gear", listId = listId))
        packingItemDao.insert(Category(categoryName = "Toiletries", listId = listId))
        packingItemDao.insert(Category(categoryName = "Documents", listId = listId))
        packingItemDao.insert(Category(categoryName = "Electronics", listId = listId))
        packingItemDao.insert(Category(categoryName = "Room Items", listId = listId))
        packingItemDao.insert(Category(categoryName = "Other", listId = listId))
    }

    /**
     * Update a packing list's dates.
     *
     * @param listId The ID of the packing list to update
     * @param startDate The new start date (in milliseconds) or null
     * @param endDate The new end date (in milliseconds) or null
     */
    suspend fun updatePackingListDates(listId: Int, startDate: Long?, endDate: Long?) {
        // Get the current packing list
        val packingList = packingItemDao.getPackingListById(listId) ?: return

        // Create an updated copy with new dates and update in the database
        packingItemDao.updatePackingList(
            packingList.copy(
                tripStartDate = startDate,
                tripEndDate = endDate
            )
        )
    }

    /**
     * Add a new item to a packing list.
     *
     * @param listId The ID of the packing list to add to
     * @param name The name of the item
     * @param categoryId The category ID for the item
     * @param quantity The initial quantity
     * @param baseQuantity The base quantity (for calculations)
     * @param quantityPerDay Additional quantity per day of trip
     */
    suspend fun addItemToList(
        listId: Int,
        name: String,
        categoryId: Int,
        quantity: Int,
        baseQuantity: Int = quantity,
        quantityPerDay: Int = 0
    ) {
        // Create and insert a new packing item
        val newItem = PackingItem(
            name = name,
            categoryId = categoryId,
            packed = false,
            quantity = quantity,
            listId = listId,
            baseQuantity = baseQuantity,
            quantityPerDay = quantityPerDay
        )
        packingItemDao.insert(newItem)
    }

    /**
     * Delete a packing list and all its items.
     * Note: Items will be deleted automatically through CASCADE foreign key.
     *
     * @param listId The ID of the packing list to delete
     */
    suspend fun deletePackingList(listId: Int) {
        val packingList = packingItemDao.getPackingListById(listId) ?: return
        packingItemDao.delete(packingList)
    }

    /**
     * Get all categories for a specific list.
     *
     * @param listId The ID of the packing list
     * @return A Flow of List<Category> that will emit updates when data changes
     */
    fun getCategoriesForList(listId: Int): Flow<List<Category>> {
        return packingItemDao.getCategoriesForList(listId)
    }
}