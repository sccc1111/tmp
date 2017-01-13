package tmp.com.cn.tmp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by songbo on 2017-01-12.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取短信类容
        Object[] pduses= (Object[])intent.getExtras().get("pdus");
        for(Object pdus: pduses){

            byte[] pdusmessage = (byte[])pdus;

            SmsMessage sms = SmsMessage.createFromPdu(pdusmessage);

            String mobile = sms.getOriginatingAddress();//发送短信的手机号码

            String content = sms.getMessageBody(); //短信内容

            Date date = new Date(sms.getTimestampMillis());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String time = format.format(date);  //得到发送时间

            Log.i(TAG,"mobile---"+mobile+"---content"+content);
        }

    }
}
