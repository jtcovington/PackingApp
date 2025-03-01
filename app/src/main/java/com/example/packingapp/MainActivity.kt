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

    //Variables to hold start and end
    private var startDate: Long? = null
    private var endDate: Long? = null

    private var selectedTripType = "Work" // Default trip type

    //Add Database and Dao
    private lateinit var database: PackingDatabase
    private lateinit var packingItemDao: PackingItemDao
    private lateinit var adapter: PackingListAdapter
    private lateinit var editTextNewItem : EditText
    private lateinit var editTextQuantity : EditText

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

        // Initialize database and DAO
        database = PackingDatabase.getDatabase(this)
        packingItemDao = database.packingItemDao()

        // Set up RecyclerView, and add update and delete callbacks
        recyclerViewPackingList.layoutManager = LinearLayoutManager(this)
        adapter = PackingListAdapter(listOf(), { updatedItem ->
            lifecycleScope.launch(Dispatchers.IO) {
                packingItemDao.update(updatedItem)
            }
        }, {packingItem ->
            lifecycleScope.launch(Dispatchers.IO){
                packingItemDao.delete(packingItem)
                loadPackingListItems() //reload list on delete
            }

        })
        recyclerViewPackingList.adapter = adapter

        // Set click listener for date range selection
        textViewDateRange.setOnClickListener {
            showDateRangePicker()
        }

        // Set listener for Radio Group
        radioGroupTripType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonWork -> selectedTripType = "Work"
                R.id.radioButtonLeisure -> selectedTripType = "Leisure"
                R.id.radioButtonCombined -> selectedTripType = "Combined"
            }
            //Update the list based on Radio Selection, and recalculate
            calculateAndDisplayDuration()
        }

        //Add Item button
        buttonAddItem.setOnClickListener{
            addNewItem()
        }

        // Load initial data from the database
        loadPackingListItems()

    }

    private fun showDateRangePicker() {
        //Build the date range picker
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select Trip Dates")
            .build()

        //Show the dialog
        dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")

        //Set the listener to receive the selected dates
        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            // dateRange is a Pair<Long, Long> representing start and end dates in milliseconds
            startDate = dateRange.first
            endDate = dateRange.second

            //Format and display the selected dates
            val startDateString = dateFormatter.format(Date(startDate!!))
            val endDateString = dateFormatter.format(Date(endDate!!))
            textViewDateRange.text = "$startDateString - $endDateString" // Display "Start Date - End Date"

            //Calculate and display the duration, and refresh list
            calculateAndDisplayDuration()
        }

        // Optional: Handle cancellation and negative button clicks if needed.
        dateRangePicker.addOnNegativeButtonClickListener {
            Log.d("MainActivity", "Date Range Selection Cancelled (Negative Button)")
        }
        dateRangePicker.addOnCancelListener{
            Log.d("MainActivity", "Date Range Selection Cancelled (Dialog Dismissed)")
        }

    }


    private fun calculateAndDisplayDuration() {
        //Check if dates are selected
        if (startDate != null && endDate != null){
            try {
                val diffInMilliseconds = endDate!! - startDate!!
                val diffInDays = (diffInMilliseconds / (1000 * 60 * 60 * 24)).toInt()

                textViewDuration.text = getString(R.string.duration_label, diffInDays)
                loadPackingListItems() //Reload from database

            } catch (e: Exception) {
                textViewDuration.text = getString(R.string.duration_invalid)
            }
        } else {
            textViewDuration.text = "" // Clear the duration if dates are not selected
        }
    }
    private fun addNewItem() {
        val itemName = editTextNewItem.text.toString().trim()
        val quantityString = editTextQuantity.text.toString().trim()

        if (itemName.isNotEmpty() && quantityString.isNotEmpty()) {
            val quantity = quantityString.toIntOrNull() ?: 1

            val days = if (startDate != null && endDate != null) {
                ((endDate!! - startDate!!) / (1000 * 60 * 60 * 24)).toInt()
            } else {
                5 // Default duration
            }

            // Use baseQuantity and quantityPerDay when adding a *new* item
            val newItem = PackingItem(
                name = itemName,
                category = "User Added", // Or get category from user input, such as a drop down.
                packed = false,
                quantity = 0, // Set initial quantity to 0; it will be calculated
                tripType = selectedTripType,
                tripDuration = days, // Use calculated or default days
                baseQuantity = quantity, // Use entered quantity as base
                quantityPerDay = 0 // Default to 0; let user modify if needed
            )

            lifecycleScope.launch(Dispatchers.IO) {
                packingItemDao.insert(newItem)
                withContext(Dispatchers.Main){
                    loadPackingListItems() // Reload list after adding a new item
                }

            }

            editTextNewItem.text.clear()
            editTextQuantity.text.clear()
        }
    }

    // Load items, using tripType and Duration
    private fun loadPackingListItems() {
        lifecycleScope.launch {
            // Calculate duration based on selected dates
            val days = if (startDate != null && endDate != null) {
                ((endDate!! - startDate!!) / (1000 * 60 * 60 * 24)).toInt()
            } else {
                5 // A default
            }

            Log.d("MainActivity", "loadPackingListItems: tripType=$selectedTripType, days=$days")

            packingItemDao.getItemsForTrip(selectedTripType, days).collect { items ->
                // Calculate the quantity for each item *before* updating the adapter
                val updatedItems = items.map { item ->
                    item.copy(quantity = item.baseQuantity + (item.quantityPerDay * days))
                }
                adapter.updateList(updatedItems) // Update the adapter with new list
            }
        }
    }
}