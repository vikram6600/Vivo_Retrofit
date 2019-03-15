package com.example.vikrampatel.vivo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vikrampatel.vivo.ApiInterface.APIInterface;
import com.example.vikrampatel.vivo.ApiManager.ApiClient;
import com.example.vikrampatel.vivo.ApiResponse.RegisterResponse;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class Main2Activity extends AppCompatActivity implements Validator.ValidationListener {
    /**
     * ButterKnife Code
     **/
    @NotEmpty
    @BindView(R.id.fname)
    EditText fname;
    @NotEmpty
    @BindView(R.id.lname)
    EditText lname;
    @NotEmpty
    @BindView(R.id.e1)
    EditText e1;
    @NotEmpty
    @BindView(R.id.e2)
    EditText e2;
    @Length(max = 10, min = 10, message = "enter 10 degit numbers")
    @BindView(R.id.mobile)
    EditText mobile;
    @NotEmpty
    @BindView(R.id.email)
    EditText email;
    @Length(message = "minimum 6 digits are requirre")
    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.btnsubmit)
    Button btnsubmit;

    Validator validator;

    APIInterface apiInterface;

    /**
     * ButterKnife Code
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);


        validator = new Validator(this);
        validator.setValidationListener(this);
    }
    @OnClick(R.id.btnsubmit)
    void Btnsubmit(){
        validator.validate();

    }

    @Override
    public void onValidationSucceeded() {
        registration();
    }


    private void registration() {
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        Call<RegisterResponse> registerResponseCall = apiInterface.REGISTER_RESPONSE_CALL(
                "registers",
                fname.getText().toString(),
                lname.getText().toString(),
                e1.getText().toString(),
                e2.getText().toString(),
                mobile.getText().toString(),
                email.getText().toString(),
                password.getText().toString()
        );
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(Main2Activity.this, "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Main2Activity.this, "faliure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("## Message", t.getMessage().toString());
                Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
// validation fail

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);


            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
