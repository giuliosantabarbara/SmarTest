package jumapp.com.smartest.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.text.DateFormat;
import java.util.Calendar;

import agency.tango.materialintroscreen.SlideFragment;
import jumapp.com.smartest.R;


/**
 * Created by giuli on 22/02/2017.
 */

public class CustomSelectionData extends SlideFragment {

    //private CheckBox checkBox;
    LinearLayout rlDateTimeRecurrenceInfo;
    LinearLayout llDateHolder, llDateRangeHolder;


    // Views to display the chosen Date, Time & Recurrence options
    TextView tvYear, tvMonth, tvDay, tvHour,
            tvMinute, tvRecurrenceOption, tvRecurrenceRule,
            tvStartDate, tvEndDate;
    // Chosen values
    SelectedDate mSelectedDate;
    int mHour, mMinute;
    String mRecurrenceOption, mRecurrenceRule;

    // Show date, time & recurrence options that have been selected
    private void updateInfoView() {
        if (mSelectedDate != null) {
            if (mSelectedDate.getType() == SelectedDate.Type.SINGLE) {
                llDateRangeHolder.setVisibility(View.GONE);
                llDateHolder.setVisibility(View.VISIBLE);

                tvYear.setText(applyBoldStyle("YEAR: ")
                        .append(String.valueOf(mSelectedDate.getStartDate().get(Calendar.YEAR))));
                tvMonth.setText(applyBoldStyle("MONTH: ")
                        .append(String.valueOf(mSelectedDate.getStartDate().get(Calendar.MONTH))));
                tvDay.setText(applyBoldStyle("DAY: ")
                        .append(String.valueOf(mSelectedDate.getStartDate().get(Calendar.DAY_OF_MONTH))));
            } else if (mSelectedDate.getType() == SelectedDate.Type.RANGE) {
                llDateHolder.setVisibility(View.GONE);
                llDateRangeHolder.setVisibility(View.VISIBLE);

                tvStartDate.setText(applyBoldStyle("START: ")
                        .append(DateFormat.getDateInstance().format(mSelectedDate.getStartDate().getTime())));
                tvEndDate.setText(applyBoldStyle("END: ")
                        .append(DateFormat.getDateInstance().format(mSelectedDate.getEndDate().getTime())));
            }
        }

       /* tvHour.setText(applyBoldStyle("HOUR: ").append(String.valueOf(mHour)));
        tvMinute.setText(applyBoldStyle("MINUTE: ").append(String.valueOf(mMinute)));
        tvRecurrenceOption.setText(applyBoldStyle("RECURRENCE OPTION: ")
                .append(mRecurrenceOption));
        tvRecurrenceRule.setText(applyBoldStyle("RECURRENCE RULE: ").append(
                mRecurrenceRule));*/

        rlDateTimeRecurrenceInfo.setVisibility(View.VISIBLE);
    }
    // Applies a StyleSpan to the supplied text
    private SpannableStringBuilder applyBoldStyle(String text) {
        SpannableStringBuilder ss = new SpannableStringBuilder(text);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_selection_data, container, false);
        //checkBox = (CheckBox) view.findViewById(R.id.checkBox);






        llDateHolder = (LinearLayout) view.findViewById(R.id.llDateHolder);
        llDateRangeHolder = (LinearLayout) view.findViewById(R.id.llDateRangeHolder);

        // Initialize views to display the chosen Date, Time & Recurrence options
        tvYear = ((TextView) view.findViewById(R.id.tvYear));
        tvMonth = ((TextView) view.findViewById(R.id.tvMonth));
        tvDay = ((TextView) view.findViewById(R.id.tvDay));

        tvStartDate = ((TextView)view.findViewById(R.id.tvStartDate));
        tvEndDate = ((TextView) view.findViewById(R.id.tvEndDate));

        rlDateTimeRecurrenceInfo
                = (LinearLayout) view.findViewById(R.id.rlDateTimeRecurrenceInfo);

        Button b = (Button)view.findViewById(R.id.buttonClick);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // DialogFragment to host SublimePicker
                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(new SublimePickerFragment.Callback() {
                    @Override
                    public void onCancelled() {
                        rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                                        int hourOfDay, int minute,
                                                        SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                        String recurrenceRule) {

                        mSelectedDate = selectedDate;
                        mHour = hourOfDay;
                        mMinute = minute;
                        mRecurrenceOption = recurrenceOption != null ?
                                recurrenceOption.name() : "n/a";
                        mRecurrenceRule = recurrenceRule != null ?
                                recurrenceRule : "n/a";

                        updateInfoView();

                    }
                });

                //Set Option to display in picker
                SublimeOptions options = new SublimeOptions();
                options.setCanPickDateRange(true);
                options.setDisplayOptions(1);

                /*Pair<Boolean, SublimeOptions> optionsPair = getOptions();
                if (!optionsPair.first) { // If options are not valid
                    Toast.makeText(Sampler.this, "No pickers activated",
                            Toast.LENGTH_SHORT).show();
                    return;
                }*/

                // Valid options
                Bundle bundle = new Bundle();
                bundle.putParcelable("SUBLIME_OPTIONS", options);
                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE,0);
                pickerFrag.show(getActivity().getSupportFragmentManager(), "SUBLIME_PICKER");

               /* // Valid options
                Bundle bundle = new Bundle();
                bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                pickerFrag.setArguments(bundle);
                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");*/
            }
        });
        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.custom_slide_background;
    }

    @Override
    public int buttonsColor() {
        return R.color.custom_slide_buttons;
    }

    @Override
    public boolean canMoveFurther() {
        return llDateRangeHolder.isShown();//true;//checkBox.isChecked();
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.error_message_range_date);
    }

}