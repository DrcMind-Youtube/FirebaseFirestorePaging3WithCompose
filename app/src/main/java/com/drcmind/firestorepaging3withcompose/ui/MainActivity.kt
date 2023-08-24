package com.drcmind.firestorepaging3withcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.drcmind.firestorepaging3withcompose.ui.theme.FirestorePaging3WithComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirestorePaging3WithComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel : MainVIewModel = hiltViewModel()
                    val quotes = viewModel.quotes.collectAsLazyPagingItems()

                    Scaffold(
                        topBar = {
                            TopAppBar(title = {
                                Text(text = "QuotesApp", fontWeight = FontWeight.ExtraBold) })
                        }
                    ) {paddingValues ->
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)){

                            if(quotes.loadState.refresh is LoadState.Loading){
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }else{
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    items(quotes){quote->
                                        Column(Modifier.fillMaxWidth()) {
                                            if (quote != null) {
                                                Text(text = quote.quote,
                                                    style = MaterialTheme.typography.bodyMedium)
                                                Text(text = quote.author,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontStyle = FontStyle.Italic,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.End
                                                )
                                                Divider()
                                            }
                                        }
                                    }
                                    item {
                                        if(quotes.loadState.append is LoadState.Loading){
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
