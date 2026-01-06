package com.proyectoPdm.seashellinc.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyectoPdm.seashellinc.data.model.calculators.ToCalculate
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.LightBlue

/**
 * Es un DropdownMenu reutilizable, por si se necesita usar en algún otro lugar.
 * @param itemList
 * Es la lista de cosas para el menú, preferiblemente de texto.
 * @param selectedItem
 * Es el item seleccionado, para que se vea el texto tras seleccionarlo o algo así.
 * @param onItemSelected
 * Es lo que quieres que haga el seleccionar un item, además de mostrar el texto seleccionado.
 * @param itemContent
 * Es el `Composable` que abrá para cada texto, así que puede ser un `Text`.
 * @param label
 * Es el texto placeholder del menú, por si quieres, no sé
 * @param modifier
 * Es el `Modifier` de la caja que, al presionar, abre el menú.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SelectionMenu(
    itemList: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemContent: @Composable (T) -> Unit,
    label: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
){
    var openMenu by remember { mutableStateOf(false) }

    var selectedItemText = selectedItem?.let { item ->
        when(item) {
            is String -> item
            // tiro la toalla con esto, es super situacional, pero bueno,
            // soy yo el imbécil que decidió trabajar con enums
            is ToCalculate -> item.label
            else -> item.toString()
        }
    } ?: "Dumb stupid error"

    ExposedDropdownMenuBox(
        expanded = openMenu,
        onExpandedChange = { openMenu = !openMenu },
        modifier = modifier
    ) {
        TextField(
            value = selectedItemText,
            onValueChange = {}, //para que no se pueda escribir (estoy seguro de que esto quita algo de reusabilidad)
            readOnly = true,
            label = label,
            shape = RoundedCornerShape(10.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = openMenu) },
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
            colors = TextFieldDefaults.colors(
                // Por qué? O sea, entiendo por qué... pero, realmente no había otra forma?
                unfocusedContainerColor = LightBlue,
                focusedContainerColor = LightBlue,
                focusedTrailingIconColor = DarkBlue,
                unfocusedTrailingIconColor = DarkBlue,
                focusedTextColor = DarkBlue,
                unfocusedTextColor = DarkBlue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = DarkBlue,
                    shape = RoundedCornerShape(10.dp)
                )
        )

        ExposedDropdownMenu(
            expanded = openMenu,
            onDismissRequest = {openMenu = false}
        ) {
            itemList.forEach { item ->
                DropdownMenuItem(
                    text = { itemContent(item) },
                    onClick = {
                        onItemSelected(item)
                        openMenu = false
                    }
                )
            }
        }
    }
}