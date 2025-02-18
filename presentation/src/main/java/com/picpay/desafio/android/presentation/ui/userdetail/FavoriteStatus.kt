package com.picpay.desafio.android.presentation.ui.userdetail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.presentation.R

@Composable
fun FavoriteStatus(
    user: User,
    onFavoriteClick: (User) -> Unit,
) {
    val favoriteColor =
        if (user.isFavorite) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
    ) {
        IconButton(
            onClick = { onFavoriteClick(user) },
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                imageVector = if (user.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite_icon),
                tint = favoriteColor,
                modifier = Modifier.size(32.dp),
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
}
