package com.suliteous.phoneauth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RegisterFrgment extends Fragment{

    private OnRegisterListener mListener;
    private boolean mRegisterIn;
    private EditText mPhoneView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_fragment,container,false);

        mPhoneView = (EditText) rootView.findViewById(R.id.phone_edit_text);
        rootView.findViewById(R.id.phone_sent_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        return rootView;
    }

    private void register() {
        if (mRegisterIn){
            return;
        }

        mPhoneView.setError(null);

        String phone = mPhoneView.getText().toString().trim();

        boolean cancelLogin = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)){
            mPhoneView.setError(getString(R.string.field_required));
            focusView = mPhoneView;
            cancelLogin = true;
        }else if (!isPhoneValid(phone)){
            mPhoneView.setError(getString(R.string.invalid_phone));
            focusView = mPhoneView;
            cancelLogin = true;
        }

        if (cancelLogin) {
            focusView.requestFocus();
        } else {
            mRegisterIn = true;
            mListener.onRegister(phone);
            mPhoneView.setText("");
        }
    }

    private boolean isPhoneValid(String phone) {
        int digit = 0;

        for (int i = 0; i < phone.length(); i++) {
            if (Character.isDigit(phone.charAt(i)))
                digit++;

        }

        return phone.length() == 10 && (phone.startsWith("7") || phone.startsWith("8") || phone.startsWith("9")) && digit == 10;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRegisterListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegisterListener{
        void onRegister(String phone);
    }
}
