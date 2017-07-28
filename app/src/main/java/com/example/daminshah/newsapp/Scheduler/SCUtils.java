package com.example.daminshah.newsapp.Scheduler;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by daminshah on 7/26/17.
 */

public class SCUtils {


    private static final String JOB_TAG="SCUtils";


    //FirebaseJObDispatcher schedules background job to update data
     public static void scheduleRefresh(@NonNull final Context context){

        Driver driver=new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher=new FirebaseJobDispatcher(driver);

        Job contraintRefresh=dispatcher.newJobBuilder()
                .setService(NewsService.class)
                .setTag(JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(0,60))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(contraintRefresh);

    }



}
