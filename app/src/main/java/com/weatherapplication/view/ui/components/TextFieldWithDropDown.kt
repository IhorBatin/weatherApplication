package com.weatherapplication.view.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.weatherapplication.model.data.City

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextView(
    modifier: Modifier,
    query: String,
    queryLabel: String,
    onQueryChanged: (String) -> Unit = {},
    predictions: List<City>,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onItemClick: (City) -> Unit = {}
) {
    val view = LocalView.current
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier.heightIn(max = TextFieldDefaults.MinHeight * 6)
    ) {
        item {
            QuerySearch(
                query = query,
                label = queryLabel,
                onQueryChanged = onQueryChanged,
                onDoneActionClick = {
                    onDoneActionClick()
                },
                onClearClick = {
                    onClearClick()
                }
            )
        }

        if (predictions.isNotEmpty()) {
            items(predictions) { prediction ->
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            view.clearFocus()
                            onItemClick(prediction)
                        }
                ) {
                    Text(text = prediction.fullCityName)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun QuerySearch(
    modifier: Modifier = Modifier,
    query: String,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onQueryChanged: (String) -> Unit
) {
    var showClearButton by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                showClearButton = (focusState.isFocused)
            },
        value = query,
        onValueChange = onQueryChanged,
        label = { Text(text = label) },
        textStyle = MaterialTheme.typography.labelSmall,
        singleLine = true,
        trailingIcon = {
            if (showClearButton) {
                IconButton(onClick = { onClearClick() }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Clear")
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onDoneActionClick()
        })
    )
}
