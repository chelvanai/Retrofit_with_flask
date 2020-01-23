package isl.com.retrofit_post_multiimages;

import java.util.List;


public class MainImageViewModel {
    private List<ImageViewModel> imagesViewModels;
    private String ServiceNo;

    public List<ImageViewModel> getImagesViewModels() {
        return imagesViewModels;
    }

    public void setImagesViewModels(List<ImageViewModel> imagesViewModels) {
        this.imagesViewModels = imagesViewModels;
    }

    public String getServiceNo() {
        return ServiceNo;
    }

    public void setServiceNo(String serviceNo) {
        ServiceNo = serviceNo;
    }

}
