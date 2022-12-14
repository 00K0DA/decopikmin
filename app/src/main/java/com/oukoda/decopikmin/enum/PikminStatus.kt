package com.oukoda.decopikmin.enum

enum class PikminStatus(val value: Int) {
    AlreadyExists(0),
    Growing(1),
    NotHave(2);

    companion object {
        fun create(value: Int): PikminStatus = values().first { it.value == value }

        fun updateStatus(pikminStatus: PikminStatus): PikminStatus {
            return when (pikminStatus) {
                AlreadyExists -> Growing
                Growing -> NotHave
                NotHave -> AlreadyExists
            }
        }
    }

}
