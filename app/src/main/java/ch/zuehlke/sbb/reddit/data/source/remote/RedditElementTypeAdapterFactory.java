package ch.zuehlke.sbb.reddit.data.source.remote;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;

import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;

import static ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement.reference;

/**
 * Created by chsc on 13.11.17.
 */

public final class RedditElementTypeAdapterFactory implements TypeAdapterFactory {

    // Effectively a singleton, no state, fully thread-safe, etc
    private static final TypeAdapterFactory elementTypeAdapterFactory = new RedditElementTypeAdapterFactory();

    private static final TypeToken<RedditPostElement.DataRedditPostElement> dateElementTypeToken = new TypeToken<RedditPostElement.DataRedditPostElement>() {
    };

    private RedditElementTypeAdapterFactory() {
    }

    // So just return the single instance and hide away the way it's instantiated
    public static TypeAdapterFactory getElementTypeAdapterFactory() {
        return elementTypeAdapterFactory;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
        // Not the RedditPostElement class?
        if ( !RedditPostElement.class.isAssignableFrom(typeToken.getRawType()) ) {
            // Then just let Gson pick up the next best type adapter
            return null;
        }
        //
        final TypeAdapter<RedditPostElement.DataRedditPostElement> dataElementTypeAdapter = gson.getDelegateAdapter(this, dateElementTypeToken);
        @SuppressWarnings("unchecked")
        final TypeAdapter<T> typeAdapter = (TypeAdapter<T>) new ElementTypeAdapter(dataElementTypeAdapter);
        return typeAdapter.nullSafe();
    }

    private static final class ElementTypeAdapter extends TypeAdapter<RedditPostElement> {

        private final TypeAdapter<RedditPostElement.DataRedditPostElement> dataTypeAdapter;

        private ElementTypeAdapter(final TypeAdapter<RedditPostElement.DataRedditPostElement> dataTypeAdapter) {
            this.dataTypeAdapter = dataTypeAdapter;
        }

        @Override
        public void write(final JsonWriter out, final RedditPostElement value)
                throws IOException {
            if ( value instanceof RedditPostElement.DataRedditPostElement) {
                dataTypeAdapter.write(out, (RedditPostElement.DataRedditPostElement) value);
            } else if ( value instanceof RedditPostElement.ReferenceRedditPostElement) {
                out.value(((RedditPostElement.ReferenceRedditPostElement) value).reference);
            } else {
                // null-protection is configured with .nullSafe() above
                throw new AssertionError(value.getClass());
            }
        }

        @Override
        public RedditPostElement read(final JsonReader in)
                throws IOException {
            final JsonToken token = in.peek();
            switch ( token ) {
                case BEGIN_OBJECT:
                    return dataTypeAdapter.read(in);
                case STRING:
                    return reference(in.nextString());
                case BEGIN_ARRAY:
                case END_ARRAY:
                case END_OBJECT:
                case NAME:
                case NUMBER:
                case BOOLEAN:
                case NULL: // null-protection is configured with .nullSafe() above
                case END_DOCUMENT:
                    throw new MalformedJsonException("Cannot parse " + token + " at " + in);
                default:
                    // If someday there are more tokens...
                    throw new AssertionError(token);
            }
        }

    }

}