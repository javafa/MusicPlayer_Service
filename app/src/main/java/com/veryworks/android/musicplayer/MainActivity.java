package com.veryworks.android.musicplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.veryworks.android.musicplayer.App.PLAY;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 앱을 껏다가 플레이어가 실행중이면 일단 PlayerActivity로 이동한다
        if(App.playStatus == PLAY){
            Intent intent = new Intent(this,PlayerActivity.class);
            intent.putExtra("position", App.position);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.activity_main);
            // 버전체크해서 마시말로우 보다 낮으면 런타임권한 체크를 하지 않는다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermission();
            } else {
                init();
            }
        }
    }

    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M) // Target 지정 애너테이션
    private void checkPermission(){
        // 1.1 런타임 권한체크
        if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ){
            // 1.2 요청할 권한 목록 작성
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            // 1.3 시스템에 권한요청
            requestPermissions(permArr, REQ_CODE);
        }else{
            init();
        }
    }

    // 2. 권한체크 후 콜백 < 사용자가 확인후 시스템이 호출하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_CODE){
            // 2.1 배열에 넘긴 런타임권한을 체크해서 승인이 됬으면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                // 2.2 프로그램 실행
                init();
            }else{
                Message.show("권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.",this);
                finish();
            }
        }
    }

    // 데이터를 로드할 함수
    private void init(){
        Message.show("프로그램을 실행합니다",this);
        listInit();
    }

    private void listInit(){
        RecyclerView listView = (RecyclerView) findViewById(R.id.listView);
        MusicAdapter adapter = new MusicAdapter(this);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
    }
}
