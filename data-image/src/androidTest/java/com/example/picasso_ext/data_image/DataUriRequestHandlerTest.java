package com.example.picasso_ext.data_image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by RonK on 2017-04-21.
 */
@RunWith(Parameterized.class)
public class DataUriRequestHandlerTest {
    private final String urlStr;

    public DataUriRequestHandlerTest(String urlStr) {
        this.urlStr = urlStr;
    }

    @Parameterized.Parameters
    public static Collection<String> data() {
        String url1 = "data:image/png;base64,iVBORw0KGgoAAA" +
                "ANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4" +
                "//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU" +
                "5ErkJggg==";
        String url2 = "data:image/gif;base64,R0lGODdhMAAwAPAAAAAAAP///ywAAAAAMAAw" +
                "AAAC8IyPqcvt3wCcDkiLc7C0qwyGHhSWpjQu5yqmCYsapyuvUUlvONmOZtfzgFz" +
                "ByTB10QgxOR0TqBQejhRNzOfkVJ+5YiUqrXF5Y5lKh/DeuNcP5yLWGsEbtLiOSp" +
                "a/TPg7JpJHxyendzWTBfX0cxOnKPjgBzi4diinWGdkF8kjdfnycQZXZeYGejmJl" +
                "ZeGl9i2icVqaNVailT6F5iJ90m6mvuTS4OK05M0vDk0Q4XUtwvKOzrcd3iq9uis" +
                "F81M1OIcR7lEewwcLp7tuNNkM3uNna3F2JQFo97Vriy/Xl4/f1cf5VWzXyym7PH" +
                "hhx4dbgYKAAA7";
        return Arrays.asList(url1, url2);

    }

    private DataUriRequestHandler loader;

    @org.junit.Before
    public void setUp() throws Exception {
        loader = new DataUriRequestHandler();
    }

    @Test
    public void fullIntegration() throws Exception {
        Picasso picasso = new Picasso.Builder(InstrumentationRegistry.getTargetContext()).addRequestHandler(new DataUriRequestHandler()).build();
        Bitmap bitmap = picasso.load(urlStr).get();

        assertNotNull(bitmap);
    }

    @org.junit.Test
    public void valid_canHandleRequest() throws Exception {
        Uri uri = getDataUri();
        Request request = new Request.Builder(uri).build();
        boolean canHandleRequest = loader.canHandleRequest(request);

        assertTrue(canHandleRequest);

        RequestHandler.Result result = loader.load(request, 0);

        Bitmap bitmap = result.getBitmap();
        assertNull(bitmap);
        InputStream stream = result.getStream();
        assertNotNull(stream);
        assertEquals(Picasso.LoadedFrom.DISK, result.getLoadedFrom());

        Bitmap decodedBitmap = BitmapFactory.decodeStream(stream);
        assertNotNull(decodedBitmap);
    }

    @org.junit.Test
    public void invalid_canHandleRequest() throws Exception {
        Request request = new Request.Builder(Uri.parse("http://square.github.io/picasso/")).build();
        boolean canHandleRequest = loader.canHandleRequest(request);

        assertFalse(canHandleRequest);
    }

    private Uri getDataUri() {

        return Uri.parse(urlStr);
    }

}