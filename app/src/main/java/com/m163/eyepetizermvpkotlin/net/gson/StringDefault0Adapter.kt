package com.m163.eyepetizermvpkotlin.net.gson

import com.google.gson.*
import java.lang.reflect.Type


class StringDefault0Adapter : JsonSerializer<String>, JsonDeserializer<String> {
    override fun serialize(src: String?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): String {
        if (json!!.asString == null) {
            return ""
        }
        if (json!!.asString == "" || json.asString == "null") {
            return ""
        }
        return json.asString
    }

}