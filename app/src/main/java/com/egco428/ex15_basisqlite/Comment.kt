package com.egco428.ex15_basisqlite

class Comment {
    var id: Long = 0
    var commentdata: String = ""

    override fun toString(): String {

        return commentdata.toString()
    }
}