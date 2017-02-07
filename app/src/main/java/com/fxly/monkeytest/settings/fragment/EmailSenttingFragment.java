package com.fxly.monkeytest.settings.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxly.monkeytest.R;
import com.fxly.spinner_libs.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import static com.fxly.monkeytest.action.PublicAction.APPSETTINGS;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class EmailSenttingFragment extends Fragment {
    MaterialEditText emailaccount,emailpassword,email_sendserver,email_sendserver_port,email_incomingserver,email_incomingserver_prot;
    MaterialSpinner email_type_spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.activity_email_settings, container, false);

        SharedPreferences myshared = getActivity().getSharedPreferences(APPSETTINGS, Activity.MODE_PRIVATE);

        emailaccount= (MaterialEditText)rootview.findViewById(R.id.email_account);
        emailpassword= (MaterialEditText)rootview.findViewById(R.id.account_password);
        email_sendserver= (MaterialEditText)rootview.findViewById(R.id.email_send_server);
        email_sendserver_port= (MaterialEditText)rootview.findViewById(R.id.email_send_port);
        email_incomingserver= (MaterialEditText)rootview.findViewById(R.id.email_incoming_server);
        email_incomingserver_prot= (MaterialEditText)rootview.findViewById(R.id.email_incoming_port);

        emailaccount.setText(myshared.getString("email_account", ""));
        emailpassword.setText(myshared.getString("email_password", ""));
        email_sendserver.setText(myshared.getString("email_sendserver", "smtp.126.com"));
        email_sendserver_port.setText(myshared.getString("email_sendserver_port", "465"));
        email_incomingserver.setText(myshared.getString("email_incomingserver", "imap.126.com"));
        email_incomingserver_prot.setText(myshared.getString("email_incomingserver_prot", "993"));



        email_type_spinner=(MaterialSpinner)rootview.findViewById(R.id.email_type_spinner);
        email_type_spinner.setItems(this.getResources().getStringArray(R.array.email_type));
        email_type_spinner.setSelectedIndex(myshared.getInt("email_type", 0));

        email_type_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SharedPreferences myshared = getActivity().getSharedPreferences(APPSETTINGS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = myshared.edit();
                editor.putInt("email_type", position);
                editor.commit();

                switch (position){
                    case 0:
                        email_sendserver.setText("smtp.126.com");
                        email_sendserver_port.setText("465");
                        email_incomingserver.setText("imap.126.com");
                        email_incomingserver_prot.setText("993");
                        break;
                    case 1:
                        email_sendserver.setText("smtp.163.com");
                        email_sendserver_port.setText("465");
                        email_incomingserver.setText("imap.163.com");
                        email_incomingserver_prot.setText("993");
                        break;
                    case 2:
                        email_sendserver.setText("smtp.qq.com");
                        email_sendserver_port.setText("465");
                        email_incomingserver.setText("imap.qq.com");
                        email_incomingserver_prot.setText("993");
                        break;
//                    case 3:
//                        email_sendserver.setText("smtp.exmail.qq.com");
//                        email_sendserver_port.setText("465");
//                        email_incomingserver.setText("imap.exmail.qq.com");
//                        email_incomingserver_prot.setText("993");
//                        break;

                }

            }
        });

        email_type_spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, R.string.noing_changed, Snackbar.LENGTH_LONG).show();
            }
        });
        return  rootview;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences mysave= getActivity().getSharedPreferences(APPSETTINGS, Activity.MODE_PRIVATE);

        mysave.edit().putString("email_account", emailaccount.getText().toString()).commit();
        mysave.edit().putString("email_password", emailpassword.getText().toString()).commit();
        mysave.edit().putString("email_sendserver", email_sendserver.getText().toString()).commit();
        mysave.edit().putString("email_sendserver_port", email_sendserver_port.getText().toString()).commit();
        mysave.edit().putString("email_incomingserver", email_incomingserver.getText().toString()).commit();
        mysave.edit().putString("email_incomingserver_prot", email_incomingserver_prot.getText().toString()).commit();



    }
}
