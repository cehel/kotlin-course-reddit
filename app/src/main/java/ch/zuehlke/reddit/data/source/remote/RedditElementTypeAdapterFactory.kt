package ch.zuehlke.reddit.data.source.remote

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.google.gson.stream.MalformedJsonException

import java.io.IOException

import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement
import ch.zuehlke.reddit.data.source.remote.model.posts.RedditPostElement.Companion.reference


/**
 * Created by chsc on 13.11.17.
 */

class RedditElementTypeAdapterFactory private constructor() : TypeAdapterFactory {

    override fun <T> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
        // Not the RedditPostElement class?
        if (!RedditPostElement::class.java!!.isAssignableFrom(typeToken.rawType)) {
            // Then just let Gson pick up the next best type adapter
            return null
        }
        //
        val dataElementTypeAdapter = gson.getDelegateAdapter<RedditPostElement.DataRedditPostElement>(this, dateElementTypeToken)
        val typeAdapter = ElementTypeAdapter(dataElementTypeAdapter) as TypeAdapter<T>
        return typeAdapter.nullSafe()
    }

    private class ElementTypeAdapter  constructor(private val dataTypeAdapter: TypeAdapter<RedditPostElement.DataRedditPostElement>) : TypeAdapter<RedditPostElement>() {

        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: RedditPostElement) {
            if (value is RedditPostElement.DataRedditPostElement) {
                dataTypeAdapter.write(out, value)
            } else if (value is RedditPostElement.ReferenceRedditPostElement) {
                out.value(value.reference)
            } else {
                throw AssertionError(value.javaClass)
            }
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): RedditPostElement {
            val token = `in`.peek()
            when (token) {
                JsonToken.BEGIN_OBJECT -> return dataTypeAdapter.read(`in`)
                JsonToken.STRING -> return reference(`in`.nextString())
                JsonToken.BEGIN_ARRAY, JsonToken.END_ARRAY, JsonToken.END_OBJECT, JsonToken.NAME, JsonToken.NUMBER, JsonToken.BOOLEAN, JsonToken.NULL, JsonToken.END_DOCUMENT -> throw MalformedJsonException("Cannot parse $token at $`in`")
                else ->
                    // If someday there are more tokens...
                    throw AssertionError(token)
            }
        }

    }

    companion object {

        // Effectively a singleton, no state, fully thread-safe, etc
        // So just return the single instance and hide away the way it's instantiated
        val elementTypeAdapterFactory: TypeAdapterFactory = RedditElementTypeAdapterFactory()

        private val dateElementTypeToken = object : TypeToken<RedditPostElement.DataRedditPostElement>() {

        }
    }

}