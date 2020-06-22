package com.andb.apps.aspen.util

import com.andb.apps.aspen.state.UserAction

class ActionHandler(val handle: (UserAction) -> Boolean)