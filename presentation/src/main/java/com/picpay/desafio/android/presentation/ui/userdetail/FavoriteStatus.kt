package com.picpay.desafio.android.presentation.ui.userdetail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.presentation.R
import kotlinx.coroutines.delay

@Composable
fun FavoriteStatus(
    user: User,
    onFavoriteClick: (User) -> Unit,
) {
    var animateScale by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (animateScale) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
    )

    val animatedFavoriteColor by animateColorAsState(
        targetValue =
            if (user.isFavorite) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            },
        animationSpec = tween(durationMillis = 300),
    )

    if (animateScale) {
        LaunchedEffect(Unit) {
            delay(200)
            animateScale = false
        }
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 4.dp,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable {
                    animateScale = true
                    onFavoriteClick(user)
                },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .size(48.dp)
                        .scale(scale)
                        .background(
                            color = animatedFavoriteColor.copy(alpha = 0.2f),
                            shape = CircleShape,
                        ),
            ) {
                Icon(
                    imageVector = if (user.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_icon),
                    tint = animatedFavoriteColor,
                    modifier = Modifier.size(28.dp),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = if (user.isFavorite) stringResource(R.string.favorite) else stringResource(R.string.mark_as_favorite),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = animatedFavoriteColor,
            )
        }
    }
}
