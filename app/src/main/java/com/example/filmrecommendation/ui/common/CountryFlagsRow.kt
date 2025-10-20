package com.example.filmrecommendation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import com.example.filmrecommendation.ui.movie.screen.getFlagEmoji

@Composable
fun CountryFlagsRow(countries: List<String>) {
    var selectedCountry by remember {  mutableStateOf<String?>(null) }

    Row (horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        countries.forEach { country ->
            val emojiFlag = getFlagEmoji(country)

            Box(contentAlignment = Alignment.Center) {
                //Flag Button
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.LightGray.copy(alpha = 0.3f), CircleShape)
                        .clickable {
                            selectedCountry = country
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(emojiFlag, fontSize = 24.sp)
                }

                //Small suggestion bubble above
                if (selectedCountry == country) {
                    //creating new component to show name
                    SuggestionBubble(
                        text = country,
                        onDismiss = { selectedCountry = null }
                    )
                }
            }
        }
    }
}

