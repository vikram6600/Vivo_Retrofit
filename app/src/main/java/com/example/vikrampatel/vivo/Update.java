package com.example.vikrampatel.vivo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vikrampatel.vivo.ApiInterface.APIInterface;
import com.example.vikrampatel.vivo.ApiManager.ApiClient;
import com.example.vikrampatel.vivo.ApiResponse.UpdateResponse;
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

public class Update extends AppCompatActivity implements Validator.ValidationListener{
    /** ButterKnife Code **/
    @NotEmpty
    @BindView(R.id.id)
    EditText id;
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
    @Length(max = 10 , min = 10, message = "enter 10 degit numbers")
    @BindView(R.id.mobile)
    EditText mobile;
    @NotEmpty
    @BindView(R.id.email)
    EditText email;
    @Length(message = "minimum 8 digits required")
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnupdate)
    Button btnupdate;
    /** ButterKnife Code **/
    Validator validator;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        validator=new Validator(this);
        validator.setValidationListener(this);

    }

    @OnClick(R.id.btnupdate)
    void btn(){
        validator.validate();

    }


    @Override
    public void onValidationSucceeded() {
        update();
    }

    private void update() {
        apiInterface = (APIInterface) ApiClient.getClient().create(APIInterface.class);
        Call<UpdateResponse> updateResponseCall = apiInterface.UPDATE_RESPONSE_CALL(
                "update",
                id.getText().toString(),
                fname.getText().toString(),
                lname.getText().toString(),
                e1.getText().toString(),
                e2.getText().toString(),
                mobile.getText().toString(),
                email.getText().toString(),
                password.getText().toString()
        );
        updateResponseCall.enqueue(new Callback<UpdateResponse>() {
            @Override
            public void onResponse(Call<UpdateResponse> call, retrofit2.Response<UpdateResponse> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(Update.this, "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Update.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Update.this, "faliure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {
                Log.e("## Message", t.getMessage().toString());
                Toast.makeText(Update.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
