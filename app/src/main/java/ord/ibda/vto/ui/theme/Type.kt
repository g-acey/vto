package ord.ibda.vto.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import ord.ibda.vto.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontName = GoogleFont("Roboto")

val bodyFontFamily = FontFamily(
    Font(
        googleFont = bodyFontName,
        fontProvider = provider,
    )
)

val bodyFontFamilyMedium = FontFamily(
    Font(
        googleFont = bodyFontName,
        fontProvider = provider,
        weight = FontWeight.Medium
    )
)

val fontName = GoogleFont("Poppins")

val fontFamily = FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider
    )
)

val fontFamilyMedium= FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider,
        weight = FontWeight.Medium
    )
)

val fontFamilySemiBold= FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider,
        weight = FontWeight.SemiBold
    )
)

val fontFamilyBold= FontFamily(
    Font(
        googleFont = fontName,
        fontProvider = provider,
        weight = FontWeight.Bold
    )
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = fontFamilyBold),
    displayMedium = baseline.displayMedium.copy(fontFamily = fontFamilySemiBold),
    displaySmall = baseline.displaySmall.copy(fontFamily = fontFamilySemiBold),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = fontFamilyMedium),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = fontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = bodyFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = bodyFontFamilyMedium),
    titleSmall = baseline.titleSmall.copy(fontFamily = bodyFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamilyMedium),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamilyMedium),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamilyMedium),
)