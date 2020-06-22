package com.andb.apps.aspen

import androidx.lifecycle.ViewModel
import com.andb.apps.aspen.state.Screens
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivityViewModel : ViewModel(){
    val screens = Screens(MutableStateFlow(listOf()))
}