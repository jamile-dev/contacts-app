package com.picpay.desafio.android.presentation.ui.userdetail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.presentation.R
import com.picpay.desafio.android.presentation.ui.theme.AppColors

@Composable
fun HeaderSection(user: User) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = user.img,
                contentDescription = stringResource(R.string.profile_image),
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(4.dp, AppColors.Green, CircleShape),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user.name,
            style =
                MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Blue,
                ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "@${user.username}",
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.Blue.copy(alpha = 0.7f),
                ),
        )
    }
}
