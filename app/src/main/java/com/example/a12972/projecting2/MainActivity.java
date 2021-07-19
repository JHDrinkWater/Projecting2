package com.example.a12972.projecting2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> map = new HashMap<String, String>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList object) {
                List<Category> categories = object.getCategories();
                int size = categories.size();
                Log.d(TAG, "onSuccess: Category size ------> " + size);
                for (Category category : categories) {
                    Log.d(TAG, "onSuccess: category name -------> " + category.getCategoryName());
                }
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "onError: error code" + code + " error message " + message);
            }
        });
    }
}
