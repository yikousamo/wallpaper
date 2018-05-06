package com.dongyk.wallpaper.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author dongyk
 * @date 2018/5/3
 * @discription
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
    }

    abstract fun getLayoutResId(): Int

}