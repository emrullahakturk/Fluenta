package com.yargisoft.fluenta.views.fragments.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment  @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Splash ekranı 2 saniye gösterelim (isteğe bağlı)
        Handler(Looper.getMainLooper()).postDelayed({
            checkUserSession()
        }, 2000)
    }

    private fun checkUserSession() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // Kullanıcı oturum açmış, Home sayfasına yönlendir
            findNavController().navigate(R.id.splashToQuote)
        } else {
            // Kullanıcı oturum açmamış, FirstPage'e yönlendir
            findNavController().navigate(R.id.splashToWelcome)
        }
    }
}



