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
import com.os_tec.store.databinding.FragmentLoginBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val logKey="LoginFragment.OS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        (requireActivity() as Navigation3Activity).supportActionBar!!.show()


        val service=ApiClient.getRetrofitInstance()!!.create(RetrofitApiInterface::class.java)
        val validation=Validation(requireActivity())
        val alertDialog=CustomAlertDialog(requireActivity())

        //btn onClick
        binding.btnLogin.setOnClickListener {
            val email=binding.edEmail.text.toString()
            val password=binding.edPassword.text.toString()

            //validate text
            if (validation.validateEmail(email)&& validation.validateEditText(password)){

                alertDialog.showDialog(resources.getString(R.string.DialogLogin))//alert dialog

                //send login request
                val params=HashMap<String,Any>()
                params["email"] = email
                params["password"]=password


                service.login(params).enqueue(object :Callback<RegistrationResponse>{
                    override fun onResponse(
                        call: Call<RegistrationResponse>,
                        response: Response<RegistrationResponse>) {
                        //check response
                        if (response.code()==200){
                               //check user status ,Active or not
                                if (response.body()!!.status){
                                    //get data from response
                                    //add userData in SharedPreferences
                                    SharedPreferences(requireActivity()).addUserData(response.body()!!)
                                    toastAndLog(resources.getString(R.string.LoginSuccess),resources.getString(R.string.LoginSuccess))
                                    alertDialog.dismissDialog() //dismiss Dialog

                                    //start activity ->Home
                                    startActivity(Intent(requireContext(), MainNavigation::class.java))
                                    requireActivity().finish()
                                }//End status

                        }else{//!=200
                            alertDialog.dismissDialog()
                            val jsonObject=JSONObject(response.errorBody()!!.string())
                            val message=jsonObject.getString("message")

                            toastAndLog("Response: $response ::: ErrorMessage : $message",resources.getString(R.string.Message))

                        }

                    }

                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        alertDialog.dismissDialog()
                        toastAndLog("onFailure : ${t.message} " ,resources.getString(R.string.ServerError))
                    }

                })

            }

        }

        binding.btnSignUp.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container3, SignupFragment()).commit()
        }

        return binding.root
    }


fun toastAndLog(log:String,toast:String){
    Toast.makeText(activity,toast,Toast.LENGTH_LONG).show()
    Log.e(logKey,log)

}

}