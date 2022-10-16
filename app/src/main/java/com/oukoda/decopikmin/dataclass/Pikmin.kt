package com.oukoda.decopikmin.dataclass

import com.oukoda.decopikmin.enum.Costume
import com.oukoda.decopikmin.enum.DecorType
import com.oukoda.decopikmin.enum.PikminStatus
import com.oukoda.decopikmin.enum.PikminType

data class Pikmin(
    val decorType: DecorType,
    val costume: Costume,
    val pikminType: PikminType,
    val number: Int,
    var pikminStatus: PikminStatus
) {
    companion object {
        fun newInstance(
            decorType: DecorType,
            costume: Costume,
            pikminType: PikminType,
            number: Int
        ): Pikmin {
            return Pikmin(decorType, costume, pikminType, number, PikminStatus.NotHave)
        }
    }

    fun createStatusKey(): String {
        return "${pikminType.value}-${costume.value}-${number}"
    }

    fun statusUpdate() {
        pikminStatus = PikminStatus.updateStatus(pikminStatus)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Pikmin) {
            this.decorType == other.decorType &&
                    this.costume == other.costume &&
                    this.pikminType == other.pikminType &&
                    this.number == other.number
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = decorType.hashCode()
        result = 31 * result + costume.hashCode()
        result = 31 * result + pikminType.hashCode()
        result = 31 * result + number
        return result
    }
}