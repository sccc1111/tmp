package tmp.com.cn.tmp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import tmp.com.cn.tmp.data.PersonDataHelper;

/**
 * Created by songbo on 2017-01-12.
 */
public class PersonContentProvider extends ContentProvider {

    private static final int QUEYSUCESS = 0;
    private static final int INSERTSUCESS = 1;
    private static final int UPDATESUCESS  = 2;
    private static final int DELSUCESS  = 3;
    PersonDataHelper personDataHelper = null;
    //1 想使用内容提供者 必须定义 匹配规则   code:定义的匹配规则 如果 匹配不上  有一个返回码  -1
    static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    //2 我要添加匹配规则
    static{
        //开始添加匹配规则
        /**
         * authority   主机名  通过主机名来访问我暴露的数据
         * path   你也可以随意 写 cn.itcast.providers.personprovider/query
         * code 匹配码
         */
        matcher.addURI("cn.itcast.providers.personprovider", "query", QUEYSUCESS);
        //添加插入匹配规则
        matcher.addURI("cn.itcast.providers.personprovider", "insert", INSERTSUCESS);
        //添加更新匹配规则
        matcher.addURI("cn.itcast.providers.personprovider", "update", UPDATESUCESS);
        //添加删除匹配规则
        matcher.addURI("cn.itcast.providers.personprovider", "delete", DELSUCESS);
    }

    @Override
    public boolean onCreate() {
        //创建数据库
        personDataHelper = new PersonDataHelper(getContext(),"person.db");
        //创建数据表
        String sql = "create table if not exists person(id integer primary key,name varchar,phone varchar,email varchar,notes varchar)";
        SQLiteDatabase db = personDataHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //传递过来的uri 是否和我们定义的匹配规则 匹配
        int match = matcher.match(uri);
        if (match == QUEYSUCESS ) {
            //说明匹配成功
            SQLiteDatabase db = personDataHelper.getReadableDatabase();  //获取数据库对象
            Cursor cursor = db.query("person", projection, selection, selectionArgs, null, null, sortOrder);

            //数据库发生了改变
            getContext().getContentResolver().notifyChange(uri, null);

            return cursor;

        }else{
            //匹配失败
            throw new IllegalArgumentException("路径匹配失败");

        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int match = matcher.match(uri);
        if (match == INSERTSUCESS) {
            //说明匹配成功  
            SQLiteDatabase db = personDataHelper.getReadableDatabase();
            long insert = db.insert("person", null, values);

            //执行上面这句话 说明我的数据库内容 发生了变化   首先 要发送一条通知 说明 我发生改变   

            if (insert>0) {

                //数据库发生变化  发送一个通知   
                getContext().getContentResolver().notifyChange(uri, null);
            }

            Uri uri2 = Uri.parse("cn.itcast.providers.personprovider/"+insert);
            return uri2;
        }else{
            //匹配失败   
            throw new IllegalArgumentException("路径匹配失败");

        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = matcher.match(uri);
        if (match == DELSUCESS) {
            //匹配成功  
            SQLiteDatabase db = personDataHelper.getReadableDatabase();
            int delete = db.delete("person", selection, selectionArgs);

            if (delete>0) {
                //数据库发生了改变
                getContext().getContentResolver().notifyChange(uri, null);

            }
            return delete;
        }else {

            //匹配失败   
            throw new IllegalArgumentException("路径匹配失败");

        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int match = matcher.match(uri);
        if (match == UPDATESUCESS) {
            //匹配成功   
            SQLiteDatabase db = personDataHelper.getReadableDatabase();
            int update = db.update("person", values, selection, selectionArgs);

            if (update>0) {
                // 数据库发生了改变
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return update;
        }else {
            //匹配失败   
            throw new IllegalArgumentException("路径匹配失败");
        }
    }
}
