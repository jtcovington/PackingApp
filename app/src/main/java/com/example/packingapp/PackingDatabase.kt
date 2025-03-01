// app/src/main/java/com/example/packingapp/PackingDatabase.kt
package com.example.packingapp

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PackingItem::class, Category::class, PackingList::class], version = 3, exportSchema = false) // Increased version, added entities
abstract class PackingDatabase : RoomDatabase() {

    abstract fun packingItemDao(): PackingItemDao // No changes to the DAO accessor

    companion object {
        @Volatile
        private var INSTANCE: PackingDatabase? = null

        fun getDatabase(context: Context): PackingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PackingDatabase::class.java,
                    "packing_database"
                )
                    .fallbackToDestructiveMigration() // Destructive migration for development
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("PackingDatabase", "onCreate called!")

                            CoroutineScope(Dispatchers.IO).launch {
                                val database = INSTANCE ?: run {
                                    Log.e("PackingDatabase", "INSTANCE is null in onCreate!")
                                    return@launch
                                }

                                database.withTransaction {
                                    val dao = database.packingItemDao()
                                    //PREVIOUS DATA POPULATION REMOVED
                                    // Add initial categories *and* their list IDs
                                    dao.insert(Category(categoryName = "Clothing", listId = 1)) // CategoryID 1
                                    dao.insert(Category(categoryName = "Uniforms", listId = 1)) // CategoryID 2
                                    dao.insert(Category(categoryName = "PT Gear", listId = 1))  // CategoryID 3
                                    dao.insert(Category(categoryName = "Toiletries", listId = 1))// CategoryID 4
                                    dao.insert(Category(categoryName = "Documents", listId = 1))// CategoryID 5
                                    dao.insert(Category(categoryName = "Electronics", listId = 1))//CategoryID 6
                                    dao.insert(Category(categoryName = "Room Items", listId = 1))// CategoryID 7
                                    dao.insert(Category(categoryName = "Other", listId = 1))     // CategoryID 8

                                    // Add a default packing list
                                    val listId = dao.insert(PackingList(listName = "Default List", tripStartDate = null, tripEndDate = null)) // Use the returned listId

                                    Log.d("PackingDatabase", "Before inserting items")
                                    //Add List ID to inserts

                                    // Work Trip Items
                                    dao.insert(PackingItem(name = "Underwear", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Boots", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Gym shoes", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Uniform of the Day", categoryId = 2, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "PT Gear", categoryId = 3, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Toothbrush", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothpaste", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Deodorant", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Shampoo", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Conditioner", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Body Wash", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Bath Robe", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "CAC Card", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Navy Cash Card", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Passport", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Computer", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Phone", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Headphones", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Charging Cables", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Pillow", categoryId = 7, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sheets", categoryId = 7, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))

                                    // Leisure Trip Items
                                    dao.insert(PackingItem(name = "Casual Shirts", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Shoes", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Swimsuit", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothbrush", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothpaste", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Deodorant", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Shampoo", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Conditioner", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sunscreen", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Passport/ID", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Tickets", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Itinerary", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Phone", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Camera", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Charger", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))

                                    // Combined Trip Items
                                    dao.insert(PackingItem(name = "Underwear", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Boots", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Gym shoes", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Casual Shirts", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts", categoryId = 1, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Uniform of the Day", categoryId = 2, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "PT Gear", categoryId = 3, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Toothbrush", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothpaste", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Deodorant", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Shampoo", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Conditioner", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Body Wash", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Bath Robe", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sunscreen", categoryId = 4, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "CAC Card", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Navy Cash Card", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Passport", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Tickets", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Itinerary", categoryId = 5, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Computer", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Phone", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Headphones", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Charging Cables", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Camera", categoryId = 6, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Pillow", categoryId = 7, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sheets", categoryId = 7, listId = listId.toInt(), baseQuantity = 1, quantityPerDay = 0))

                                    Log.d("PackingDatabase", "After inserting items")

                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}