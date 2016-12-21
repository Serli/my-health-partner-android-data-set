package com.serli.myhealthpartner;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ServiceTestRule;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeoutException;

@RunWith(JUnit4.class)
public class AccelerometerServiceTest {

    @Rule
    public final ServiceTestRule serviceRule = new ServiceTestRule();

    @Test
    public void launchAndStopService() throws TimeoutException {
        Intent serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), AccelerometerService.class);
        serviceIntent.putExtra("duration", 1000);
        serviceIntent.putExtra("activity", 0);
        InstrumentationRegistry.getTargetContext().startService(serviceIntent);
        waitForServiceState(true, 500);
        Assert.assertTrue("Service failed to start", AccelerometerService.isRunning());

        putDelay(1000 + InstrumentationRegistry.getTargetContext().getResources().getInteger(R.integer.start_delay));
        Assert.assertFalse("Service is not automatically stopped after the acquisition time", AccelerometerService.isRunning());


        InstrumentationRegistry.getTargetContext().startService(serviceIntent);
        waitForServiceState(true, 500);
        serviceIntent = new Intent(InstrumentationRegistry.getTargetContext(), AccelerometerService.class);
        InstrumentationRegistry.getTargetContext().stopService(serviceIntent);
        waitForServiceState(false, 500);
        Assert.assertFalse("Service is not stopped when stopService is called", AccelerometerService.isRunning());
    }

    private void waitForServiceState(boolean state, int maxDelay) {
        int cpt = 0;
        while (AccelerometerService.isRunning() != state && ++cpt < maxDelay / 10) {
            putDelay(10);
        }
    }

    private void putDelay(int delay) {
        SystemClock.sleep(delay);
    }

}
