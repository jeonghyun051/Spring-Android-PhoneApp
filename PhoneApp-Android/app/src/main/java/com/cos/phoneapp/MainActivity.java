package com.cos.phoneapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    private RecyclerView rvphone;
    private PhoneAdapter phoneAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab_save);

        fab.setOnClickListener(v -> {
            addContact();
        });


        PhoneService phoneService = PhoneService.retrofit.create(PhoneService.class);
        Call<CMRespDto<List<Phone>>> call = phoneService.findAll();

        call.enqueue(new Callback<CMRespDto<List<Phone>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Phone>>> call, Response<CMRespDto<List<Phone>>> response) {
                CMRespDto<List<Phone>> cmRespDto = response.body();
                List<Phone> phones = cmRespDto.getData();
                //어댑터에 넘기기

                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false);
                rvphone = findViewById(R.id.rv_phone);
                rvphone.setLayoutManager(manager);
                phoneAdapter = new PhoneAdapter(phones);

                rvphone.setAdapter(phoneAdapter);

                Log.d(TAG, "onResponse: 응답 받은 데이터 :"+phones);
            }

            @Override
            public void onFailure(Call<CMRespDto<List<Phone>>> call, Throwable t) {
                Log.d(TAG, "onFailure: findAll() 실패");
            }
        });
    }

    public void addContact() {
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_add_concat, null);

        final EditText etName = dialogView.findViewById(R.id.etname);
        final EditText etTel = dialogView.findViewById(R.id.ettel);

        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("연락처 등록");
        dlg.setView(dialogView);
        dlg.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PhoneSaveReqDto phoneSaveReqDto = new PhoneSaveReqDto();
                phoneSaveReqDto.setName(etName.getText().toString());
                phoneSaveReqDto.setTel(etTel.getText().toString());

                Log.d(TAG, "onClick: 등록 클릭시 값 확인" + phoneSaveReqDto);

                PhoneService phoneService = PhoneService.retrofit.create(PhoneService.class);
                Call<PhoneSaveReqDto> call = phoneService.save(phoneSaveReqDto);

                call.enqueue(new Callback<PhoneSaveReqDto>() {
                    @Override
                    public void onResponse(Call<PhoneSaveReqDto> call, Response<PhoneSaveReqDto> response) {

                        Log.d(TAG, "onResponse: call 은" + call);
                        Log.d(TAG, "onResponse: response은" + response);
                        Log.d(TAG, "onResponse: phoneSaveReqDto" + phoneSaveReqDto);

                        Phone phone = new Phone();
                        phone.setName(phoneSaveReqDto.getName());
                        phone.setTel(phoneSaveReqDto.getTel());

                        phoneAdapter.addItem(phone);
                    }

                    @Override
                    public void onFailure(Call<PhoneSaveReqDto> call, Throwable t) {

                    }
                });

                //createContact(etName.getText().toString(), etEmail.getText().toString());
            }
        });
        dlg.setNegativeButton("닫기", null);
        dlg.show();

    }
}