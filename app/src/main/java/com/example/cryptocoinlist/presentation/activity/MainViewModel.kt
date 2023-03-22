package com.example.cryptocoinlist.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoinlist.domain.ApiRepository
import com.example.cryptocoinlist.domain.models.Coin
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

private const val SEARCH_TIMEOUT = 500L
private const val SEARCH_QUERY_LENGTH = 2

class MainViewModel(private val apiRepository: ApiRepository) :
    ContainerHost<CoinState, CoinSideEffect>, ViewModel() {

    override val container = container<CoinState, CoinSideEffect>(CoinState())

    private var cryptoList: ImmutableList<Coin> = persistentListOf()

    private val searchFLow = MutableStateFlow("")

    private val errorHandler by lazy {
        CoroutineExceptionHandler { _, _ ->
            intent {
                reduce {
                    state.copy(uiState = UiState.Error)
                }
            }
        }
    }

    init {
        loadCoinList()
        createSearchFlow()
    }

    fun loadCoinList() {
        viewModelScope.launch(errorHandler) {
            intent {
                postSideEffect(CoinSideEffect.Loading)
            }
            val list = apiRepository.getCoins().toImmutableList()
            cryptoList = list
            intent {
                reduce {
                    state.copy(coinList = list, uiState = UiState.ShowCoins)
                }
                postSideEffect(CoinSideEffect.ShowCoins)
            }
        }
    }

    private fun createSearchFlow() {
        searchFLow
            .debounce(SEARCH_TIMEOUT)
            .filter { it.length >= SEARCH_QUERY_LENGTH || it.isEmpty() }
            .onEach { textQuery ->
                intent {
                    reduce {
                        state.copy(
                            coinList = if (textQuery.isNotEmpty())
                                cryptoList.filter {
                                    it.name.lowercase().contains(textQuery.lowercase())
                                }.toImmutableList()
                            else cryptoList
                        )
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun search(query: String) {
        intent {
            reduce {
                state.copy(searchText = query)
            }
        }
        searchFLow.value = query
    }
}