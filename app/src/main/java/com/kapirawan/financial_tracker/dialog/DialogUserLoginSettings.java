package com.kapirawan.financial_tracker.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.model.User;
import com.kapirawan.financial_tracker.model.UserCredentials;
import com.kapirawan.financial_tracker.webservice.RetrofitClient;
import com.kapirawan.financial_tracker.webservice.WebServiceAPILogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogUserLoginSettings extends DialogPreference
        implements DialogInterface.OnClickListener{

    EditText editTextUsername;
    EditText editTextPassword;
    WebServiceAPILogin webServiceAPI;

    public DialogUserLoginSettings(Context context, AttributeSet attrs){
        super(context, attrs);
        setDialogLayoutResource(R.layout.dialog_user_login_settings);
        setPositiveButtonText(R.string.login);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
        setDialogTitle(R.string.login_dialog_title);
        webServiceAPI = RetrofitClient.getRetrofitInstance().create(WebServiceAPILogin.class);
    }

    @Override
    protected void onBindDialogView(View view) {
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        super.onBindDialogView(view);
    }

    @Override
    public void onClick(DialogInterface dialog, int id){
        if(id == DialogInterface.BUTTON_POSITIVE){
            onClick_logIn();
        }
    }

    private void onClick_logIn(){
        UserCredentials credentials = new UserCredentials(editTextUsername.getText().toString(),
                editTextPassword.getText().toString());
        Call<User> call = webServiceAPI.authenticateUser(credentials);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    User user = response.body();
                    persistString(new Gson().toJson(user));
                    setSummary("you are logged as " + user.getUsername());
                } else {
                    Log.e("DialogUserLogin", "Connection failed, response code: " + response.code());
                    Log.e("DialogUserLogin", "Message: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("DialogUserLogin", "Call failed:" + t.getMessage());
            }
        });
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue){
        if (restorePersistedValue) {
            User user = new Gson().fromJson(this.getPersistedString(""), User.class) ;
            setSummary("you are logged as " + user.getUsername());
        }
    }
}