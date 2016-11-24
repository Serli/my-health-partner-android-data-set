package com.serli.myhealthpartner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.serli.myhealthpartner.controller.MainController;
import com.serli.myhealthpartner.model.AccelerometerData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * View of the main activity.
 */
// TODO : Add send and delete acquisition.
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainController controller;

    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private Spinner activitySpinner;
    private Button startStopButton;

    private boolean acquisitionStarted;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AccelerometerService.BROADCAST_START_ACTION)) {
                startStopButton.setText(R.string.button_stop);
                acquisitionStarted = true;
            }
            if (intent.getAction().equals(AccelerometerService.BROADCAST_STOP_ACTION)) {
                startStopButton.setText(R.string.button_start);
                acquisitionStarted = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        controller = new MainController(this);

        acquisitionStarted = AccelerometerService.isRunning();

        minutePicker = (NumberPicker) findViewById(R.id.minute_picker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        secondPicker = (NumberPicker) findViewById(R.id.second_picker);
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);

        activitySpinner = (Spinner) findViewById(R.id.activity_spinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sport_activity));
        activitySpinner.setAdapter(stringArrayAdapter);

        startStopButton = (Button) findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(this);
        if (acquisitionStarted)
            startStopButton.setText(R.string.button_stop);

        IntentFilter filter = new IntentFilter();
        filter.addAction(AccelerometerService.BROADCAST_START_ACTION);
        filter.addAction(AccelerometerService.BROADCAST_STOP_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);

        //populateDataListView();

        RelativeLayout dataLayout = (RelativeLayout) findViewById(R.id.data_list_layout);
        dataLayout.setVisibility(RelativeLayout.INVISIBLE);

        Button updateButton = (Button) findViewById(R.id.button_update);
        updateButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
        if (item.getItemId() == R.id.action_show_data) {
            RelativeLayout dataLayout = (RelativeLayout) findViewById(R.id.data_list_layout);
            if (dataLayout.getVisibility() == RelativeLayout.INVISIBLE)
                dataLayout.setVisibility(RelativeLayout.VISIBLE);
            else
                dataLayout.setVisibility(RelativeLayout.INVISIBLE);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_stop_button) {
            if (acquisitionStarted) {
                controller.stopAcquisition();
            } else {
                long duration = minutePicker.getValue() * 60000 + secondPicker.getValue() * 1000;
                if (duration > 0) {
                    controller.startAcquisition(duration, activitySpinner.getSelectedItemPosition());
                }
            }
        }
        if (view.getId() == R.id.button_update) {
            populateDataListView();
        }
    }

    private void populateDataListView() {
        ListView listView = (ListView) findViewById(R.id.data_list_view);
        final List<AccelerometerData> datas = controller.getDatas();
        //final List<AccelerometerData> datas = null;
        ArrayAdapter<AccelerometerData> dataArrayAdapter = new ArrayAdapter<AccelerometerData>(this, android.R.layout.simple_list_item_2, datas) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                AccelerometerData data = datas.get(position);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss,SSS");

                text1.setText(dateFormat.format(new Date(data.getTimestamp())));
                text2.setText(String.format("x=%.2f$1 y=%.2f$2 z=%.2f$3 $4", data.getX(), data.getY(), data.getZ(), MainActivity.this.getResources().getTextArray(R.array.sport_activity)[data.getActivity()]));

                return view;
            }
        };
        listView.setAdapter(dataArrayAdapter);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
