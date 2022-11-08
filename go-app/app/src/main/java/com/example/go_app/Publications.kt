package com.example.go_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.go_app.databinding.ActivityPublicationsBinding
import com.example.go_app.databinding.CardPubBinding

class Publications : AppCompatActivity() {
    private lateinit var binding: ActivityPublicationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun configurarRecyclerView() {
        val filmes =
        val recyclerContainer = binding.recyclerContainer
        recyclerContainer.layoutManager = LinearLayoutManager(
            baseContext
        )
        recyclerContainer.adapter = FilmesAdapter(
            filmes
        ) { mensagem ->
            Toast.makeText(
                baseContext,
                mensagem,
                Toast.LENGTH_LONG
            ).show()
        }

    }
}

class FilmesAdapter(
    private val filmes: List<Filme>,
    private val onclick: (mensagem: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    )
            : RecyclerView.ViewHolder {

        val layoutDoCard = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.res_card_item, parent, false)

        return FilmeHolder(
            layoutDoCard,
        )

    }

    inner class FilmeHolder(
        private val layoutDoCard: View
    ) : RecyclerView.ViewHolder(layoutDoCard) {
        fun vincular(filme: Filme) {

            val tvTitulo = layoutDoCard
                .findViewById<TextView>(R.id.titulo)
            val ivImagem = layoutDoCard
                .findViewById<ImageView>(R.id.imagem)

            tvTitulo.text = filme.titulo
            ivImagem.setImageResource(filme.imagem)
            ivImagem.setOnClickListener {
                onclick("Você clicou no card X")
            }

        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        (holder as FilmeHolder).vincular(filmes[position])
    }

    override fun getItemCount(): Int {
        return filmes.size
    }

}

data class Filme(
    val titulo: String,
    val imagem: Int
)