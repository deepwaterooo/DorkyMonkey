package dev.dorkymonkey;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.ViewGroup;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
import android.app.Activity;

public class MyFragment extends Fragment {  

    public static MyFragment newInstance() {
        return new MyFragment();
    }
 
    public MyFragment() {
    }
 
    @Nullable
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
        
    }  

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView contentTxt = (TextView)view.findViewById(R.id.parsedResult);
        Button cancelBtn = (Button)view.findViewById(R.id.cancelBtn);
        Button confirmBtn = (Button)view.findViewById(R.id.confirmBtn);
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Get rage face names and descriptions.
        final Resources resources = context.getResources();
        mNames = resources.getStringArray(R.array.names);
        mDescriptions = resources.getStringArray(R.array.descriptions);
        mUrls = resources.getStringArray(R.array.urls);
 
        // Get rage face images.
        final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
        final int imageCount = mNames.length;
        mImageResIds = new int[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageResIds[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
    }
 
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_rage_comic_list, container, false);
 
        final Activity activity = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        recyclerView.setAdapter(new RageComicAdapter(activity));
        return view;
    }

    public void setTextViewText(String value){
        contentTxt.setText(value);
    }*/
}  
