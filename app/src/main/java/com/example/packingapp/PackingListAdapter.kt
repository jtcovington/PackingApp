package com.example.packingapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

// Adapter for the RecyclerView to display PackingItems
class PackingListAdapter(
    private var items: List<PackingItem>,
    private val onItemUpdate: (PackingItem) -> Unit, // Callback for updates
    private val onDeleteItem: (PackingItem) -> Unit // Callback for Deletion
) : RecyclerView.Adapter<PackingListAdapter.ViewHolder>() {

    // ViewHolder class
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItemName: TextView = itemView.findViewById(R.id.textViewItemName)
        val textViewCategory: TextView = itemView.findViewById(R.id.textViewCategory)
        val checkBoxPacked: CheckBox = itemView.findViewById(R.id.checkBoxPacked)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_packing_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        holder.textViewItemName.text = currentItem.name
        holder.textViewCategory.text = "(${currentItem.category})"

        // Set the checkbox state based on the item's packed status
        holder.checkBoxPacked.isChecked = currentItem.packed

        // Set an OnCheckedChangeListener for the checkbox
        holder.checkBoxPacked.setOnCheckedChangeListener { _, isChecked ->
            // Update the 'packed' status of the current item
            currentItem.packed = isChecked
            // Call the update callback
            onItemUpdate(currentItem)
        }
        //add delete button listener
        holder.deleteButton.setOnClickListener {
            onDeleteItem(currentItem)
        }
        updateItemTextStyle(holder, currentItem.packed)
    }
    override fun getItemCount(): Int = items.size

    // Method to update the list
    fun updateList(newItems: List<PackingItem>) {
        items = newItems
        notifyDataSetChanged() // Efficient update
    }
    private fun updateItemTextStyle(holder: ViewHolder, isChecked: Boolean) {
        val context = holder.itemView.context

        if (isChecked) {
            holder.textViewItemName.paintFlags = holder.textViewItemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.textViewCategory.paintFlags = holder.textViewCategory.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.textViewItemName.setTextColor(ContextCompat.getColor(context, R.color.grayed_out))
            holder.textViewCategory.setTextColor(ContextCompat.getColor(context, R.color.grayed_out))


        } else {
            holder.textViewItemName.paintFlags = holder.textViewItemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.textViewCategory.paintFlags = holder.textViewCategory.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.textViewItemName.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            holder.textViewCategory.setTextColor(ContextCompat.getColor(context, R.color.light_gray)) // Use a defined color resource
        }
    }
}