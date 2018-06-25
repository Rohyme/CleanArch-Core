package com.tripl3dev.presentation.ui.movieDetails

import com.tripl3dev.domain.businessLogic.businessUseCases.movies.MovieDetailUseCase
import com.tripl3dev.presentation.base.BaseViewModel
import javax.inject.Inject

class MovieDetailsVM @Inject constructor(val movieDetailUseCase: MovieDetailUseCase) : BaseViewModel() {
}