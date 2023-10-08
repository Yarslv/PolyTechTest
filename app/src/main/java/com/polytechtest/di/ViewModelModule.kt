package com.polytechtest.di

import com.polytechtest.ui.fragments.book_list.FragmentBookListViewModel
import com.polytechtest.ui.fragments.categories.CategoriesFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoriesFragmentViewModel(get(), it[0]) }
    viewModel { FragmentBookListViewModel(get(), it[0], it[1]) }
}