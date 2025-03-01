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
    private var items: List<PackingItemAndCategory>,
    private val onItemUpdate: (PackingItem) -> Unit,
    private val onDeleteItem: (PackingItem) -> Unit
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

        holder.textViewItemName.text = currentItem.packingItem.name
        holder.textViewCategory.text = "(${currentItem.category?.categoryName ?: "Unknown"})"

        // *** CRUCIAL FIX: Remove any existing listener BEFORE setting isChecked ***
        holder.checkBoxPacked.setOnCheckedChangeListener(null)

        // NOW set the checked state
        holder.checkBoxPacked.isChecked = currentItem.packingItem.packed

        // Set the new listener
        holder.checkBoxPacked.setOnCheckedChangeListener { _, isChecked ->
            currentItem.packingItem.packed = isChecked
            onItemUpdate(currentItem.packingItem)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteItem(currentItem.packingItem)
        }
        updateItemTextStyle(holder, currentItem.packingItem.packed)
    }

    override fun getItemCount(): Int = items.size

    // Method to update the list
    fun updateList(newItems: List<PackingItemAndCategory>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun updateItemTextStyle(holder: ViewHolder, isChecked: Boolean) {
        val context = holder.itemView.context

        if (isChecked) {
            holder.textViewItemName.paintFlags =
                holder.textViewItemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.textViewCategory.paintFlags =
                holder.textViewCategory.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.textViewItemName.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.grayed_out
                )
            )
            holder.textViewCategory.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.grayed_out
                )
            )

        } else {
            holder.textViewItemName.paintFlags =
                holder.textViewItemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.textViewCategory.paintFlags =
                holder.textViewCategory.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.textViewItemName.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.black
                )
            )
            holder.textViewCategory.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            ) // Use a defined color resource
        }
    }
}