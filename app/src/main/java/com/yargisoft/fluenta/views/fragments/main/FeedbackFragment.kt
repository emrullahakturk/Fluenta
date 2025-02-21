package com.yargisoft.fluenta.views.fragments.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.yargisoft.fluenta.databinding.FragmentFeedbackBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        // Firebase'den kullanıcı bilgilerini al
        val user = auth.currentUser
        val userEmail = user?.email ?: "E-posta bulunamadı"
        val userName = user?.displayName ?: "Anonim Kullanıcı"

        binding.btnSendFeedback.setOnClickListener {
            sendFeedbackEmail(userName, userEmail)
        }
    }

    private fun sendFeedbackEmail(userName: String, userEmail: String) {
        val subject = binding.etSubject.text.toString().trim()
        val message = binding.etMessage.text.toString().trim()

        if (subject.isEmpty() || message.isEmpty()) {
            Toast.makeText(requireContext(), "Lütfen konu ve mesaj alanlarını doldurun.", Toast.LENGTH_SHORT).show()
            return
        }

        val recipient = "support@fluentaapp.com" // Geri bildirim e-posta adresi

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, "Fluenta Geri Bildirim - $subject")
            putExtra(Intent.EXTRA_TEXT, """
                Kullanıcı Adı: $userName
                Kullanıcı E-Postası: $userEmail
                
                Mesaj:
                $message
            """.trimIndent())
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "E-posta gönder"))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "E-posta gönderimi desteklenmiyor.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
