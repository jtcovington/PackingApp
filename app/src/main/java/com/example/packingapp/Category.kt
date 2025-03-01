// app/src/main/java/com/example/packingapp/Category.kt
package com.example.packingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val categoryName: String,
    val listId: Int // Foreign key referencing the PackingList entity
)