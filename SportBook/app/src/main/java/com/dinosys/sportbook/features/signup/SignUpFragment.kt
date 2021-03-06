package com.dinosys.sportbook.features.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dinosys.sportbook.R
import com.dinosys.sportbook.application.SportbookApp
import com.dinosys.sportbook.extensions.appContext
import com.dinosys.sportbook.features.BaseFragment
import com.dinosys.sportbook.networks.models.AuthModel
import com.dinosys.sportbook.utils.ToastUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_up.*
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by hanth on 31/05/2017.
 */

class SignUpFragment : BaseFragment() {

    override fun inflateFromLayout() = R.layout.fragment_sign_up

    @Inject
    lateinit var signUpApi: SignUpViewModel

    private var mCallbackManager: CallbackManager? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SportbookApp.authComponent.inject(this)
        initFacebookLoginConfig()
        initListeners()
    }


    override fun initListeners() {
        super.initListeners()
        val btnSignUpDisposable = RxView.clicks(btnSignUp)
                .subscribeOn(AndroidSchedulers.mainThread())
                .switchMap {
                    val name = etName.text.toString()
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()
                    val confirmpassword = etConfirmPassword.text.toString()
                    signUpApi.signUp(context,name,email,password,confirmpassword)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorResumeNext {
                                t:Throwable? -> onSignUpErrorResponse(t?.message)
                            }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response -> onSignUpDataResponse(response = response)})
        addDisposable(btnSignUpDisposable)
    }

    private fun onSignUpErrorResponse(textError : String?) : ObservableSource<Response<AuthModel>>? {
        ToastUtil.show(appContext, textError)
        return Observable.empty()
    }

    private fun onSignUpDataResponse(response: Response<AuthModel>) {
        val statusCode = response.code()
        when (statusCode) {
            in 200..300 -> {
                val signIn = response.body()
                signIn?.header = response.headers()
            }
            else -> onSignUpErrorResponse(getString(R.string.error_login_failure_text))
        }
    }

    private fun initFacebookLoginConfig() {
        btnFacebookLogin!!.setReadPermissions("email")
        btnFacebookLogin!!.fragment = this
        mCallbackManager = CallbackManager.Factory.create()
        btnFacebookLogin!!.registerCallback(mCallbackManager, createFacebookcallback())
    }

    private fun createFacebookcallback(): FacebookCallback<LoginResult> {
        return object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

            }

            override fun onCancel() {
                Log.v(TAG, "[FacebookCallback][onCancel]")
            }

            override fun onError(exception: FacebookException) {
                Log.e(TAG, "[FacebookCallback][onError]:" + exception.message)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mCallbackManager != null) {
            mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        val TAG = "SignUpFragment"
    }



}

