package com.m163.eyepetizermvpkotlin.net.gson

import com.google.gson.*
import java.lang.reflect.Type


class IntegerDefault0Adapter : JsonSerializer<Int>, JsonDeserializer<Int> {
    override fun serialize(src: Int?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
        if (json!!.asString == "" || json.asString == "null") {
            return 0
        }
        return json.asInt
    }
}