package com.study.littlelemon.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.study.littlelemon.R

@Preview(showBackground = true)
@Composable
fun LogoBar() {
    Column(
      modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            modifier = Modifier
                .padding(12.dp)
                .size(width = 200.dp, height = 80.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo"
        )
    }
}