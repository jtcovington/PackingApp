// app/src/main/java/com/example/packingapp/PackingItemAndCategory.kt
package com.example.packingapp

import androidx.room.Embedded
import androidx.room.Relation

data class PackingItemAndCategory(
    @Embedded val packingItem: PackingItem,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val category: Category? // Make Category nullable
)