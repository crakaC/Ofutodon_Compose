package com.crakac.ofutodon.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crakac.ofutodon.mastodon.entity.Status
import com.crakac.ofutodon.util.iconResource
import com.crakac.ofutodon.util.stringResource

val DefaultVisibility = Status.Visibility.Public

class VisibilityDropDownState(initialVisibility: Status.Visibility) {
    var expanded by mutableStateOf(false)
    var visibility by mutableStateOf(initialVisibility)
}

@Composable
fun VisibilityDropDownMenu(state: VisibilityDropDownState) {
    DropdownMenu(
        modifier = Modifier.wrapContentSize(),
        expanded = state.expanded, onDismissRequest = { state.expanded = false }
    ) {
        Status.Visibility.values().forEach { visibility ->
            DropdownMenuItem(
                onClick = {
                    state.visibility = visibility
                    state.expanded = false
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = visibility.iconResource()),
                    contentDescription = visibility.name
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(id = visibility.stringResource())
                )
            }
        }
    }
}