package tmp.com.cn.tmp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by songbo on 2017-01-12.
 */
public class PersonDataHelper extends SQLiteOpenHelper {
    private static final int VSERSION = 1;
    public PersonDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, VSERSION);
    }

    public PersonDataHelper(Context context, String name, int version) {
        super(context, name, null, VSERSION);
    }
    public PersonDataHelper(Context context,String name){
        this(context,name,VSERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
