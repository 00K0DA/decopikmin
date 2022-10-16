package com.oukoda.decopikmin.enum

import com.oukoda.decopikmin.R

enum class PikminType(val value: String) {
    Red("Red"),
    Blue("Blue"),
    Yellow("Yellow"),
    White("White"),
    Purple("Purple"),
    Rock("Rock"),
    Wing("Wing");

    companion object {
        fun getPikminStringId(pikminType: PikminType): Int{
            return when (pikminType) {
                Red -> R.string.pikmin_type_red
                Blue -> R.string.pikmin_type_blue
                Yellow -> R.string.pikmin_type_yellow
                White -> R.string.pikmin_type_white
                Purple -> R.string.pikmin_type_purple
                Rock -> R.string.pikmin_type_rock
                Wing -> R.string.pikmin_type_wing
            }
        }
    }
}
