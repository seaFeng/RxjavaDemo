package com.zhy.rxjavademo;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class RxT1Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "RxT1Fragment";

    Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Throwable {
            emitter.onNext("更新1");
            emitter.onNext("更新2");
            emitter.onComplete();
        }
    });

    Observer<String> reader = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.e(TAG, "onSubscribe()");
        }

        @Override
        public void onNext(String s) {
            Log.e(TAG, "onNext()");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError()");
        }

        @Override
        public void onComplete() {
            Log.e(TAG, "onComplete()");
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rxt1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.rx_t1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rx_t1:
                //observable.subscribe(reader);
                rxT();
                break;
            case R.id.rx_t2:
                //rxT2();
                rx2();
                break;
        }
    }

    private void rx3() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Throwable {

            }
        }).subscribe(it -> Log.e(TAG,it));

        /*Observable.create { aSubscriber ->
                50.times { i ->
            if (!aSubscriber.unsubscribed) {
                aSubscriber.onNext("value_${i}")
            }
        }
            // after sending all values we complete the sequence
            if (!aSubscriber.unsubscribed) {
                aSubscriber.onCompleted()
            }
        }*/

        //Observable.create
    }

    private void rx2() {
        String[] strs = {"1","2","3"};
        Flowable.create(emitter -> {

        }, BackpressureStrategy.BUFFER)
        ;
        Flowable.fromArray(strs).subscribe(s -> {
            Log.e(TAG,s);
        });

        Observable<String> observable = Observable.fromArray(strs);
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void rxT2() {
        /*Observable.from(folders).flatMap(new Func1<File, Observable<File>>() {
            @Override
            public Observable<File> call(File file) {
                return Observable.from(file.listFiles());
            }
        }).filter(new Func1<File, Boolean>() {
            @Override
            public Boolean call(File file) {
                return file.getName().endsWith(".png");
            }
        }).map(new Func1<File, Bitmap>() {
            @Override
            public Bitmap call(File file) {
                return getBitmapFromFile(file);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                imageCollectorView.addImage(bitmap);
            }
        });*/

        /*Observable.from(folders).flatMap((Func1) (folder) -> {
            Observable.from(file.listFiles());
        }).filter((Func1) (file) -> {
            file.getName().endsWith(".png");
        }).map((Func1) (file) -> {
            getBitmapFromFile(file);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Action1) (bitmap) -> {
            imageCollectorView.addImage(bitmap);
        });*/
    }

    private void rxT() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Throwable {
                Log.e(TAG, "发布执行线程 == " + Thread.currentThread());
                emitter.onNext("更新了");
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {

                        Log.e(TAG, "onNext:" + Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete()");
                    }
                });
    }
}
