package com.example.picasso_ext.data_image;

import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by RonK on 2017-04-21.
 */
public class DataUriRequestHandler extends RequestHandler {

    private final DataUriLoader loader;

    public DataUriRequestHandler() {
        this(new DataUriLoader());
    }

    DataUriRequestHandler(@NonNull DataUriLoader loader) {
        this.loader = loader;
    }

    @Override
    public boolean canHandleRequest(Request request) {
        return DataUriLoader.SCHEME.equals(request.uri.getScheme());
    }

    @Override
    public Result load(Request request, int networkPolicy) throws IOException {
        byte[] buf = loader.getPayload(request.uri);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
        return new Result(inputStream, Picasso.LoadedFrom.DISK);
    }

}
