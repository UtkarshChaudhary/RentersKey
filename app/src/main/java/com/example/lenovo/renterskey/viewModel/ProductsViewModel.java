package com.example.lenovo.renterskey.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.lenovo.renterskey.repository.ProductRepository;
import com.example.lenovo.renterskey.util.AbsentLiveData;
import com.example.lenovo.renterskey.util.Objects;
import com.example.lenovo.renterskey.vo.Products;
import com.example.lenovo.renterskey.vo.Resource;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by lenovo on 28-12-2017.
 */

public class ProductsViewModel extends ViewModel {

    @VisibleForTesting
    final MutableLiveData<String> categoryOfItem=new MutableLiveData<>();
    private final LiveData<Resource<List<Products>>> productList;

    @Inject
    public ProductsViewModel(ProductRepository repository){
         productList = Transformations.switchMap(categoryOfItem, input -> {
            if (input==null||input.trim().length()==0) {
                return AbsentLiveData.create();
            }
             return repository.loadProducts(input); //

        });
    }

    public LiveData<Resource<List<Products>>> getProducts(){
        return productList; //3
    }

    public void retry(){
        String current=categoryOfItem.getValue();
        if(current!=null&&!current.isEmpty()){
            categoryOfItem.setValue(current);
        }
    }


    public void setCategory(String category){

         categoryOfItem.setValue(category);    //
    }



//    static class LoadMoreState {
//        private final boolean running;
//        private final String errorMessage;
//        private boolean handledError = false;
//
//        LoadMoreState(boolean running, String errorMessage) {
//            this.running = running;
//            this.errorMessage = errorMessage;
//        }
//
//        boolean isRunning() {
//            return running;
//        }
//
//        String getErrorMessage() {
//            return errorMessage;
//        }
//
//        String getErrorMessageIfNotHandled() {
//            if (handledError) {
//                return null;
//            }
//            handledError = true;
//            return errorMessage;
//        }
//    }
//
//    @VisibleForTesting
//    static class NextPageHandler implements Observer<Resource<Boolean>>{
//
//        @Nullable
//        private LiveData<Resource<Boolean>> nextPageLiveData;
//        private final MutableLiveData<LoadMoreState> loadMoreState = new MutableLiveData<>();
//        private String category;
//        private final ProductRepository repository;
//
//        @VisibleForTesting
//        boolean hasMore;
//
//        @VisibleForTesting
//        NextPageHandler(ProductRepository repository) {
//            this.repository = repository;
//            reset();
//        }
//
//
//        @Override
//        public void onChanged(@Nullable Resource<Boolean> booleanResource) {
//
//        }
//    }

}
