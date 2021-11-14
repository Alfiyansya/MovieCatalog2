package com.alfian.moviecatalog2.di

import com.alfian.moviecatalog2.data.source.remote.RemoteDataSource
import com.alfian.moviecatalog2.data.source.remote.api.ApiBuilder
import com.alfian.moviecatalog2.data.source.remote.api.ApiService
import com.alfian.moviecatalog2.data.source.repo.ShowRepository
import com.alfian.moviecatalog2.ui.detail.DetailViewModel
import com.alfian.moviecatalog2.ui.show.ShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val showViewModelModule = module {
    viewModel {
        ShowViewModel(get())
    }
}
val detailViewModelModule = module {
    viewModel {
        DetailViewModel(get())
    }
}
val remoteModule = module {
    single {
        RemoteDataSource(get())
    }
}
val repositoryModule = module {
    single {
        ShowRepository(get())
    }
}
val networkModule = module {
    fun provideNetworking(): ApiService {
        return ApiBuilder.createService()
    }
    single {
        provideNetworking()
    }
}