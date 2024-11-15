package com.mikeapp.sportsmate.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Design() {
    Row(verticalAlignment = Alignment.Top) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SectionHeader("Typography")
            Text(
                text = "h1",
                style = MaterialTheme.typography.h1
            )
            Text(
                text = "h2",
                style = MaterialTheme.typography.h2
            )
            Text(
                text = "h3",
                style = MaterialTheme.typography.h3
            )
            Text(
                text = "h4",
                style = MaterialTheme.typography.h4
            )
            Text(
                text = "h5",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "h6",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "subtitle1",
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "subtitle2",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "body1",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "body2",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "caption",
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "button",
                style = MaterialTheme.typography.button
            )
            Text(
                text = "overline",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.size(5.dp))
            SectionHeader("Colors")
            Text(
                text = "primary",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
            Text(
                text = "secondary",
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
            )
            Text(
                text = "background",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            )
            Text(
                text = "surface",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            )
            Text(
                text = "error",
                color = MaterialTheme.colorScheme.onError,
                modifier = Modifier.background(MaterialTheme.colorScheme.error)
            )
        }

        Column(horizontalAlignment = Alignment.Start) {
            SectionHeader("Components")
            Text(text = "Text")
            Button({ mockClick() }) {
                Text(text = "Button")
            }
            Button(
                onClick = { mockClick() },
                enabled = false
            ) {
                Text(text = "Button disabled")
            }
            Card(modifier = Modifier.size(200.dp, 25.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "Card")
                }
            }
            FloatingActionButton(onClick = { mockClick() }) {
                Text(text = "Fab")
            }
            Row {
                val checkedState = remember { mutableStateOf(true) }
                Text(text = "Checkbox")
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it })
            }
            Row {
                Text(text = "Disabled")
                Checkbox(checked = true, onCheckedChange = null, enabled = false)
            }
            Row {
                Text(text = "Disabled")
                Checkbox(checked = false, onCheckedChange = null, enabled = false)
            }
            Row {
                val checkedState = remember { mutableStateOf(true) }
                Text(text = "RadioButton")
                RadioButton(selected = true, onClick = { mockClick() })
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it })
            }
            Row {
                Text(text = "Disabled")
                Checkbox(checked = true, onCheckedChange = null, enabled = false)
            }
            Row {
                Text(text = "Disabled")
                Checkbox(checked = false, onCheckedChange = null, enabled = false)
            }
            Row {
                Text(text = "Divider")
                Divider()
            }
            Row {
                Text(text = "ProgressIndicatorDefaults")
                ProgressIndicatorDefaults
            }


            RadioButton(selected = false, onClick = { mockClick() })
            RadioButton(selected = true, onClick = { mockClick() }, enabled = false)
            RadioButton(selected = false, onClick = { mockClick() }, enabled = false)
        }
    }

}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = Modifier.background(Color.Black)
    )
}

private fun mockClick() {

}