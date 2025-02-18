import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.picpay.desafio.androd.domain.model.User
import com.picpay.desafio.android.presentation.R

@Composable
fun UserList(
    users: List<User>,
    onClick: (String) -> Unit,
    onFavoriteClick: (User) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users) { user ->
            UserItem(user = user, onClick = onClick, onFavoriteClick = onFavoriteClick)
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onClick: (String) -> Unit,
    onFavoriteClick: (User) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        onClick = { onClick(user.id.toString()) },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.img,
                contentDescription = stringResource(R.string.user_image),
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = user.name, fontWeight = FontWeight.Bold)

                Text(text = "@${user.username}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onFavoriteClick(user) }) {
                val favoriteIcon =
                    if (user.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(favoriteIcon, contentDescription = stringResource(R.string.favorite))
            }
        }
    }
}
