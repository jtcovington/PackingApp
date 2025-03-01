// app/src/main/java/com/example/packingapp/PackingItem.kt
package com.example.packingapp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "packing_items",
    foreignKeys = [
        ForeignKey(
            entity = PackingList::class,
            parentColumns = ["listId"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE // Important: Cascade deletion
        ),
        ForeignKey( //added Category foreign key
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE // If a category is deleted, delete associated items
        )
    ],
    indices = [Index("listId"), Index("categoryId") ] // Add index for listId
)
data class PackingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var categoryId: Int, // Foreign key referencing the Category entity
    var packed: Boolean = false,
    var quantity: Int = 0,
    var baseQuantity: Int,
    var quantityPerDay: Int,
    val listId: Int // Foreign key referencing the PackingList

)