package com.fxly.monkeytest.settings.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.fxly.monkeytest.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import static android.content.Context.MODE_PRIVATE;
import static com.fxly.monkeytest.action.PublicAction.APPSETTINGS;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class ParametersSettingsFragment extends Fragment {

    MaterialEditText monkey_throttle,monkey_appswitch_percent,monkey_anyevent_percent,running_count;
    CheckBox ignore_crashes,ignore_timeouts,ignore_security_exceptions,ignore_killprocess_aftererrror;

    CheckBox sent_report,savelogcat,save_monkeylog;
    private boolean isCheck_ignore_crashes,isCheck_ignore_timeouts,isCheck_security_exceptions,isCheck_killprocess_aftererrror;
    SharedPreferences user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.activity_settings_ui, container, false);

            user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);

        monkey_throttle=(MaterialEditText)rootview.findViewById(R.id.monkey_throttle);
        monkey_appswitch_percent=(MaterialEditText)rootview.findViewById(R.id.monkey_appswitch_percent);
        monkey_anyevent_percent=(MaterialEditText)rootview.findViewById(R.id.monkey_anyevent_percent);
        running_count=(MaterialEditText)rootview.findViewById(R.id.running_count);

        monkey_throttle.setText(user.getString("monkey_throttle","500"));
        monkey_appswitch_percent.setText(user.getString("monkey_appswitch_percent","50"));
        monkey_anyevent_percent.setText(user.getString("monkey_anyevent_percent","50"));
        running_count.setText(user.getString("running_count","144000"));

        ignore_crashes=(CheckBox)rootview.findViewById(R.id.ignore_crashes);
        ignore_timeouts=(CheckBox)rootview.findViewById(R.id.ignore_timeouts);
        ignore_security_exceptions=(CheckBox)rootview.findViewById(R.id.ignore_security_exceptions);
        ignore_killprocess_aftererrror=(CheckBox)rootview.findViewById(R.id.ignore_killprocess_aftererrror);
        save_monkeylog=(CheckBox)rootview.findViewById(R.id.savemonkeylogs);
        savelogcat=(CheckBox)rootview.findViewById(R.id.savelogcat);
        sent_report=(CheckBox)rootview.findViewById(R.id.sent_report);





        ignore_crashes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                isCheck_ignore_crashes=b;
                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putBoolean("ignore_crashes", b);
                editor.commit();
                Toast.makeText(getActivity(),"选择状态"+b,Toast.LENGTH_SHORT).show();
//                user.edit().putBoolean("ignore_crashes",b);
            }
        });
        ignore_timeouts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//                user.edit().putBoolean("ignore_timeouts",b);
                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putBoolean("ignore_timeouts", b);
                editor.commit();
                Toast.makeText(getActivity(),"选择状态"+b,Toast.LENGTH_SHORT).show();
            }
        });
        ignore_security_exceptions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//                user.edit().putBoolean("ignore_security_exceptions",b);
                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putBoolean("ignore_security_exceptions", b);
                editor.commit();
                Toast.makeText(getActivity(),"选择状态"+b,Toast.LENGTH_SHORT).show();
            }
        });
        ignore_killprocess_aftererrror.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//                user.edit().putBoolean("ignore_killprocess_aftererrror",b);
                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putBoolean("ignore_killprocess_aftererrror", b);
                editor.commit();
                Toast.makeText(getActivity(),"选择状态"+b,Toast.LENGTH_SHORT).show();
            }
        });

        sent_report.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putBoolean("sent_report", b);
                editor.commit();

//                user.edit().putBoolean("sent_report",b);
            }
        });

        savelogcat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putBoolean("savelogcat", b);
                editor.commit();
//                user.edit().putBoolean("savelogcat",b);
            }
        });

        save_monkeylog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putBoolean("save_monkeylog", b);
                editor.commit();
//                user.edit().putBoolean("save_monkeylog",b);
            }
        });

        return  rootview;
    }


    @Override
    public void onStart() {
        super.onStart();
//        SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        ignore_crashes.setChecked(user.getBoolean("ignore_crashes",false));
        ignore_timeouts.setChecked(user.getBoolean("ignore_timeouts",false));
        ignore_security_exceptions.setChecked(user.getBoolean("ignore_security_exceptions",false));
        ignore_killprocess_aftererrror.setChecked(user.getBoolean("ignore_killprocess_aftererrror",false));
        sent_report.setChecked(user.getBoolean("sent_report",false));
        savelogcat.setChecked(user.getBoolean("savelogcat",false));
        save_monkeylog.setChecked(user.getBoolean("save_monkeylog",false));

    }

    @Override
    public void onDestroy() {


        super.onDestroy();
        user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
//        SharedPreferences user = getActivity().getSharedPreferences(APPSETTINGS, MODE_PRIVATE);
        user.edit().putString("monkey_throttle", monkey_throttle.getText().toString()).commit();
        user.edit().putString("monkey_appswitch_percent", monkey_appswitch_percent.getText().toString()).commit();
        user.edit().putString("monkey_anyevent_percent", monkey_anyevent_percent.getText().toString()).commit();
        user.edit().putString("running_count", running_count.getText().toString()).commit();


    }
}
