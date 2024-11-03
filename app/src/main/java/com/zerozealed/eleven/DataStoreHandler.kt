package com.zerozealed.eleven

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zerozealed.eleven.model.Time
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val DATA_STORE_NAME = "Ele.ven"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

data object DataStoreHandler {

    private const val TAG = "DataStoreHandler"
    private val KEY_FLEX_MINUTES = intPreferencesKey("FlexKey")

    lateinit var flexFlow: Flow<Int>

    fun init(context: Context) {
        flexFlow = context.dataStore.data.map { preferences ->
            (preferences[KEY_FLEX_MINUTES] ?: 0).also {
                Log.d(TAG, "Flex updated to $it")
            }
        }
    }

    suspend fun addFlex(context: Context, time: Time) {
        context.dataStore.edit { prefs ->
            val currentFlex = prefs[KEY_FLEX_MINUTES] ?: 0
            prefs[KEY_FLEX_MINUTES] = currentFlex + time.totalMinutes
        }
    }
}