package com.spopli.gosarconstruction.myClass

data class SiteDetails (val siteName: String = "", val siteAddress: String = "", val sitePhone: String = "") {
    override fun toString(): String {
        return  siteName
    }
}