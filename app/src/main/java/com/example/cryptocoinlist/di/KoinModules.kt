package com.example.cryptocoinlist.di

import com.example.cryptocoinlist.data.repository.ApiRepositoryImpl
import com.example.cryptocoinlist.domain.ApiRepository
import com.example.cryptocoinlist.presentation.activity.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<ApiRepository> { ApiRepositoryImpl(get()) }
}

val viewModelsModule = module {
    viewModel { MainViewModel(apiRepository = get()) }
}