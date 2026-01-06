package com.proyectoPdm.seashellinc.presentation.ui.components


import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.proyectoPdm.seashellinc.data.model.element.Element
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown
import com.proyectoPdm.seashellinc.presentation.ui.theme.MontserratFontFamily
import com.proyectoPdm.seashellinc.presentation.ui.theme.getCategoryColor
import com.proyectoPdm.seashellinc.utils.getSpanishName
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color
import com.proyectoPdm.seashellinc.presentation.ui.theme.Background
import com.proyectoPdm.seashellinc.presentation.ui.theme.Buff
import com.proyectoPdm.seashellinc.presentation.ui.theme.DarkBlue
import com.proyectoPdm.seashellinc.presentation.ui.theme.MainBlue

@Composable
fun ElementDialog(element: Element?, changeShowDialog: (Element?) -> Unit) {

    val borderColor = getCategoryColor(element?.category)

    Dialog(onDismissRequest = { changeShowDialog(null) }) {
        Box(
            modifier = Modifier
                .border(width = 4.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
                .background(color = Background, shape = RoundedCornerShape(12.dp))
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = element?.name.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    fontFamily = MontserratFontFamily,
                    color = MainBlue
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text("Símbolo: ", fontSize = 20.sp, fontFamily = MontserratFontFamily)
                    Text(
                        element?.symbol.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = CitrineBrown,
                        fontFamily = MontserratFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text("Categoría: ", fontSize = 20.sp, fontFamily = MontserratFontFamily)
                    Text(
                        element?.category?.getSpanishName().toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = CitrineBrown,
                        fontFamily = MontserratFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text("Número Atómico: ", fontSize = 20.sp, fontFamily = MontserratFontFamily)
                    Text(
                        element?.atomicNumber.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = CitrineBrown,
                        fontFamily = MontserratFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    Text("Masa Atómica: ", fontSize = 20.sp, fontFamily = MontserratFontFamily)
                    Text(
                        element?.atomicMass.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = CitrineBrown,
                        fontFamily = MontserratFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                TextButton(onClick = { changeShowDialog(null) }) {
                    Text("Cerrar", fontFamily = MontserratFontFamily, fontWeight = FontWeight.Bold, color = MainBlue)
                }
            }
        }
    }
}