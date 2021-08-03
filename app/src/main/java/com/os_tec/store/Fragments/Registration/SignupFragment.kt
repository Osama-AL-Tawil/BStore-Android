package com.os_tec.store.Fragments.Registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Activities.MainNavigation
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Classes.CustomAlertDialog
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.Classes.Validation
import com.os_tec.store.Model.RegistrationResponse
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentSignupBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment : Fragment() {
    private val logKey="SignupFragment.OS"
    lateinit var binding:FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentSignupBinding.inflate(inflater,container,false)

        val service= ApiClient.getRetrofitInstance()!!.create(RetrofitApiInterface::class.java)
        val validation= Validation(requireActivity())
        val alertDialog= CustomAlertDialog(requireActivity())

        //btn onClick
        binding.btnSignUp.setOnClickListener {
            val email=binding.edEmail.text.toString()
            val name=binding.edPassword.text.toString()
            val mobile=binding.edPhoneNumber.text.toString()
            val password=binding.edPassword.text.toString()

            //validate text
            if (validation.validateEmail(email)&& validation.validateEditText(name)
                &&validation.validateEditText(password)&& validation.validatePhone(mobile)){

                alertDialog.showDialog(resources.getString(R.string.DialogSignup))//alert dialog

                // val params:MutableMap<String,Any> =HashMap()
                val params:MutableMap<String,Any> =HashMap()
                params["name"]=name
                params["email"]=email
                params["mobile"]=mobile
                params["password"]=password

                //send login request
                service.signup(params).enqueue(object : Callback<RegistrationResponse> {
                    override fun onResponse(
                        call: Call<RegistrationResponse>,
                        response: Response<RegistrationResponse>
                    ) {
                        //check response
                        if (response.code()==200){
                            //check user status ,Active or not
                            if (response.body()!=null){
                                //dismiss Dialog
                                alertDialog.dismissDialog()
                                //Move and send email to activation code Fragment
                                (activity as Navigation3Activity).startActivationFragment(email)

                            }else{//null Body
                                alertDialog.dismissDialog()
                                toastAndLog("Null Response", "Null Response")
                            }

                        }else{//!=200
                            alertDialog.dismissDialog()
                            val jsonObject=JSONObject(response.errorBody()!!.string())
                            val message=jsonObject.getString("message")
                            toastAndLog(jsonObject.toString(), message)
                            toastAndLog(jsonObject.toString(), message)

                        }

                    }

                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        alertDialog.dismissDialog()
                        toastAndLog("OnFailure : ${t.message}",resources.getString(R.string.ServerError))
                    }

                })

            }

        }


        binding.btnLogin.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container3,
                LoginFragment()
            ).commit()
        }
        return binding.root
    }

    fun toastAndLog(log:String,toast:String){
        Toast.makeText(activity,toast, Toast.LENGTH_LONG).show()
        Log.e(logKey,log)

    }

    fun startActivationFragment(email:String){
        val bundle=Bundle()
        bundle.putString("email",email)
        val fragment=ActivationCodeFragment()
        fragment.arguments=bundle
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.container3,fragment).commit()
    }
}