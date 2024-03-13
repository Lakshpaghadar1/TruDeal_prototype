/*
 * Copyright (C) 2016 Marco Hernaiz Cao
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
package com.marcohc.robotocalendar;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * The roboto calendar view
 *
 * @author Marco Hernaiz Cao
 */
public class RobotoCalendarView extends LinearLayout {

    private static final String DAY_OF_THE_WEEK_TEXT = "dayOfTheWeekText";
    private static final String DAY_OF_THE_WEEK_LAYOUT = "dayOfTheWeekLayout";
    private static final String DAY_OF_THE_MONTH_LAYOUT = "dayOfTheMonthLayout";
    private static final String DAY_OF_THE_MONTH_TEXT = "dayOfTheMonthText";
    private static final String DAY_OF_THE_MONTH_BACKGROUND = "dayOfTheMonthBackground";
    private static final String DAY_OF_THE_MONTH_ABSENT = "dayOfTheMonthAbsent";
    private static final String DAY_OF_THE_MONTH_CIRCLE_IMAGE = "dayOfTheMonthCircleImage";
    private static final String DAY_OF_THE_MONTH_CIRCLE_IMAGE_1 = "dayOfTheMonthCircleImage1";
    private static final String DAY_OF_THE_MONTH_CIRCLE_IMAGE_2 = "dayOfTheMonthCircleImage2";
    private static final String DAY_OF_THE_MONTH_CIRCLE_IMAGE_3 = "dayOfTheMonthCircleImage3";

    private TextView dateTitleMonth;
    private TextView dateTitleDay;
    private TextView dateTitleYear;
    private ImageView leftButton;
    private ImageView rightButton;
    private View rootView;
    private ViewGroup robotoCalendarMonthLayout;
    private RobotoCalendarListener robotoCalendarListener;

    private Calendar currentCalendar = getCalendar();

    //private boolean isMultipleSelection = false;

    private Calendar lastSelectedDayCalendar;
    private final OnClickListener onDayOfMonthClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            // Extract day selected
            ViewGroup dayOfTheMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfTheMonthContainer.getTag();
            tagId = tagId.substring(DAY_OF_THE_MONTH_LAYOUT.length());
            TextView dayOfTheMonthText = view.findViewWithTag(DAY_OF_THE_MONTH_TEXT + tagId);

            // Extract the day from the text
            Calendar calendar = getCalendar();
            calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfTheMonthText.getText().toString()));

            markDayAsSelectedDay(calendar.getTime());

            // Fire event
            if (robotoCalendarListener == null) {
                throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
            } else {
                robotoCalendarListener.onDayClick(calendar.getTime());
            }
        }
    };
    private final OnLongClickListener onDayOfMonthLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {

            // Extract day selected
            ViewGroup dayOfTheMonthContainer = (ViewGroup) view;
            String tagId = (String) dayOfTheMonthContainer.getTag();
            tagId = tagId.substring(DAY_OF_THE_MONTH_LAYOUT.length());
            TextView dayOfTheMonthText = view.findViewWithTag(DAY_OF_THE_MONTH_TEXT + tagId);

            // Extract the day from the text
            Calendar calendar = getCalendar();
            calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dayOfTheMonthText.getText().toString()));

            markDayAsSelectedDay(calendar.getTime());

            // Fire event
            if (robotoCalendarListener == null) {
                throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
            } else {
                robotoCalendarListener.onDayLongClick(calendar.getTime());
            }
            return true;
        }
    };
    private boolean shortWeekDays = false;

    public RobotoCalendarView(Context context) {
        super(context);
        init(null);
    }

    public RobotoCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RobotoCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public RobotoCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private static String checkSpecificLocales(String dayOfTheWeekString, int i) {
        // Set Wednesday as "X" in Spanish Locale.getDefault()
        if (i == 4 && "ES".equals(Locale.getDefault().getCountry())) {
            dayOfTheWeekString = "X";
        } else {
            dayOfTheWeekString = dayOfTheWeekString.substring(0, 1).toUpperCase();
        }
        return dayOfTheWeekString;
    }

    private static int getDayIndexByDate(Calendar currentCalendar) {
        int monthOffset = getMonthOffset(currentCalendar);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        return currentDay + monthOffset;
    }

    private static int getMonthOffset(Calendar currentCalendar) {
        Calendar calendar = getCalendar();
        calendar.setTime(currentCalendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition = calendar.get(Calendar.DAY_OF_WEEK);

        if (firstDayWeekPosition == 1) {
            return dayPosition - 1;
        } else {

            if (dayPosition == 1) {
                return 6;
            } else {
                return dayPosition - 2;
            }
        }
    }

    private static int getWeekIndex(int weekIndex, Calendar currentCalendar) {
        int firstDayWeekPosition = currentCalendar.getFirstDayOfWeek();

        if (firstDayWeekPosition == 1) {
            return weekIndex;
        } else {

            if (weekIndex == 1) {
                return 7;
            } else {
                return weekIndex - 1;
            }
        }
    }

    private static boolean areInTheSameDay(Calendar calendarOne, Calendar calendarTwo) {
        return calendarOne.get(Calendar.YEAR) == calendarTwo.get(Calendar.YEAR) && calendarOne.get(Calendar.DAY_OF_YEAR) == calendarTwo.get(Calendar.DAY_OF_YEAR);
    }

    private void init(AttributeSet set) {

        if (isInEditMode()) {
            return;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflate.inflate(R.layout.roboto_calendar_view_layout, this, true);
            findViewsById(rootView);
            setUpEventListeners();
            setShortWeekDays(true);
            currentCalendar = getCalendar();
            setDate(currentCalendar.getTime());

            ViewPump.init(ViewPump.builder()
                    .addInterceptor(new CalligraphyInterceptor(
                            new CalligraphyConfig.Builder()
                                    .setFontAttrId(R.attr.fontPath)
                                    .build()))
                    .build());
        }, 1000);

    }

    public Date getDate() {
        return currentCalendar.getTime();
    }

    /**
     * Set an specific calendar to the view and update de view
     *
     * @param date, the selected date
     */
    public void setDate(Date date) {
        currentCalendar.setTime(date);
        updateView();
    }

    public Date getSelectedDay() {
        return lastSelectedDayCalendar.getTime();
    }

    public void markDayAsSelectedDay(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);

        // Clear previous current day mark
        clearSelectedDay();

        markListOfDaysAsSelectedDays();
        // Store current values as last values
        lastSelectedDayCalendar = calendar;

        // Mark current day as selected
        TextView dayOfTheMonthBackground = getDayOfMonthText(calendar);
        dayOfTheMonthBackground.setBackgroundResource(R.drawable.circle);


        TextView dayOfTheMonth = getDayOfMonthText(calendar);
        dayOfTheMonth.setTextColor(ContextCompat.getColor(getContext(), R.color.roboto_calendar_selected_day_font));
    }

    public void clearSelectedDay() {
        if (lastSelectedDayCalendar != null) {
            TextView dayOfTheMonthBackground = getDayOfMonthText(lastSelectedDayCalendar);

            // If it's today, keep the current day style
            Calendar nowCalendar = getCalendar();
            if (nowCalendar.get(Calendar.YEAR) == lastSelectedDayCalendar.get(Calendar.YEAR) && nowCalendar.get(Calendar.DAY_OF_YEAR) == lastSelectedDayCalendar.get(Calendar.DAY_OF_YEAR)) {
                dayOfTheMonthBackground.setBackgroundResource(R.drawable.ring);
            } else {
                dayOfTheMonthBackground.setBackgroundResource(android.R.color.transparent);
            }

            TextView dayOfTheMonth = getDayOfMonthText(lastSelectedDayCalendar);
            dayOfTheMonth.setTextColor(ContextCompat.getColor(getContext(), R.color.roboto_calendar_day_of_the_month_font));

            ImageView circleImage1 = getCircleImage1(lastSelectedDayCalendar);
            ImageView circleImage2 = getCircleImage2(lastSelectedDayCalendar);
            ImageView circleImage3 = getCircleImage3(lastSelectedDayCalendar);
            if (circleImage1.getVisibility() == VISIBLE) {
                DrawableCompat.setTint(circleImage1.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_1));
            }
            if (circleImage2.getVisibility() == VISIBLE) {
                DrawableCompat.setTint(circleImage2.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_2));
            }
            if (circleImage3.getVisibility() == VISIBLE) {
                DrawableCompat.setTint(circleImage2.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_3));
            }
        }
    }

    public void setShortWeekDays(boolean shortWeekDays) {
        this.shortWeekDays = shortWeekDays;
    }

    public void markAbsent(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        TextView textViewAbsent = getTextViewAbsent(calendar);
        if (lastSelectedDayCalendar != null && areInTheSameDay(calendar, lastSelectedDayCalendar)) {
            textViewAbsent.setVisibility(View.GONE);
        } else {
            textViewAbsent.setVisibility(View.GONE);
        }
    }

    public void markCircleImage(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        ImageView circleImage1 = getCircleImage1(calendar);
        circleImage1.setVisibility(View.VISIBLE);
        if (lastSelectedDayCalendar != null && areInTheSameDay(calendar, lastSelectedDayCalendar)) {
            DrawableCompat.setTint(circleImage1.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_1));
        } else {
            DrawableCompat.setTint(circleImage1.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_1));
        }
    }

    public void markCircleImage1(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        ImageView circleImage1 = getCircleImage1(calendar);
        circleImage1.setVisibility(View.VISIBLE);
        if (lastSelectedDayCalendar != null && areInTheSameDay(calendar, lastSelectedDayCalendar)) {
            DrawableCompat.setTint(circleImage1.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_1));
        } else {
            DrawableCompat.setTint(circleImage1.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_1));
        }
    }

    public void markCircleImage2(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        ImageView circleImage2 = getCircleImage2(calendar);
        circleImage2.setVisibility(View.VISIBLE);
        if (lastSelectedDayCalendar != null && areInTheSameDay(calendar, lastSelectedDayCalendar)) {
            DrawableCompat.setTint(circleImage2.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_2));
        } else {
            DrawableCompat.setTint(circleImage2.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_2));
        }
    }

    public void markCircleImage3(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        ImageView circleImage3 = getCircleImage3(calendar);
        circleImage3.setVisibility(View.VISIBLE);
        if (lastSelectedDayCalendar != null && areInTheSameDay(calendar, lastSelectedDayCalendar)) {
            DrawableCompat.setTint(circleImage3.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_3));
        } else {
            DrawableCompat.setTint(circleImage3.getDrawable(), ContextCompat.getColor(getContext(), R.color.roboto_calendar_circle_3));
        }
    }

    public void showDateTitle(boolean show) {
        if (show) {
            robotoCalendarMonthLayout.setVisibility(VISIBLE);
        } else {
            robotoCalendarMonthLayout.setVisibility(GONE);
        }
    }

    public void setRobotoCalendarListener(RobotoCalendarListener robotoCalendarListener) {
        this.robotoCalendarListener = robotoCalendarListener;
    }

    private void findViewsById(View view) {

        robotoCalendarMonthLayout = view.findViewById(R.id.robotoCalendarDateTitleContainer);
        leftButton = view.findViewById(R.id.leftButton);
        rightButton = view.findViewById(R.id.rightButton);
        dateTitleMonth = view.findViewById(R.id.monthText);
        dateTitleDay = view.findViewById(R.id.dayText);
        dateTitleYear = view.findViewById(R.id.yearText);

        LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 42; i++) {

            int weekIndex = (i % 7) + 1;
            ViewGroup dayOfTheWeekLayout = view.findViewWithTag(DAY_OF_THE_WEEK_LAYOUT + weekIndex);

            // Create day of the month
            View dayOfTheMonthLayout = inflate.inflate(R.layout.roboto_calendar_day_of_the_month_layout, null);
            View dayOfTheMonthText = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_TEXT);
            View dayOfTheMonthBackground = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_BACKGROUND);
            View dayOfTheMonthCircleImage1 = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_1);
            View dayOfTheMonthCircleImage2 = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_2);
            View dayOfTheMonthCircleImage3 = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_3);
            View dayOfTheMonthAbsent = dayOfTheMonthLayout.findViewWithTag(DAY_OF_THE_MONTH_ABSENT);

            // Set tags to identify them
            int viewIndex = i + 1;
            dayOfTheMonthLayout.setTag(DAY_OF_THE_MONTH_LAYOUT + viewIndex);
            dayOfTheMonthText.setTag(DAY_OF_THE_MONTH_TEXT + viewIndex);
            dayOfTheMonthBackground.setTag(DAY_OF_THE_MONTH_BACKGROUND + viewIndex);
            dayOfTheMonthCircleImage1.setTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_1 + viewIndex);
            dayOfTheMonthCircleImage2.setTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_2 + viewIndex);
            dayOfTheMonthCircleImage3.setTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_3 + viewIndex);
            dayOfTheMonthAbsent.setTag(DAY_OF_THE_MONTH_ABSENT + viewIndex);

            dayOfTheWeekLayout.addView(dayOfTheMonthLayout);
        }
    }

    private void setUpEventListeners() {
        dateTitleMonth.setOnClickListener(view -> {
            if (robotoCalendarListener == null) {
                throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
            }

            // Decrease month
            currentCalendar.add(Calendar.MONTH, -1);
            lastSelectedDayCalendar = null;
            updateView();

            Calendar calendar = getCalendar();
            calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH));

            robotoCalendarListener.onLeftButtonClick(calendar.getTime());
        });
        leftButton.setOnClickListener(view -> {
            if (robotoCalendarListener == null) {
                throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
            }

            // Decrease month
            currentCalendar.add(Calendar.MONTH, -1);
            lastSelectedDayCalendar = null;
            updateView();

            Calendar calendar = getCalendar();

            calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH));

            robotoCalendarListener.onLeftButtonClick(calendar.getTime());
        });

        dateTitleYear.setOnClickListener(view -> {
            if (robotoCalendarListener == null) {
                throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
            }

            // Increase month
            currentCalendar.add(Calendar.MONTH, 1);
            lastSelectedDayCalendar = null;
            updateView();

            Calendar calendar = getCalendar();

            calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH));

            robotoCalendarListener.onRightButtonClick(calendar.getTime());
        });
        rightButton.setOnClickListener(view -> {
            if (robotoCalendarListener == null) {
                throw new IllegalStateException("You must assign a valid RobotoCalendarListener first!");
            }

            // Increase month
            currentCalendar.add(Calendar.MONTH, 1);
            lastSelectedDayCalendar = null;
            updateView();

            Calendar calendar = getCalendar();
            calendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH));

            robotoCalendarListener.onRightButtonClick(calendar.getTime());
        });
    }

    private void setUpMonthLayout() {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        Date currentTime = currentCalendar.getTime();
        dateTitleDay.setText(" ");
        dateTitleYear.setText(" ");
        //dateTitleYear.setText(yearFormat.format(currentTime));
        dateTitleMonth.setText(monthFormat.format(currentTime));
    }

    private void setUpWeekDaysLayout() {
        TextView dayOfWeek;
        String dayOfTheWeekString;
        String[] weekDaysArray = new String[7];
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.getDefault());
        String[] weekdays = dateFormatSymbols.getWeekdays();

        //set days from Sunday to Saturday
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            dayOfWeek = rootView.findViewWithTag(DAY_OF_THE_WEEK_TEXT + getWeekIndex(i, currentCalendar));
            if (shortWeekDays) {
                dayOfTheWeekString = checkSpecificLocales(weekDaysArray[i - Calendar.SUNDAY] = weekdays[i], i);
            } else {
                dayOfTheWeekString = weekDaysArray[i - Calendar.SUNDAY] = weekdays[i].substring(0, 1).toUpperCase() +
                        (weekDaysArray[i - Calendar.SUNDAY] = weekdays[i]).substring(1, 3);
            }
            Log.e("LOCAL_WEEKDAY==>", dayOfTheWeekString);

            dayOfWeek.setText(dayOfTheWeekString);
        }

        //open to set days from Monday to Sunday
        /*String[] weekDaysArray = new DateFormatSymbols(Locale.getDefault()).getWeekdays();
        int length = weekDaysArray.length;
        for (int i = 1; i < length; i++) {
            dayOfWeek = rootView.findViewWithTag(DAY_OF_THE_WEEK_TEXT + getWeekIndex(i, currentCalendar));
            dayOfTheWeekString = weekDaysArray[i];
            if (shortWeekDays) {
                dayOfTheWeekString = checkSpecificLocales(dayOfTheWeekString, i);
            } else {
                dayOfTheWeekString = dayOfTheWeekString.substring(0, 1).toUpperCase() + dayOfTheWeekString.substring(1, 3);
            }

            dayOfWeek.setText(dayOfTheWeekString);
        }*/
    }

    List<Date> dates = new ArrayList<>();

    public void setSelectedDates(List<String> dateStrings) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        for (String dateString : dateStrings) {
            try {
                Date date = inputFormat.parse(dateString);
                dates.add(date);
            } catch (ParseException e) {
                // Handle parsing exception if necessary
            }
        }
        markListOfDaysAsSelectedDays();
    }

    public void markListOfDaysAsSelectedDays() {
        for (Date date : dates) {
            Calendar calendar = getCalendar();
            calendar.setTime(date);

            TextView dayOfTheMonth = getDayOfMonthText(calendar);
            dayOfTheMonth.setTextColor(ContextCompat.getColor(getContext(), R.color.roboto_calendar_month_font));

            //manage to show/hide green dot
            Calendar nowCalendar = getCalendar();
            int nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
            int selectedMonth = calendar.get(Calendar.MONTH);

            if (nowCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                    && nowCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
                    &&  nowMonth == selectedMonth) {
                System.out.println("Match found: " + date);
                markDayAsCurrentDay();
            } else {
                System.out.println("Match not found: " + date);
                // Mark current day as selected
                TextView dayOfTheMonthBackground = getDayOfMonthText(calendar);
                dayOfTheMonthBackground.setBackgroundResource(R.drawable.ring_green);
            }
        }
    }

    private void setUpDaysOfMonthLayout() {
        TextView dayOfTheMonthText;
        TextView dayOfTheMonthAbsent;
        View circleImage1;
        View circleImage2;
        View circleImage3;
        ViewGroup dayOfTheMonthContainer;
        ViewGroup dayOfTheMonthBackground;

        for (int i = 1; i < 43; i++) {

            dayOfTheMonthContainer = rootView.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + i);
            dayOfTheMonthBackground = rootView.findViewWithTag(DAY_OF_THE_MONTH_BACKGROUND + i);
            dayOfTheMonthText = rootView.findViewWithTag(DAY_OF_THE_MONTH_TEXT + i);
            dayOfTheMonthAbsent = rootView.findViewWithTag(DAY_OF_THE_MONTH_ABSENT + i);
            circleImage1 = rootView.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_1 + i);
            circleImage2 = rootView.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_2 + i);
            circleImage3 = rootView.findViewWithTag(DAY_OF_THE_MONTH_CIRCLE_IMAGE_3 + i);

            dayOfTheMonthText.setVisibility(View.INVISIBLE);
            dayOfTheMonthAbsent.setVisibility(View.GONE);
            circleImage1.setVisibility(View.GONE);
            circleImage2.setVisibility(View.GONE);
            circleImage3.setVisibility(View.GONE);

            // Apply styles
            dayOfTheMonthText.setBackgroundResource(android.R.color.transparent);
            dayOfTheMonthText.setTypeface(null, Typeface.NORMAL);
            dayOfTheMonthText.setTextColor(ContextCompat.getColor(getContext(), R.color.roboto_calendar_day_of_the_month_font));
            dayOfTheMonthContainer.setBackgroundResource(android.R.color.transparent);
            dayOfTheMonthContainer.setOnClickListener(null);
            dayOfTheMonthBackground.setBackgroundResource(android.R.color.transparent);
        }
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance(Locale.ENGLISH);
    }

    private void setUpDaysInCalendar() {
        Calendar auxCalendar = getCalendar();
        auxCalendar.setTime(currentCalendar.getTime());
        auxCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = auxCalendar.get(Calendar.DAY_OF_WEEK);
        TextView dayOfTheMonthText;
        ViewGroup dayOfTheMonthContainer;
        ViewGroup dayOfTheMonthLayout;

        // Calculate dayOfTheMonthIndex
        int dayOfTheMonthIndex = getWeekIndex(firstDayOfMonth, auxCalendar);

        for (int i = 1; i <= auxCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++, dayOfTheMonthIndex++) {
            dayOfTheMonthContainer = rootView.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + dayOfTheMonthIndex);
            dayOfTheMonthText = rootView.findViewWithTag(DAY_OF_THE_MONTH_TEXT + dayOfTheMonthIndex);
            if (dayOfTheMonthText == null) {
                break;
            }
            dayOfTheMonthContainer.setOnClickListener(onDayOfMonthClickListener);
            dayOfTheMonthContainer.setOnLongClickListener(onDayOfMonthLongClickListener);
            dayOfTheMonthText.setVisibility(View.VISIBLE);
            dayOfTheMonthText.setText(String.valueOf(i));

            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
            int currentDate = Integer.parseInt(dayFormat.format(currentCalendar.getTime()));
            //disabled previous dates
            //if (disablePreviousDates) {
            if (currentCalendar.get(Calendar.MONTH) < getCalendar().get(Calendar.MONTH)) {
                dayOfTheMonthContainer.setOnClickListener(null);
                dayOfTheMonthContainer.setOnLongClickListener(null);
                dayOfTheMonthText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.roboto_calendar_day_of_the_month_font_non_accessible, null));
            } else if (currentCalendar.get(Calendar.MONTH) == getCalendar().get(Calendar.MONTH)) {
                if (i < currentDate) {
                    dayOfTheMonthContainer.setOnClickListener(null);
                    dayOfTheMonthContainer.setOnLongClickListener(null);
                    dayOfTheMonthText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.roboto_calendar_day_of_the_month_font_non_accessible, null));
                }
                //}
            }
        }

        for (int i = 36; i < 43; i++) {
            dayOfTheMonthText = rootView.findViewWithTag(DAY_OF_THE_MONTH_TEXT + i);
            dayOfTheMonthLayout = rootView.findViewWithTag(DAY_OF_THE_MONTH_LAYOUT + i);
            if (dayOfTheMonthText.getVisibility() == INVISIBLE) {
                dayOfTheMonthLayout.setVisibility(GONE);
            } else {
                dayOfTheMonthLayout.setVisibility(VISIBLE);
            }
        }
    }

    private void markDayAsCurrentDay() {
        // If it's the current month, mark current day
        Calendar nowCalendar = getCalendar();

        if (nowCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) && nowCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)) {
            Calendar currentCalendar = getCalendar();
            currentCalendar.setTime(nowCalendar.getTime());

            TextView dayOfTheMonthBackground = getDayOfMonthText(currentCalendar);
            dayOfTheMonthBackground.setBackgroundResource(R.drawable.ring);
        }
    }

    private void updateView() {
        setUpMonthLayout();
        setUpWeekDaysLayout();
        setUpDaysOfMonthLayout();
        setUpDaysInCalendar();
        markDayAsCurrentDay();
        markListOfDaysAsSelectedDays();
    }

    private ViewGroup getDayOfMonthBackground(Calendar currentCalendar) {
        return (ViewGroup) getView(DAY_OF_THE_MONTH_BACKGROUND, currentCalendar);
    }

    private TextView getDayOfMonthText(Calendar currentCalendar) {
        return (TextView) getView(DAY_OF_THE_MONTH_TEXT, currentCalendar);
    }

    private TextView getTextViewAbsent(Calendar currentCalendar) {
        return (TextView) getView(DAY_OF_THE_MONTH_ABSENT, currentCalendar);
    }

    private ImageView getCircleImage1(Calendar currentCalendar) {
        return (ImageView) getView(DAY_OF_THE_MONTH_CIRCLE_IMAGE_1, currentCalendar);
    }

    private ImageView getCircleImage2(Calendar currentCalendar) {
        return (ImageView) getView(DAY_OF_THE_MONTH_CIRCLE_IMAGE_2, currentCalendar);
    }

    private ImageView getCircleImage3(Calendar currentCalendar) {
        return (ImageView) getView(DAY_OF_THE_MONTH_CIRCLE_IMAGE_3, currentCalendar);
    }

    private View getView(String key, Calendar currentCalendar) {
        int index = getDayIndexByDate(currentCalendar);
        return rootView.findViewWithTag(key + index);
    }

    public interface RobotoCalendarListener {

        void onDayClick(Date date);

        void onDayLongClick(Date date);

        void onRightButtonClick(Date date);

        void onLeftButtonClick(Date date);
    }

}