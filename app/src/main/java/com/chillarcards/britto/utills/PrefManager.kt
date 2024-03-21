package com.chillarcards.britto.utills

import android.content.Context
import android.content.SharedPreferences
import kotlin.math.roundToLong

class PrefManager(_context: Context) {

    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "Servicexpert"

        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val PAGE = "PAGE"
        private const val USERTYPE = "USERTYPE"
        private const val UUID = "uuid"
        private const val STATUS = "STATUS"
        private const val MOBILENO = "MOBILENO"
        private const val BUSID = "BUSID"
        private const val DOCTORID = "DOCTORID"
        private const val CURRENT_LAT = "CURRENT_LAT"
        private const val CURRENT_LONG = "CURRENT_LONG"

        // shared pref mode
        private const val PRIVATE_MODE = Context.MODE_PRIVATE
    }

    private val pref: SharedPreferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private val editor: SharedPreferences.Editor = pref.edit()

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }

    fun setIsLoggedIn(value: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, value)
        editor.commit()
    }

    fun getUuid(): String {
        return pref.getString(UUID, "") ?: ""
    }
    fun setUuid(token: String) {
        editor.putString(UUID, token)
        editor.commit()
    }
    fun getRefToken(): String {
        return pref.getString(USERTYPE, "") ?: ""
    }
    fun setRefToken(token: String) {
        editor.putString(USERTYPE, token)
        editor.commit()
    }
    fun getPage(): String {
        return pref.getString(PAGE, "") ?: ""
    }
    fun setPage(token: String) {
        editor.putString(PAGE, token)
        editor.commit()
    }
    fun getMobileNo(): String {
        return pref.getString(MOBILENO, "") ?: ""
    }
    fun setMobileNo(value: String) {
        editor.putString(MOBILENO, value)
        editor.commit()
    }
    fun getBusinessID(): String {
        return pref.getString(BUSID, "") ?: ""
    }
    fun setBusinessID(value: String) {
        editor.putString(BUSID, value)
        editor.commit()
    }

    fun getStatus(): Int {
        return pref.getInt(STATUS, 0)
    }
    fun setStatus(value: Int) {
        editor.putInt(STATUS, value)
        editor.commit()
    }
    fun getCrntLat(): String {
        return pref.getString(CURRENT_LAT, "0.0") ?: ""
    }

    fun setCrntLat(lat: String) {
        editor.putString(CURRENT_LAT, lat)
        editor.commit()
    }

    fun getCrntLong(): String {
        return pref.getString(CURRENT_LONG, "0.0") ?: ""
    }

    fun setCrntLong(long: String) {
        editor.putString(CURRENT_LONG, long)
        editor.commit()
    }

    fun clearAll() {
        editor.clear()
        editor.commit()
    }

    fun clearField(keyName: String) {
        editor.remove(keyName).apply()
    }
}
