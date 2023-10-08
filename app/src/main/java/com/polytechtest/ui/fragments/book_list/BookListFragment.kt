package com.polytechtest.ui.fragments.book_list

import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.polytechtest.arch.BaseComposeFragmentWithVM
import com.polytechtest.arch.BaseViewModel
import com.polytechtest.domain.entity.BookListModel
import com.polytechtest.domain.use_cases.BookListUseCase
import com.polytechtest.ext.doAction
import com.polytechtest.network.response.onFailure
import com.polytechtest.network.response.onSuccess
import com.polytechtest.ui.composable.BookList
import com.polytechtest.ui.composable.BookListItem
import com.polytechtest.ui.composable.Progress
import com.polytechtest.util.AppAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FragmentBookList : BaseComposeFragmentWithVM() {
    private val args by navArgs<FragmentBookListArgs>()

    override val viewModel: FragmentBookListViewModel by viewModel() {
        parametersOf(args.encodedName, ::doAction)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun FragmentScreen() {
        val data by viewModel.data.collectAsState()

        val loading = viewModel.showProgress.collectAsState()

        val isShowNothing = viewModel.isShowNothingScreen.collectAsState()

        val pullRefreshState =
            rememberPullRefreshState(refreshing = loading.value, onRefresh = { viewModel.load() })

        BookList(
            isShowNothing = isShowNothing.value,
            pullRefreshState = pullRefreshState,
            title = data.title
        ) {
            items(data.list.bookList) { item -> BookListItem(item) { doAction(it) } }
        }

        if (loading.value) Progress()
    }
}

class FragmentBookListViewModel(
    private val bookListUseCase: BookListUseCase,
    val encodedName: String,
    val exception: (AppAction) -> Unit,
) :
    BaseViewModel() {
    private val _data = MutableStateFlow(BookListModel.empty())
    val data = _data.asStateFlow()

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
            bookListUseCase.getBookList(encodedName)
                .onSuccess {
                    _showProgress.value = false
                    _data.value = it
                    if (it.list.bookList.isEmpty()) _isShowNothingScreen.value = true
                }
                .onFailure {
                    _isShowNothingScreen.value = true
                    _showProgress.value = false
                    exception(AppAction.Message(it))
                }
        }
    }
}
