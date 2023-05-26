package com.mobile.test.ui.activities

import android.os.Bundle
import com.mobile.test.R
import com.mobile.test.base.BaseActivity

class AuthActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.auth_activity)
    }
}