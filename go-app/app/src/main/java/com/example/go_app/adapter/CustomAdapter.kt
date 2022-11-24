package com.example.go_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.R
import com.example.go_app.models.ComplaintsResponse

class CustomAdapter(
    val dataSet: List<ComplaintsResponse>
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val primeiraLetra: TextView
        val tipo: TextView
        val nameUser: TextView
        val descricao: TextView
        val data: TextView
        val textBoletim: TextView
        val seloBoletim: ImageView

        init {
            primeiraLetra = view.findViewById(R.id.primeira_letra)
            tipo = view.findViewById(R.id.tipo)
            nameUser = view.findViewById(R.id.name_user)
            descricao = view.findViewById(R.id.descricao)
            data = view.findViewById(R.id.data)
            textBoletim = view.findViewById(R.id.textBoletim)
            seloBoletim = view.findViewById(R.id.seloBoletim)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_pub, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val complaint: ComplaintsResponse = dataSet[position]
        val url = "http://10.0.2.2:8080/complaints/archive/${complaint.id}"
        viewHolder.primeiraLetra.text = complaint.user.name.subSequence(0, 1)
        viewHolder.descricao.text = complaint.description
        viewHolder.nameUser.text = "${complaint.user.name.subSequence(0, 1)}******"
        viewHolder.tipo.text = "Classificação do caso: ${complaint.type}"
        viewHolder.data.text =
            complaint.address.district + complaint.address.city
        if ((complaint.bo == null) || complaint.bo.equals("")) {
            viewHolder.textBoletim.visibility = View.INVISIBLE
            viewHolder.seloBoletim.visibility = View.INVISIBLE
        }

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.textView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

}