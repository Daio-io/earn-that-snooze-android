package io.daio.earnthatsnooze.networking;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;

import io.daio.earnthatsnooze.App;
import io.daio.trivapiclient.TrivapiHttpClient;

public class TrivapiNetworkClient implements TrivapiHttpClient {

    @Override
    public void request(final String url,
                        final OnFailureCallback onFailureCallback,
                        final OnSuccessCallback onSuccessCallback) {

        RequestQueue queue = Volley.newRequestQueue(App.getAppContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccessCallback.onSuccess(url, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onFailureCallback.onFailure(url, new IOException(error.getMessage()));
                    }

                });

        queue.add(stringRequest);
    }

}
