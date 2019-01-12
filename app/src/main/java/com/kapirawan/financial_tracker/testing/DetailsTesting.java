package com.kapirawan.financial_tracker.testing;

import android.util.Log;

import com.kapirawan.financial_tracker.activities.ActivityMain;
import com.kapirawan.financial_tracker.repository.AppRepository;

public class DetailsTesting {
    public interface Callback {
        void callback();
    }

    AppRepository repo;
    ActivityMain activity;
    private final String label = "TESTING";
    private SumTesting.Callback callback;

    public DetailsTesting(AppRepository appRepository, ActivityMain activity) {
        repo = appRepository;
        this.activity = activity;
    }

    public void test(SumTesting.Callback callback) {
        this.callback = callback;
        Log.i(label, "*** Details Testing Commencing ***");
        this.repo.getDetails(1, 0).observe(this.activity, details ->{
            Log.i(label, "no. of details retrieved: " + details.size());
            for (String detail : details) {
                Log.i(label, "detail: " + detail);
            }
        });
    }
}
