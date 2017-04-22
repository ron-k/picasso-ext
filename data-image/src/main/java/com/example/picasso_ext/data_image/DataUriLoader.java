package com.example.picasso_ext.data_image;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Base64;

/**
 * Created by RonK on 2017-04-22.
 */

class DataUriLoader {
    static final String SCHEME = "data";
    private static final String BASE64_INDICATOR = ";base64";

    byte[] getPayload(@NonNull Uri uri) {
        String data = uri.getSchemeSpecificPart();

        String[] splitData = data.split(",", 2);
        byte[] buf;
        if (splitData.length == 2) {
            String descriptor = splitData[0];
            if (descriptor.endsWith(BASE64_INDICATOR)) {
                buf = Base64.decode(splitData[splitData.length - 1], Base64.DEFAULT);
            } else {
                buf = splitData[splitData.length - 1].getBytes();
            }
        } else {
            buf = splitData[splitData.length - 1].getBytes();
        }
        return buf;
    }
}
