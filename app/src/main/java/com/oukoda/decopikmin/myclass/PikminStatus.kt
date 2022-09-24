package com.oukoda.decopikmin.myclass

enum class PikminStatus(val value: Int) {
    AlreadyExists(0),
    Growing(1),
    NotHave(2);
    companion object {
        fun create(value: Int): PikminStatus = values().first { it.value == value }
    }
}
