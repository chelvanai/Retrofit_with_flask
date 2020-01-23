package isl.com.retrofit_post_multiimages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;


public class RestClient {
    private UploadService uploadService;

    private String URL =" http://127.0.0.1:5000/upload";

    public RestClient(){
        Gson localGson = new GsonBuilder().create();

        this.uploadService = ((UploadService)new RestAdapter.Builder()
                .setEndpoint(URL)
                .setConverter(new GsonConverter(localGson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor()
                {
                    public void intercept(RequestFacade requestFacade)
                    {
                    /*if (URL.contains("10.0.2.2")) {
                        requestFacade.addHeader("Host", "localhost");
                    }*/
                    }
                })
                .build().create(UploadService.class));
    }

    public UploadService getService()
    {
        return this.uploadService;
    }


}
