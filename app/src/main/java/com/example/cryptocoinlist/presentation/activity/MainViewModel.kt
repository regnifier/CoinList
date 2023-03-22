package com.example.cryptocoinlist.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoinlist.domain.ApiRepository
import com.example.cryptocoinlist.domain.models.Coin
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

    private var cryptoList: List<Coin> = emptyList()

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
    }

    fun loadCoinList() {
        viewModelScope.launch(errorHandler) {
            intent {
                postSideEffect(CoinSideEffect.Loading)
            }
            val list = apiRepository.getCoins()
            cryptoList = list
            intent {
                reduce {
                    state.copy(coinList = list, uiState = UiState.ShowCoins)
                }
                postSideEffect(CoinSideEffect.ShowCoins)
            }
        }
    }

    fun search(query: String) {
        val tempList = mutableListOf<Coin>()
        MutableStateFlow(query)
            .debounce(SEARCH_TIMEOUT)
            .filter { it.length >= SEARCH_QUERY_LENGTH || it.isEmpty() }
            .map { textQuery ->
                if (textQuery.isNotEmpty()) {
                    cryptoList.forEach {
                        if (it.symbol.lowercase().contains(textQuery.lowercase())) {
                            tempList.add(it)
                        } else if (it.name.lowercase().contains(textQuery.lowercase())) {
                            tempList.add(it)
                        }
                        intent {
                            reduce {
                                state.copy(coinList = tempList)
                            }
                        }
                    }
                } else {
                    intent {
                        reduce {
                            state.copy(coinList = cryptoList)
                        }
                    }
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}