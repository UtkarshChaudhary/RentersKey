package com.example.lenovo.renterskey.Networking;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by lenovo on 27-12-2017.
 */

public class ApiResponse<T> {

    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
       }

    public ApiResponse(Response<T> response) {
        code = response.code();
        if(response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Log.e("abcdefg","ApiResponse-1");
                    Timber.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
//        String linkHeader = response.headers().get("link");
//        if (linkHeader == null) {
//            links = Collections.emptyMap();
//        } else {
//            links = new ArrayMap<>();
//            Matcher matcher = LINK_PATTERN.matcher(linkHeader);
//
//            while (matcher.find()) {
//                int count = matcher.groupCount();
//                if (count == 2) {
//                    links.put(matcher.group(2), matcher.group(1));
//                }
//            }
//        }
    }

    public boolean isSuccessful(){
        Log.e("abcdefg","ApiResponse-2");
        return code>=200 && code<300;
    }

//    public Integer getNextPage() {
//        String next = links.get(NEXT_LINK);
//        if (next == null) {
//            return null;
//        }
//        Matcher matcher = PAGE_PATTERN.matcher(next);
//        if (!matcher.find() || matcher.groupCount() != 1) {
//            return null;
//        }
//        try {
//            return Integer.parseInt(matcher.group(1));
//        } catch (NumberFormatException ex) {
//            Log.e("abcdefg","ApiResponse-3");
//            Timber.w("cannot parse next page from %s", next);
//            return null;
//        }
//    }


}
