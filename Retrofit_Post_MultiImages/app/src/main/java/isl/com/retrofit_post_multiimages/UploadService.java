package isl.com.retrofit_post_multiimages;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface UploadService {

    @POST("/FileUpload/GetEmpImages")
    public void  GetEmpImages(@Body MainImageViewModel mainImageViewModel, Callback<Boolean> callback);
}
