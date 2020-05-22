package com.andb.apps.aspen.util

import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectAsState(): State<T> = (this as Flow<T>).collectAsState(value)