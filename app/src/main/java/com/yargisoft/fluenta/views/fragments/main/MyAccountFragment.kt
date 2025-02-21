package com.yargisoft.fluenta.views.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.yargisoft.fluenta.R
import com.yargisoft.fluenta.databinding.FragmentMyAccountBinding
import com.yargisoft.fluenta.views.activities.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyAccountFragment @Inject constructor() : Fragment() {


    private val navOptions = NavOptions.Builder()
        .setPopUpTo(R.id.auth_nav, true) // Main Graph'taki tüm fragmentleri temizler
        .build()



    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val user = auth.currentUser
        if (user != null) {
            binding.tvName.text = user.displayName ?: "Ad Soyad Belirtilmemiş"
            binding.tvEmail.text = user.email
            binding.tvMembership.text = "Üyelik Türü: Ücretsiz"
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
           // findNavController().navigate(R.id.auth_nav, null, navOptions) // Auth Graph'a yönlendir
        }

        binding.btnDeleteAccount.setOnClickListener {
            showDeleteAccountDialog()
        }
    }

    private fun showDeleteAccountDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Hesabı Sil")
            .setMessage("Hesabınızı kalıcı olarak silmek istediğinize emin misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                auth.currentUser?.delete()
                findNavController().navigate(R.id.accountToAuthNav, null, navOptions)
            }
            .setNegativeButton("İptal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
