package com.example.go_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.R
import com.example.go_app.models.NotificationResponse

class CustomNotification(
    val dataSet: List<NotificationResponse>
) : RecyclerView.Adapter<CustomNotification.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView
        val descricao: TextView
        val data: TextView

        init {
            descricao = view.findViewById(R.id.notification_list_description)
            title = view.findViewById(R.id.notification_list_title)
            data = view.findViewById(R.id.notification_list_date)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.notifications_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val notification: NotificationResponse = dataSet[position]
        viewHolder.descricao.text = notification.description
        viewHolder.title.text = notification.title
        viewHolder.data.text = notification.dateTimeNotification

    }

    override fun getItemCount() = dataSet.size

}