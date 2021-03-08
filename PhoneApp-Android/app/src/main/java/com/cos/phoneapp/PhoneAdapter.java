package com.cos.phoneapp;

// 어댑터랑 RecycleView와 연결 (데이터바인딩 사용금지)

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 3번 상속받기 -> implements 생성
public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {
    private static final String TAG = "UserAdapter";

    private PhoneAdapter phoneAdapter;
    private MainActivity mainActivity;

    // 4번 컬렉션 생성
    private final List<Phone> phones;

    public PhoneAdapter(List<Phone> phones) {
        this.phones = phones;
    }

    // 5번 addItem, removeItem
    public void addItem(Phone phone){
        phones.add(phone);
        notifyDataSetChanged(); // 새로고침
    }

    public void removeItem(int position){
        phones.remove(position);
        notifyDataSetChanged();
    }

    public void updateItem(String name, String tel, int position){
        Phone phone = phones.get(position);
        phone.setName(name);
        phone.setTel(tel);
        notifyDataSetChanged();

    }

    // 7번 getView랑 똑같음
    // 차이점이 있다면 listView는 화면에 3개가 필요 최초 로딩시에 3개를 그려야하니까 getView가 3번 호출됨
    // 그 다음에 스크롤을 해서 2개가 추가되야 될때, 다시 getView를 호출함.
    // 하지만 recyclerView는 스크롤을 해서 2개가 추가되어야 할 때 onBindViewHolder를 호출함
    // onCreateViewHolder는 해당 activity 실행시에만 호출 됨 / 최초 로딩시에만 호출되고 더이상 호출안됨
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 메인 엑티비티에 연결할 객체를 만듬
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); // 고정이다.
        View view = inflater.inflate(R.layout.phone_item, parent,false); // 객체만 만들어져있음 뿌려져있지않음

        return new MyViewHolder(view); // view가 리스트뷰에 하나 그려짐
    }

    @Override // 최초 로딩끝나고 그 뒤부터는 얘가 호출됨, 데이터 추가
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Phone phone = phones.get(position);
        holder.setItem(phones.get(position));

        holder.itemView.setOnClickListener(v -> {
            View dialogView = v.inflate(v.getContext(), R.layout.layout_add_concat,null);

            final EditText etName = dialogView.findViewById(R.id.etname);
            final EditText etTel = dialogView.findViewById(R.id.ettel);

            etName.setText(phone.getName());
            etTel.setText(phone.getTel());

            AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
            //dlg.setCancelable(false);
            dlg.setTitle("연락처 수정");
            dlg.setView(dialogView);
            dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PhoneSaveReqDto phoneSaveReqDto = new PhoneSaveReqDto();
                    phoneSaveReqDto.setName(etName.getText().toString());
                    phoneSaveReqDto.setTel(etTel.getText().toString());
                    Log.d(TAG, "onClick: etname + ettel : " + etName + etTel);

                    PhoneService phoneService = PhoneService.retrofit.create(PhoneService.class);
                    Call<PhoneSaveReqDto> call = phoneService.update(phone.getId(),phoneSaveReqDto);

                    call.enqueue(new Callback<PhoneSaveReqDto>() {
                        @Override
                        public void onResponse(Call<PhoneSaveReqDto> call, Response<PhoneSaveReqDto> response) {
                            updateItem(phoneSaveReqDto.getName(),phoneSaveReqDto.getTel(),position);
                        }

                        @Override
                        public void onFailure(Call<PhoneSaveReqDto> call, Throwable t) {

                        }
                    });
                }
            });
            dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    PhoneService phoneService = PhoneService.retrofit.create(PhoneService.class);
                    Call<CMRespDto> call = phoneService.deleteById(phone.getId());

                    call.enqueue(new Callback<CMRespDto>() {
                        @Override
                        public void onResponse(Call<CMRespDto> call, Response<CMRespDto> response) {
                            removeItem(position);
                            CMRespDto result = response.body();

                        }

                        @Override
                        public void onFailure(Call<CMRespDto> call, Throwable t) {

                        }
                    });
                }
            });
            dlg.show();

        });
    }

    // 6번 컬렉션 크기 알려주기 (화면에 몇개 그려야할지를 알아야 하기 때문)
    @Override
    public int getItemCount() {
        return phones.size();
    }

    // 1번 ViewHolder 만들기
    // ViewHolder란 하나의 View를 가지고 있는 Holder이다.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // 2번 user_item이 가지고 있는 위젯들을 선언
        private TextView tvname;
        private TextView tvtel;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.name);
            tvtel = itemView.findViewById(R.id.tel);

        }

        public void setItem(Phone phone){
            tvname.setText(phone.getName());
            tvtel.setText(phone.getTel());
        }
    }
}
