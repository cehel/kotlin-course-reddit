package ch.zuehlke.sbb.reddit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ch.zuehlke.sbb.reddit.data.source.remote.model.posts.RedditPostElement;
import ch.zuehlke.sbb.reddit.util.DateUtils;

import static com.google.common.base.Strings.repeat;

/**
 * Created by chsc on 13.11.17.
 */

public abstract class AbstractTestCase {

    protected String readJsonFile (String filename) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }

        return sb.toString();
    }

    protected static void dump(final Iterable<RedditPostElement> abstractElements) {
        dump(abstractElements, 0);
    }

    protected static void dump(final Iterable<RedditPostElement> abstractElements, final int level) {
        final String tab = repeat(".", level);
        for ( final RedditPostElement e : abstractElements ) {
            if ( e instanceof RedditPostElement.DataRedditPostElement) {
                final RedditPostElement.DataRedditPostElement dataElement = (RedditPostElement.DataRedditPostElement) e;
                System.out.print(tab);
                System.out.print("DATA=");
                System.out.println(dataElement.kind);
                if ( dataElement.data.children != null ) {
                    dump(dataElement.data.children, level + 1);
                }
                if ( dataElement.data.replies != null ) {
                    final RedditPostElement replies = dataElement.data.replies;
                    if ( dataElement.data.replies instanceof RedditPostElement.DataRedditPostElement) {
                        dump(((RedditPostElement.DataRedditPostElement) replies).data.children, level + 1);
                    } else if ( dataElement.data.replies instanceof RedditPostElement.ReferenceRedditPostElement) {
                        System.out.print(tab);
                        System.out.print("REF=");
                        System.out.println(((RedditPostElement.ReferenceRedditPostElement) dataElement.data.replies).reference);
                    } else {
                        throw new AssertionError(replies.getClass());
                    }
                }
                if(dataElement.data.author != null){
                    System.out.print("Author=");
                    System.out.println(dataElement.data.author);
                }

                if(dataElement.data.body != null){
                    System.out.print("Body=");
                    System.out.println(dataElement.data.body);
                }

                if(dataElement.data.created != 0L){
                    System.out.print("Created=");
                    System.out.println(DateUtils.friendlyTime(dataElement.data.created));
                }

                if(dataElement.data.depth != 0){
                    System.out.print("Depth=");
                    System.out.println(""+dataElement.data.depth);
                }

            } else if ( e instanceof RedditPostElement.ReferenceRedditPostElement) {
                System.out.print(tab);
                System.out.print("REF=");
                System.out.println(((RedditPostElement.ReferenceRedditPostElement) e).reference);
            } else {
                throw new IllegalArgumentException(String.valueOf(e));
            }
        }
    }
}
