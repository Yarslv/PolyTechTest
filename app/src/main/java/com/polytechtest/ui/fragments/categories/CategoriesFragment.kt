package com.polytechtest.ui.fragments.categories

import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import com.polytechtest.R
import com.polytechtest.arch.BaseComposeFragmentWithVM
import com.polytechtest.arch.BaseViewModel
import com.polytechtest.domain.entity.CategoryModel
import com.polytechtest.domain.use_cases.CategoriesUseCase
import com.polytechtest.ext.doAction
import com.polytechtest.network.response.onFailure
import com.polytechtest.network.response.onSuccess
import com.polytechtest.ui.composable.BookList
import com.polytechtest.ui.composable.ListItem
import com.polytechtest.ui.composable.Progress
import com.polytechtest.util.AppAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoriesFragment : BaseComposeFragmentWithVM() {
    override val viewModel: CategoriesFragmentViewModel by viewModel() {
        parametersOf(::doAction)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun FragmentScreen() {

        val loading = viewModel.showProgress.collectAsState()
        val isShowNothing = viewModel.isShowNothingScreen.collectAsState()

        val pullRefreshState =
            rememberPullRefreshState(refreshing = loading.value, onRefresh = { viewModel.load() })

        BookList(
            isShowNothing = isShowNothing.value,
            pullRefreshState = pullRefreshState,
            title = stringResource(R.string.categories)
        ) {
            items(viewModel.list) { ListItem(model = it) { action -> doAction(action) } }
        }

        if (loading.value) Progress()
    }


}

class CategoriesFragmentViewModel(
    private val categoriesUseCase: CategoriesUseCase,
    val exception: (AppAction) -> Unit,
) :
    BaseViewModel() {

    val list = mutableStateListOf<CategoryModel>()

    private val _showProgress = MutableStateFlow(false)
    val showProgress = _showProgress.asStateFlow()

    private val _isShowNothingScreen = MutableStateFlow(false)
    val isShowNothingScreen = _isShowNothingScreen.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _showProgress.value = true
            categoriesUseCase.getCategories()
                .onSuccess {
                    _showProgress.value = false
                    list.addAll(it)
                    if (it.isEmpty()) _isShowNothingScreen.value = true
                }
                .onFailure {
                    _isShowNothingScreen.value = true
                    _showProgress.value = false
                    exception(AppAction.Message(it))
                }
        }
    }
}