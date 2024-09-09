package com.study.littlelemon.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.study.littlelemon.R
import com.study.littlelemon.database.MenuEntity
import com.study.littlelemon.navigation.Destinations
import com.study.littlelemon.ui.component.LogoBar
import com.study.littlelemon.ui.component.TextInputField
import com.study.littlelemon.ui.theme.LittleLemonTheme
import com.study.littlelemon.viewmodel.HomeUiState
import com.study.littlelemon.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Home(
    navHostController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {

    val viewState: HomeUiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box (
            modifier = Modifier.fillMaxWidth().align(Alignment.End)
        ) {
            LogoBar()
            Row(
                modifier = Modifier.matchParentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp, end = 16.dp),
                    horizontalAlignment = Alignment.End
                ){
                    Image(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable {
                                navHostController.navigate(Destinations.PROFILE.route)
                            },
                        painter = painterResource(R.drawable.profile),
                        contentScale = ContentScale.Crop,
                        contentDescription = "profile image"
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        ) {
            HomeHeroBanner()
            TextInputField(
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                placeHolder = stringResource(R.string.home_search),
                leadingIcon = {
                    Icon( imageVector = Icons.Default.Search, contentDescription = "")
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                value = viewState.searchTerm,
                onValueChange = {viewModel.handleSearch(it)},
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(Modifier.height(15.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.home_order_for_delivery),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        HomeCategoryList(
            viewModel = viewModel,
            viewState = viewState
        )
        Spacer(Modifier.height(5.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        HomeDishList(viewState = viewState)
    }
}

@Composable
fun HomeHeroBanner() {
    Column(
        modifier = Modifier.fillMaxWidth().height(280.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false),
            ),
            modifier = Modifier.offset(y=(-5).dp)
        )
        Text(
            text = stringResource(R.string.home_banner_subtitle),
            style = MaterialTheme.typography.displayMedium.copy(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            modifier = Modifier.offset(y = (-25).dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-5).dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 20.dp),
                text = stringResource(R.string.home_banner_description),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 18.sp,
                    lineHeight = 20.sp
                ),
                color = MaterialTheme.colorScheme.surface
            )
            Image(
                painter = painterResource(R.drawable.hero_image),
                contentScale = ContentScale.Crop,
                contentDescription = "hero image",
                modifier = Modifier
                    .offset(y = (-20).dp)
                    .size(width = 150.dp, height = 180.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
        }
    }
}

@Composable
fun HomeCategoryList(
    viewModel: HomeViewModel,
    viewState: HomeUiState
) {
    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(items = viewState.categoryList) { category ->
            val isSelected = category in viewState.selectedCategories

            FilterChip(
                selected = isSelected,
                onClick = {
                    viewModel.handleFilter(category)
                },
                label = {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = category.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.secondary,
                    selectedContainerColor = MaterialTheme.colorScheme.secondary,
                    selectedLabelColor = MaterialTheme.colorScheme.surface
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.Transparent,
                    selectedBorderColor = MaterialTheme.colorScheme.onSurface,
                    enabled = true,
                    selected = isSelected
                )
            )
        }
    }
}

@Composable
fun HomeDishList(viewState: HomeUiState) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        itemsIndexed(items = viewState.menuUiList) { _, menu ->
            DishItem(menu = menu)
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DishItem(menu: MenuEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            Text(
                text = menu.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = menu.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                color = MaterialTheme.colorScheme.secondary,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${menu.price}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        GlideImage(
            model = menu.image,
            contentDescription = "menu image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(95.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Home(rememberNavController())
        }
    }
}