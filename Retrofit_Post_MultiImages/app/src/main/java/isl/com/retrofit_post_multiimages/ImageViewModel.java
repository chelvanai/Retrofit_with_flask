package isl.com.retrofit_post_multiimages;

public class ImageViewModel {
    private String ImageBase64File ;
    private String ImageFileName ;

    public String getImageBase64File() {
        return ImageBase64File;
    }

    public void setImageBase64File(String imageBase64File) {
        ImageBase64File = imageBase64File;
    }

    public String getImageFileName() {
        return ImageFileName;
    }

    public void setImageFileName(String imageFileName) {
        ImageFileName = imageFileName;
    }
}
