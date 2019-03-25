package com.arsha.unsplash
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import org.json.JSONArray

/**
 * Created by Arsha on 3/25/2019.
 */
fun MyConnection(api: String, header: String, onSuccess: (JSONArray) -> Unit = {}, onFailed: (String) -> Unit = {}){
    AndroidNetworking.get("https://api.unsplash.com/$api")
            .addHeaders("Authorization",header)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener{
                override fun onResponse(response: JSONArray?) {
                    if (response != null) {
                        onSuccess(response)
                    }else {
                        onFailed("Response is empty")
                    }
                }

                override fun onError(anError: ANError?) {
                    if (anError != null) {
                        onFailed("Error Code : ${anError.errorCode}\nError Body ${anError.errorBody}\nError Detail : ${anError.errorDetail}")
                    }
                }
            })
}