package tv.banban.appsautotest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "xuzx";
    Button buttonStart, buttonStop;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        startService(new Intent(this, TestService.class));
//        stopService(new Intent(this, TestService.class));
//        buttonStart = (Button) findViewById(R.id.buttonStart);
//        buttonStop = (Button) findViewById(R.id.buttonStop);
//        buttonStart.setOnClickListener(this);
//        buttonStop.setOnClickListener(this);
//    }

    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart:
                Log.i(TAG, "onClick: starting service");
                startService(new Intent(this, TestService.class));
                break;
            case R.id.buttonStop:
                Log.i(TAG, "onClick: stopping service");
                stopService(new Intent(this, TestService.class));
                break;
        }
    }
    //1.12
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        Button bn = (Button) findViewById(R.id.buttonStart);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NbaHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
