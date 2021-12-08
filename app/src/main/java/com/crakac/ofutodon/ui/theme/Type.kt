package com.crakac.ofutodon.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h6 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
    ),
    body1 = TextStyle(
        fontSize = 15.sp,
    ),
    body2 = TextStyle(
        fontSize = 14.sp,
    )
)