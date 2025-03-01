// app/src/main/java/com/example/packingapp/MainActivity.kt
package com.example.packingapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var textViewDateRange: TextView
    private lateinit var textViewDuration: TextView
    private lateinit var recyclerViewPackingList: RecyclerView
    private val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)

    // Variables to hold start and end dates
    private var startDate: Long? = null
    private var endDate: Long? = null

    // Database, DAO, and manager
    private lateinit var database: PackingDatabase
    private lateinit var packingItemDao: PackingItemDao
    private lateinit var packingListManager: PackingListManager // New manager class
    private lateinit var adapter: PackingListAdapter
    private lateinit var editTextNewItem: EditText
    private lateinit var editTextQuantity: EditText

    // Variable to store the current packing list ID
    private var currentListId: Int = 1 // Default to the first list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views by ID
        textViewDateRange = findViewById(R.id.textViewDateRange)
        textViewDuration = findViewById(R.id.textViewDuration)
        recyclerViewPackingList = findViewById(R.id.recyclerViewPackingList)
        val radioGroupTripType: RadioGroup = findViewById(R.id.radioGroupTripType)
        editTextNewItem = findViewById(R.id.editTextNewItem)
        val buttonAddItem: Button = findViewById(R.id.buttonAddItem)
        editTextQuantity = findViewById(R.id.editTextQuantity)

        // Initialize database, DAO, and manager
        database = PackingDatabase.getDatabase(this)
        packingItemDao = database.packingItemDao()
        packingListManager = PackingListManager(packingItemDao) // Initialize the manager

        // Set up RecyclerView with adapter and callbacks
        recyclerViewPackingList.layoutManager = LinearLayoutManager(this)
        adapter = PackingListAdapter(listOf(), { updatedItem ->
            // Update callback - when an item is checked/unchecked
            lifecycleScope.launch(Dispatchers.IO) {
                packingItemDao.update(updatedItem)
            }
        }, { packingItem ->
            // Delete callback - when an item is deleted
            lifecycleScope.launch(Dispatchers.IO) {
                packingItemDao.delete(packingItem)
                withContext(Dispatchers.Main) {
                    loadPackingListItems() // Reload list after deletion
                }
            }
        })
        recyclerViewPackingList.adapter = adapter

        // Set click listener for date range selection
        textViewDateRange.setOnClickListener {
            showDateRangePicker()
        }

        // Add item button click listener
        buttonAddItem.setOnClickListener {
            addNewItem()
        }

        // Load the default packing list
        loadPackingListItems()

        // Get the dates from the current packing list
        lifecycleScope.launch {
            loadCurrentPackingListDates()
        }
    }
    private fun showDateRangePicker() {
        try {
            // Create a builder with our custom theme
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Trip Dates")
                .setTheme(R.style.CustomMaterialDatePickerTheme)
                .build()

            // Set the selection listener
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                try {
                    startDate = selection.first
                    endDate = selection.second

                    // Update the UI with formatted dates
                    val startDateStr = dateFormatter.format(Date(startDate!!))
                    val endDateStr = dateFormatter.format(Date(endDate!!))
                    textViewDateRange.text = getString(R.string.date_range_label, startDateStr, endDateStr)

                    // Calculate and display the duration
                    calculateAndDisplayDuration()

                    // Update the current packing list with the new dates
                    updatePackingListDates()
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error processing date selection", e)
                    textViewDateRange.text = "Error setting dates"
                }
            }

            // Show the dialog
            dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error showing date picker", e)
            textViewDateRange.text = "Could not show date picker"
        }
    }

    /**
     * Update the current packing list with new dates
     */
    private fun updatePackingListDates() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                // Use the manager to update dates with null safety
                packingListManager.updatePackingListDates(currentListId, startDate, endDate)
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error updating packing list dates", e)
        }
    }

    /**
     * Load dates from the current packing list and update the UI
     */
    private fun loadCurrentPackingListDates() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val packingList = packingItemDao.getPackingListById(currentListId)

                if (packingList != null) {
                    startDate = packingList.tripStartDate
                    endDate = packingList.tripEndDate

                    // Update UI on the main thread with null safety
                    withContext(Dispatchers.Main) {
                        if (startDate != null && endDate != null) {
                            try {
                                val startDateStr = dateFormatter.format(Date(startDate!!))
                                val endDateStr = dateFormatter.format(Date(endDate!!))
                                textViewDateRange.text = getString(R.string.date_range_label, startDateStr, endDateStr)
                                calculateAndDisplayDuration()
                            } catch (e: Exception) {
                                Log.e("MainActivity", "Error formatting dates", e)
                                textViewDateRange.text = "Invalid dates"
                            }
                        } else {
                            textViewDateRange.text = getString(R.string.select_dates_label)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error loading packing list dates", e)
                withContext(Dispatchers.Main) {
                    textViewDateRange.text = getString(R.string.select_dates_label)
                }
            }
        }
    }
    /**
     * Calculate the duration of the trip in days and display it
     */
    private fun calculateAndDisplayDuration() {
        // Check if dates are selected
        if (startDate != null && endDate != null) {
            try {
                val diffInMilliseconds = endDate!! - startDate!!
                val diffInDays = (diffInMilliseconds / (1000 * 60 * 60 * 24)).toInt()

                textViewDuration.text = getString(R.string.duration_label, diffInDays)
            } catch (e: Exception) {
                textViewDuration.text = getString(R.string.duration_invalid)
            }
        } else {
            textViewDuration.text = "" // Clear the duration if dates are not selected
        }
    }

    /**
     * Add a new item to the current packing list
     */
    private fun addNewItem() {
        val itemName = editTextNewItem.text.toString().trim()
        val quantityString = editTextQuantity.text.toString().trim()

        if (itemName.isNotEmpty() && quantityString.isNotEmpty()) {
            val quantity = quantityString.toIntOrNull() ?: 1

            // Use the PackingListManager to add a new item
            lifecycleScope.launch(Dispatchers.IO) {
                packingListManager.addItemToList(
                    listId = currentListId,
                    name = itemName,
                    categoryId = 8, // "Other" category by default
                    quantity = quantity,
                    baseQuantity = quantity,
                    quantityPerDay = 0
                )

                withContext(Dispatchers.Main) {
                    loadPackingListItems() // Reload list after adding
                    editTextNewItem.text.clear() // Clear input fields
                    editTextQuantity.text.clear()
                }
            }
        }
    }

    /**
     * Load all items for the current packing list and update the adapter
     */
    private fun loadPackingListItems() {
        lifecycleScope.launch {
            packingItemDao.getItemsForList(currentListId).collect { items ->
                adapter.updateList(items)
            }
        }
    }
}