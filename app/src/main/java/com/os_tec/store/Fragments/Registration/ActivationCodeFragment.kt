package com.os_tec.store.Fragments.Registration

import `in`.aabhasjindal.otptextview.OTPListener
import android.app.Service
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.os_tec.store.Activities.MainNavigation
import com.os_tec.store.Activities.Navigation3Activity
import com.os_tec.store.Api.ApiClient
import com.os_tec.store.Api.RetrofitApiInterface
import com.os_tec.store.Classes.CustomAlertDialog
import com.os_tec.store.Classes.SharedPreferences
import com.os_tec.store.Classes.Validation
import com.os_tec.store.Model.RegistrationResponse
import com.os_tec.store.R
import com.os_tec.store.databinding.FragmentActivationCodeBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActivationCodeFragment : Fragment() {
    lateinit var binding:FragmentActivationCodeBinding
    private val logKey="ActivationFragment.OS"
    private var code=""
    private var email=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentActivationCodeBinding.inflate(inflater,container,false)
        (activity as Navigation3Activity).supportActionBar!!.hide() //hide ActionBar
        val service=ApiClient.getRetrofitInstance()!!.create(RetrofitApiInterface::class.java)//Api
         email=requireArguments().getString("email").toString()//get Email
        val dialog=CustomAlertDialog(requireActivity())//Alert Dialog

        //show keyboard
        val otpView=binding.otpView
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        otpView.requestFocus()

        //get and set codeText
        otpView.otpListener = (object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                code=otp
                //Toast.makeText(requireContext(), otp, Toast.LENGTH_LONG).show()
            }
        })



        binding.btnVerify.setOnClickListener {
            if (Validation(requireActivity()).validateEditText(code)){//validate If code not null
                dialog.showDialog(resources.getString(R.string.Verifying))//show Dialog
                //set data
                val params = HashMap<String, Any>()
                params["email"] = email
                params["code"] = code

                service.activationCode(params).enqueue(object : Callback<RegistrationResponse> {
                    override fun onResponse(
                        call: Call<RegistrationResponse>,
                        response: Response<RegistrationResponse>
                    ) {
                        if (response.code() == 200) {

                            if (response.body() != null) {
                                dialog.dismissDialog()
                                //add user Data to SharedPreferences
                                SharedPreferences(requireContext()).addUserData(response.body()!!)
                                toastAndLog("ResponseCode-> 200", response.body()!!.message)
                                startActivity(Intent(requireContext(), MainNavigation::class.java))
                                requireActivity().finish()

                            } else {//null body
                                dialog.dismissDialog()
                                toastAndLog("Null Response", "Null Response")
                            }


                        } else {//!=200
                            dialog.dismissDialog()
                            val jsonObject= JSONObject(response.errorBody()!!.string())
                            val message=jsonObject.getString("message")
                            toastAndLog(jsonObject.toString(), message)
                        }

                    }

                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        dialog.dismissDialog()
                        toastAndLog("OnFailure :->${t.message}",resources.getString(R.string.ServerError))
                    }

                })

            }//endValidation

        }//endClick



        return binding.root
    }



    fun toastAndLog(log:String,toast:String){
        Toast.makeText(activity,toast, Toast.LENGTH_LONG).show()
        Log.e(logKey,log)

    }
}