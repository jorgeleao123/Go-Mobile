package com.example.go_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.go_app.R
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.models.SaveComplaint
import com.example.go_app.models.SuccessResponse
import com.example.go_app.rest.Rest
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomAdapter(
    val dataSet: List<ComplaintsResponse>,
    val idUSerLogado: Int,
    val isSave: Boolean,
    val context: Context
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val primeiraLetra: TextView
        val tipo: TextView
        val nameUser: TextView
        val descricao: TextView
        val data: TextView
        val textBoletim: TextView
        val seloBoletim: ImageView
        val image: ImageView
        val btnSave: ImageView

        init {
            primeiraLetra = view.findViewById(R.id.primeira_letra)
            tipo = view.findViewById(R.id.tipo)
            nameUser = view.findViewById(R.id.name_user)
            descricao = view.findViewById(R.id.descricao)
            data = view.findViewById(R.id.data)
            textBoletim = view.findViewById(R.id.textBoletim)
            seloBoletim = view.findViewById(R.id.seloBoletim)
            image = view.findViewById(R.id.image_1)
            btnSave = view.findViewById(R.id.image_save)
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val complaint: ComplaintsResponse = dataSet[position]
//        val url = "http://10.0.2.2:8080/complaints/archive/${complaint.id}"
        val url = "https://goapp-api.azurewebsites.net/complaints/archive/${complaint.id}"
        viewHolder.primeiraLetra.text = complaint.user.name.subSequence(0, 1)
        viewHolder.descricao.text = complaint.description
        viewHolder.nameUser.text = "${complaint.user.name.subSequence(0, 1)}******"
        viewHolder.tipo.text = "Classificação do caso: ${complaint.type}"
        viewHolder.data.text = "${complaint.address.district}, ${complaint.address.city}"
        if ((complaint.bo == null) || complaint.bo.equals("")) {
            viewHolder.textBoletim.visibility = View.INVISIBLE
            viewHolder.seloBoletim.visibility = View.INVISIBLE
        }
        viewHolder.btnSave.setOnClickListener {
            if (isSave) {
                saveItem(idUSerLogado, complaint.id)
            } else {
                deleteSaveItem(idUSerLogado, complaint.id)
            }

        }
        Picasso.get().load(url).into(viewHolder.image);
    }

    override fun getItemCount() = dataSet.size

    private fun saveItem(idUserView: Int, idComplaintIdView: Int) {
        val request =
            Rest.getInstance()
                .create(com.example.go_app.services.ComplaintSavedController::class.java)

        val body = SaveComplaint(
            userId = idUserView,
            complaintId = idComplaintIdView
        )
        request.saveComplaint(body).enqueue(
            object : Callback<SuccessResponse> {
                override fun onResponse(
                    call: Call<SuccessResponse>,
                    response: Response<SuccessResponse>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        println("aqui")

                        val text = "Item Salvo"
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(context, text, duration)
                        toast.show()

                    }
                }

                override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                    println("não foi")
                }

            }
        )
    }

    private fun deleteSaveItem(idUserView: Int, idComplaintIdView: Int) {
        val request =
            Rest.getInstance()
                .create(com.example.go_app.services.ComplaintSavedController::class.java)

        request.removeComplaintSave(idUserView, idComplaintIdView).enqueue(
            object : Callback<SuccessResponse> {
                override fun onResponse(
                    call: Call<SuccessResponse>,
                    response: Response<SuccessResponse>
                ) {
                    if (response.code() == 404) {
                        println("não foi")
                    } else {
                        println("aqui")

                    }
                }

                override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                    println("não foi")
                }

            }
        )
        val text = "Item removido dos Salvos"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }
}