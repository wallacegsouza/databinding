package com.example.android.boardingpass;

/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.boardingpass.databinding.ActivityMainBinding;
import com.example.android.boardingpass.utilities.FakeDataUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Create a data binding instance called binding of type ActivityMainBinding
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the Content View using DataBindingUtil to the activity_main layout
        /*
        * DataBindUtil.setContentView replaces our normal call of setContent view.
        * DataBindingUtil also created our ActivityMainBinding that we will eventually use to
        * display all of our data.
        */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Load a BoardingPassInfo object with fake data using FakeDataUtils
        BoardingPassInfo fakeBoardingInfo = FakeDataUtils.generateFakeBoardingPassInfo();
        // Call displayBoardingPassInfo and pass the fake BoardingInfo instance
        displayBoardingPassInfo(fakeBoardingInfo);
    }

    private void displayBoardingPassInfo(BoardingPassInfo info) {
        // Use binding to set the Text in all the textViews using the data in info
        binding.textViewPassengerName.setText(info.passengerName);
        binding.textViewOriginAirport.setText(info.originCode);
        binding.textViewFlightCode.setText(info.flightCode);
        binding.textViewDestinationAirport.setText(info.destCode);
        // Use a SimpleDateFormat formatter to set the formatted value in time text views
        SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.timeFormat),
            Locale.getDefault());
        String boardingTime = formatter.format(info.boardingTime);
        String departureTime = formatter.format(info.departureTime);
        String arrivalTime = formatter.format(info.arrivalTime);
        binding.textViewBoardingTime.setText(boardingTime);
        binding.textViewDepartureTime.setText(departureTime);
        binding.textViewArrivalTime.setText(arrivalTime);
        // Use TimeUnit methods to format the total minutes until boarding
        long totalMinutesUntilBoarding = info.getMinutesUntilBoarding();
        long hoursUntilBoarding = TimeUnit.MINUTES.toHours(totalMinutesUntilBoarding);
        long minutesLessHoursUntilBoarding =
            totalMinutesUntilBoarding - TimeUnit.HOURS.toMinutes(hoursUntilBoarding);
        String hoursAndMinutesUntilBoarding = getString(R.string.countDownFormat,
            hoursUntilBoarding,
            minutesLessHoursUntilBoarding);
        binding.textViewBoardingInCountdown.setText(hoursAndMinutesUntilBoarding);
        binding.textViewTerminal.setText(info.departureTerminal);
        binding.textViewGate.setText(info.departureGate);
        binding.textViewSeat.setText(info.seatNumber);
    }
}

