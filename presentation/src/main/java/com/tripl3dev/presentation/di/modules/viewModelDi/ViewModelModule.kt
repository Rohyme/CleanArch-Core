package com.tripl3dev.presentation.di.modules.viewModelDi

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tripl3dev.presentation.ui.MainActivityViewModel
import com.tripl3dev.presentation.ui.login.GuestSessionVM
import com.tripl3dev.presentation.ui.moviesScreen.MoviesVM
import com.tripl3dev.presentation.ui.peopleScreen.PeopleVM
import com.tripl3dev.presentation.ui.tvShowsScreen.TVShowsVM
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun postListViewModel(viewModel: MainActivityViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(GuestSessionVM::class)
    internal abstract fun GuestSessionViewModel(viewModel: GuestSessionVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoviesVM::class)
    internal abstract fun MoviesViewModel(viewModel: MoviesVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeopleVM::class)
    internal abstract fun PeopleViewModel(viewModel: PeopleVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TVShowsVM::class)
    internal abstract fun TvShowsViewModel(viewModel: TVShowsVM): ViewModel

}