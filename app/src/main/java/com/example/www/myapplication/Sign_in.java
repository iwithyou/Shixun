package com.example.www.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 贾紫璇 on 2016/11/16.
 */
public class Sign_in extends Activity {
    private EditText etusername;
    private EditText etpwd;
    private EditText etpwd2;
    private Button btnsign;
    private UserDataManager mUserDataManager;         //用户数据管理类

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //获取控件
        btnsign=(Button)findViewById(R.id.btnsign);
        etusername=(EditText) findViewById(R.id.etusername);
        etpwd=(EditText)findViewById(R.id.etpwd);
        etpwd2=(EditText)findViewById(R.id.etpwd2);
        //建立本地数据库
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager();
            mUserDataManager.openDataBase();
        }

        //绑定注册监听器
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //..
                register_check();


                Intent i = new Intent(Sign_in.this,success.class);
            }
        });

    }
    //确认按钮的监听事件
    public void register_check(){
        if(isUserNameAndPwdValid()){
            String username=etusername.getText().toString().trim();
            String pwd1=etpwd.getText().toString().trim();
            String pwd2=etpwd2.getText().toString().trim();
            //检查用户是否存在
            int count=mUserDataManager.findUserByName(userName);
            //用户已经存在时返回，给出提示文字
            if (count>0){
                Toast.makeText(this, getString(R.string.name_already_exist), username,Toast.LENGTH_SHORT).show();
                return;
            }
            //两次密码输入不一样
            if (pwd1.equals(pwd2)==false){
                Toast.makeText(this,getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();
                return;
            }else {
                UserData mUser= new UserData(username,pwd1);
                mUserDataManager.openDataBase();
                long flag = mUserDataManager.insertUserData(mUser);//新建用户信息
                if(flag == -1){
                    Toast.makeText(this,getString(R.string.register_fail),Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this,getString(R.string.register_success),Toast.LENGTH_SHORT).show();

                    Intent i =new Intent(Sign_in.this,Login.class);
                    startActivity(i);
                    finish();
                }
            }

        }

    }
    public boolean isUserNameAndPwdValid(){
        if (etusername.getText().toString().trim().equals("")){
            Toast.makeText(this,getString(R.string.etusername_empty),Toast.LENGTH_SHORT).show();
            return false;
        }else if(etpwd.getText().toString().trim().equals("")){
            Toast.makeText(this, getString(R.string.etpwd1_empty),Toast.LENGTH_SHORT).show();
            return false;
        }else if (etpwd2.getText().toString().trim().equals("")){
            Toast.makeText(this, getString(R.string.etpwd2_empty),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
