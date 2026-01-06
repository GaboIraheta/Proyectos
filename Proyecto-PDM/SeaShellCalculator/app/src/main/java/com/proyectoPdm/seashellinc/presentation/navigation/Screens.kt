package com.proyectoPdm.seashellinc.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class PeriodicTableScreenSerializable(val backOfPremium : Boolean = false)

@Serializable
data class BalEquationScreenSerializable(val backOfPremium : Boolean = false)

@Serializable
data class BuyPremiumScreenSerializable(val screen : String)

@Serializable
object ChemicalUnitsScreenSerializable

@Serializable
data class CompoundScreenSerializable(val compoundName : String, val static : Boolean = true)

@Serializable
object LoadingScreenSerializable

@Serializable
object LoginScreenSerializable

@Serializable
object MainScreenSerializable

@Serializable
data class MolarMassPersonalScreenSerializable(
    val backOfPremium : Boolean = false,
    val isCalculator : Boolean,
    val screenToBack : String,
)

@Serializable
data class MolarMassScreenSerializable(
    val backOfPremium : Boolean = false,
    val isCalculator : Boolean,
    val screenToBack : String,
)

@Serializable
object PhysicalUnitsScreenSerializable

@Serializable
object RegisterScreenSerializable

@Serializable
object ErrorScreenSerializable

@Serializable
object MassOverMassCalculatorSerializable

@Serializable
object MassOverVolumeCalculatorSerializable

@Serializable
object PartsPerMillionCalculatorSerializable

@Serializable
object VolumeOverVolumeCalculatorSerializable

@Serializable
object MolarityCalculatorSerializable

@Serializable
object MolalityCalculatorSerializable

@Serializable
object NormalityCalculatorSerializable

@Serializable
object MolarFractionCalculatorSerializable