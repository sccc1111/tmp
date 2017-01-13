package tmp.com.cn.tmp;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Uri uri;
    private TextView tv_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView) findViewById(R.id.tv_show);
        //1注册一个内容观察者
        uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, new SmsObserver(new Handler()));
    }

    public class SmsObserver extends ContentObserver {

        public SmsObserver(Handler handler) {
            super(handler);
        }
        @Override
        public void onChange(boolean selfChange) {

            //当短信的数据库发生了变化    我就去取出所有短信的内容
            Cursor cursor = getContentResolver().query(uri, new String[]{"address","body","date"}, null, null, null);
            String str="";
            while(cursor.moveToNext()){

                String address = cursor.getString(0);
                String body = cursor.getString(1);
                String date = cursor.getString(2);

                str = str+"address---"+address+"--body:"+body+"\n";
            }
            tv_show.setText(str);
            super.onChange(selfChange);
        }

    }
}
