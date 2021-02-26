package com.chama.googleplacestest.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {
    protected fun setupToolbar(toolbar: Toolbar, title: String, showBackButton: Boolean = false) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        if (showBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}