package com.example.go_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.R

class CustomAdapterNotification(
//    private val dataSet: Array<String>
) :
    RecyclerView.Adapter<CustomAdapterNotification.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView

//        init {
        // Define click listener for the ViewHolder's View.
//            textView = view.findViewById(R.id.textView)
//        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.notifications_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.textView.text = dataSet[position]
    }

    override fun getItemCount() = 2

}