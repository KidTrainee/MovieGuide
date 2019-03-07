package com.esoxjem.movieguide.common;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LogcatFragment extends Fragment {
    protected String TAG = "DEBUG_FRAGMENT_" + getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach" + getAllState());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate" + getAllState());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView" + getAllState());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated" + getAllState());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart" + getAllState());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume" + getAllState());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState" + getAllState());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause" + getAllState());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop" + getAllState());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView" + getAllState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy" + getAllState());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach" + getAllState());
    }

    public String getAllState() {
//        return ", isAdded= " + isAdded() + ", isDetached= " + isDetached()
//                + ", isHidden= " + isHidden() + ", isInLayout= " + isInLayout()
//                + ", isResumed= " + isResumed() + ", isVisible= " + isVisible() + ", isStateSaved= " + isStateSaved();
        return "";
    }
}
