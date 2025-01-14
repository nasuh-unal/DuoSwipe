package com.example.duoswipe.ui.component

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.duoswipe.R


@Composable
fun MyBottomBar(
    navigateToProfileScreen:()->Unit,
    navigateToOverviewScreen:()->Unit
) {
    val bottomMenuItemsList = prepareBottomMenu()
    val contextForToast = LocalContext.current.applicationContext
    var selectedItem by remember {
        mutableStateOf("Home")
    }

    BottomAppBar(
        containerColor = Color(android.graphics.Color.parseColor("#F8F8F8")), tonalElevation = 3.dp
    ) {
        bottomMenuItemsList.forEachIndexed { index, bottomMenuItem ->
            if (index == 2) {
                NavigationBarItem(selected = false, onClick = {}, icon = {}, enabled = false)
            }
            NavigationBarItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem=bottomMenuItem.label
                    if(selectedItem=="Home"){
                        navigateToProfileScreen()
                    }else{
                        navigateToOverviewScreen()
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                },
                label = { Text(text = bottomMenuItem.label, Modifier.padding(top = 0.dp)) },
                alwaysShowLabel = true,
                enabled = true
            )

        }
    }
}

data class BottomMenuItem(val label: String, val icon: Painter)

@Composable
fun prepareBottomMenu(): List<BottomMenuItem> {
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Home", icon = painterResource(id = R.drawable.baseline_home_filled_24)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            label = "Profile", icon = painterResource(id = R.drawable.baseline_person_24)
        )
    )
    return bottomMenuItemList
}