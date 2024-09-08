package com.ipb.remangokbabel.ui.components.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ipb.remangokbabel.ui.theme.MyStyle
import com.ipb.remangokbabel.ui.theme.RemangokBabelTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    modifier: Modifier = Modifier,
    ) {
    SearchBar(
        windowInsets = WindowInsets(top = 0.dp),
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = { Text("Cari Produk") },
        shape = MaterialTheme.shapes.large,
        colors = SearchBarDefaults.colors(
            containerColor = MyStyle.colors.bgSecondary,
        ),
        modifier = modifier
            .height(SearchBarDefaults.InputFieldHeight)
            .fillMaxWidth(),
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    RemangokBabelTheme {
        Search()
    }
}