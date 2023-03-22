package com.example.cryptocoinlist.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptocoinlist.R
import com.example.cryptocoinlist.domain.models.Coin
import com.example.cryptocoinlist.presentation.activity.CoinState

@Composable
fun CoinListScreen(
    state: CoinState,
    onCurrentTextChange: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SearchField(
            modifier = Modifier.fillMaxWidth(),
            onCurrentTextChange = { text -> onCurrentTextChange(text) })
        CoinList(
            modifier = Modifier.weight(1f),
            coins = state.coinList
        )
    }
}

@Composable
fun SearchField(
    onCurrentTextChange: (text: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var message by rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = modifier,
        color = Color(0xFFE7F1F1),
        shape = RoundedCornerShape(
            topStart = 9.dp,
            topEnd = 9.dp,
            bottomStart = 9.dp,
            bottomEnd = 9.dp,
        )
    ) {
        TextField(leadingIcon = { Icon(Icons.Filled.Search, null, tint = Color.Gray) },
            placeholder = { Text(text = stringResource(R.string.search)) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = Color.DarkGray
            ),
            value = message,
            onValueChange = { newText ->
                message = newText
                onCurrentTextChange(newText.trim())
            })
    }
}

@Composable
fun CoinList(
    coins: List<Coin>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        state = state,
    ) {
        items(items = coins,
            key = { coin -> coin.id }) { coin ->
            CoinListItem(
                item = coin, modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CoinListItem(item: Coin, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = item.name, )
            Text(
                text = stringResource(R.string.price) + item.current_price + stringResource(R.string.dollar_sign) ,
            )
        }
    }
}