package pl.inpost.recruitmenttask.data.localStorage.db

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import pl.inpost.recruitmenttask.data.network.model.CustomerNetwork
import pl.inpost.recruitmenttask.data.network.model.OperationsNetwork

class TypeConverter {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromStringCustomerNetwork(value: String?): CustomerNetwork? {
        return if (value == null) {
            null
        } else {
            val adapter = moshi.adapter(CustomerNetwork::class.java)
            return adapter.fromJson(value)
        }
    }

    @TypeConverter
    fun toStringCustomerNetwork(value: CustomerNetwork?): String? {
        val adapter = moshi.adapter(CustomerNetwork::class.java)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun fromStringOperationsNetwork(value: String?): OperationsNetwork? {
        return if (value == null) {
            null
        } else {
            val adapter = moshi.adapter(OperationsNetwork::class.java)
            return adapter.fromJson(value)
        }
    }

    @TypeConverter
    fun toStringOperationsNetwork(value: OperationsNetwork?): String? {
        val adapter = moshi.adapter(OperationsNetwork::class.java)
        return adapter.toJson(value)
    }
}