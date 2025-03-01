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

@Database(entities = [PackingItem::class], version = 2, exportSchema = false)
abstract class PackingDatabase : RoomDatabase() {

    abstract fun packingItemDao(): PackingItemDao
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
                    .fallbackToDestructiveMigration()
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

                                    Log.d("PackingDatabase", "Before inserting items")

// Work Trip Items (Corrected Inserts)
                                    dao.insert(PackingItem(name = "Underwear (x1)", category = "Clothing", tripType = "Work", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x3)", category = "Clothing", tripType = "Work", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x5)", category = "Clothing", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x7)", category = "Clothing", tripType = "Work", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x1)", category = "Clothing", tripType = "Work", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x3)", category = "Clothing", tripType = "Work", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x5)", category = "Clothing", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x7)", category = "Clothing", tripType = "Work", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Boots (x1)", category = "Clothing", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Gym shoes (x1)", category = "Clothing", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Uniform of the Day (x1)", category = "Uniforms", tripType = "Work", tripDuration = 1, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Uniform of the Day (x3)", category = "Uniforms", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "PT Gear (x1)", category = "PT Gear", tripType = "Work", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "PT Gear (x3)", category = "PT Gear", tripType = "Work", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "PT Gear (x5)", category = "PT Gear", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "PT Gear (x7)", category = "PT Gear", tripType = "Work", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Toothbrush", category = "Toiletries", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothpaste", category = "Toiletries", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Deodorant", category = "Toiletries", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Shampoo", category = "Toiletries", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Conditioner", category = "Toiletries", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Body Wash", category = "Toiletries", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Bath Robe", category = "Toiletries", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "CAC Card", category = "Documents", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Navy Cash Card", category = "Documents", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Passport", category = "Documents", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Computer", category = "Electronics", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Phone", category = "Electronics", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Headphones", category = "Electronics", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Charging Cables", category = "Electronics", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Pillow (x2)", category = "Room Items", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sheets (x1)", category = "Room Items", tripType = "Work", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    // Leisure Trip Items
                                    dao.insert(PackingItem(name = "Casual Shirts (x1)", category = "Clothing", tripType = "Leisure", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Casual Shirts (x3)", category = "Clothing", tripType = "Leisure", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Casual Shirts (x5)", category = "Clothing", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Casual Shirts (x7)", category = "Clothing", tripType = "Leisure", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x1)", category = "Clothing", tripType = "Leisure", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x3)", category = "Clothing", tripType = "Leisure", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x5)", category = "Clothing", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x7)", category = "Clothing", tripType = "Leisure", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x1)", category = "Clothing", tripType = "Leisure", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x3)", category = "Clothing", tripType = "Leisure", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x5)", category = "Clothing", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x7)", category = "Clothing", tripType = "Leisure", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x1)", category = "Clothing", tripType = "Leisure", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x3)", category = "Clothing", tripType = "Leisure", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x5)", category = "Clothing", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x7)", category = "Clothing", tripType = "Leisure", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Shoes (x2)", category = "Clothing", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Swimsuit", category = "Clothing", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothbrush", category = "Toiletries", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothpaste", category = "Toiletries", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Deodorant", category = "Toiletries", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Shampoo", category = "Toiletries", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Conditioner", category = "Toiletries", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sunscreen", category = "Toiletries", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Passport/ID", category = "Documents", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Tickets", category = "Documents", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Itinerary", category = "Documents", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Phone", category = "Electronics", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Camera", category = "Electronics", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Charger", category = "Electronics", tripType = "Leisure", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    // Combined Trip Items
                                    dao.insert(PackingItem(name = "Underwear (x1)", category = "Clothing", tripType = "Combined", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x3)", category = "Clothing", tripType = "Combined", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x5)", category = "Clothing", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Underwear (x7)", category = "Clothing", tripType = "Combined", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x1)", category = "Clothing", tripType = "Combined", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x3)", category = "Clothing", tripType = "Combined", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x5)", category = "Clothing", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Socks (x7)", category = "Clothing", tripType = "Combined", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Boots (x1)", category = "Clothing", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Gym shoes (x1)", category = "Clothing", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Casual Shirts (x1)", category = "Clothing", tripType = "Combined", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Casual Shirts (x3)", category = "Clothing", tripType = "Combined", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Casual Shirts (x5)", category = "Clothing", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Casual Shirts (x7)", category = "Clothing", tripType = "Combined", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x1)", category = "Clothing", tripType = "Combined", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x3)", category = "Clothing", tripType = "Combined", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x5)", category = "Clothing", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Pants/Shorts (x7)", category = "Clothing", tripType = "Combined", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Uniform of the Day (x1)", category = "Uniforms", tripType = "Combined", tripDuration = 1, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Uniform of the Day (x3)", category = "Uniforms", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "PT Gear (x1)", category = "PT Gear", tripType = "Combined", tripDuration = 1, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "PT Gear (x3)", category = "PT Gear", tripType = "Combined", tripDuration = 3, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "PT Gear (x5)", category = "PT Gear", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "PT Gear (x7)", category = "PT Gear", tripType = "Combined", tripDuration = 7, baseQuantity = 1, quantityPerDay = 1))
                                    dao.insert(PackingItem(name = "Toothbrush", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Toothpaste", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Deodorant", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Shampoo", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Conditioner", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Body Wash", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Bath Robe", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sunscreen", category = "Toiletries", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "CAC Card", category = "Documents", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Navy Cash Card", category = "Documents", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Passport", category = "Documents", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Tickets", category = "Documents", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0)) // Added for Combined
                                    dao.insert(PackingItem(name = "Itinerary", category = "Documents", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0)) // Added for Combined
                                    dao.insert(PackingItem(name = "Computer", category = "Electronics", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Phone", category = "Electronics", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Headphones", category = "Electronics", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Charging Cables", category = "Electronics", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Camera", category = "Electronics", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0)) // Added for Combined
                                    dao.insert(PackingItem(name = "Pillow (x2)", category = "Room Items", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))
                                    dao.insert(PackingItem(name = "Sheets (x1)", category = "Room Items", tripType = "Combined", tripDuration = 5, baseQuantity = 1, quantityPerDay = 0))

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