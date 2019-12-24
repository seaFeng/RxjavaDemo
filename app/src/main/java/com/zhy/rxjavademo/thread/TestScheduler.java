package com.zhy.rxjavademo.thread;

import io.reactivex.rxjava3.core.Scheduler;

public class TestScheduler extends Scheduler {
    @Override
    public Worker createWorker() {
        return null;
    }
}
