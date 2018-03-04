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

public class CodeVerifyFragment extends Fragment{

    private OnVerifyListener mListener;
    private boolean mRegisterIn;
    EditText mCodeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.code_verify_fragment,container,false);
        mCodeView = rootView.findViewById(R.id.code_edit_text);
        rootView.findViewById(R.id.verify_code_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
        rootView.findViewById(R.id.resend_code_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendCode();
            }
        });
        return rootView;
    }

    private void resendCode() {
        mListener.onResend();
    }

    private void verify() {
        if (mRegisterIn){
            return;
        }

        mCodeView.setError(null);

        String phone = mCodeView.getText().toString().trim();

        boolean cancelLogin = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)){
            mCodeView.setError(getString(R.string.field_required));
            focusView = mCodeView;
            cancelLogin = true;
        }

        if (cancelLogin) {
            focusView.requestFocus();
        } else {
            mRegisterIn = true;
            mListener.onVerify(phone);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnVerifyListener) activity;
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

    public interface OnVerifyListener{
        void onVerify(String code);
        void onResend();
    }
}
