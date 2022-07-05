package br.com.zup.movieflix.moviedetail.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.zup.movieflix.CHAVE_MOVIE
import br.com.zup.movieflix.databinding.ActivityMovieDetailBinding
import br.com.zup.movieflix.home.model.Movie
import br.com.zup.movieflix.moviedetail.model.MovieWithDirectorModel
import br.com.zup.movieflix.moviedetail.viewmodel.MovieDetailViewModel

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by lazy {
        ViewModelProvider(this)[MovieDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observable()
        getPassedData()
    }

    fun getPassedData() {
        val movie = intent.getParcelableExtra<Movie>(CHAVE_MOVIE)
        movie?.let { viewModel.getMovieWithDirector(it, binding.switchFavorite.isChecked) }
    }

    fun observable() {
        viewModel.response.observe(this) {
            binding.tvMovieTitle.text = it.movie.title
            binding.tvMovieSinopse.text = it.movie.sinopse
            binding.tvDirectorName.text = it.director.name
            binding.tvDirectorInfo.text = it.director.info
            binding.imageView.setImageResource(it.movie.image)

            if (!it.movie.favorite) {
                binding.tvFavorite.text = "Favoritar"
            } else {
                binding.tvFavorite.text = "Favoritado!"
            }
        }

        binding.switchFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                binding.tvFavorite.text = "Favoritar"
            } else {
                binding.tvFavorite.text = "Favoritado!"
            }
        }
    }
}