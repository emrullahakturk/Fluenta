package com.yargisoft.fluenta.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun registerUser(email: String, password: String, fullName: String): Result<String> {
        return try {
            // Kullanıcı oluştur
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            // Ad soyadı Firebase'de güncelle
            user?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()
            )?.await()

            // E-posta doğrulama gönder
            user?.sendEmailVerification()?.await()

            Result.success("Kayıt başarılı! E-posta adresinizi doğrulamak için lütfen gelen kutunuzu kontrol edin.")
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception(mapFirebaseError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null && !user.isEmailVerified) {
                resendVerificationEmail()
                Result.failure(Exception("E-posta adresiniz doğrulanmamış. Lütfen doğrulama işlemini tamamlayın."))
            } else {
                Result.success("Giriş başarılı!")
            }
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception(mapFirebaseError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun resendVerificationEmail(): Result<String> {
        return try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                user.sendEmailVerification().await()
                Result.success("Doğrulama e-postası başarıyla gönderildi.")
            } else {
                Result.failure(Exception("Kullanıcı oturumu yok."))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Doğrulama e-postası gönderilemedi: ${e.message}"))
        }
    }

    suspend fun resetPassword(email: String): Result<String> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success("Şifre sıfırlama bağlantısı e-posta adresinize gönderildi.")
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception(mapFirebaseError(e)))
        } catch (e: Exception) {
            Result.failure(Exception("Şifre sıfırlama bağlantısı gönderilemedi: ${e.message}"))
        }
    }


    private fun mapFirebaseError(exception: FirebaseAuthException): String {
        return when (exception.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> "Bu e-posta zaten kullanılıyor."
            "ERROR_WEAK_PASSWORD" -> "Şifre çok zayıf. Lütfen daha güçlü bir şifre kullanın."
            "ERROR_INVALID_EMAIL" -> "Geçersiz bir e-posta adresi girdiniz."
            "ERROR_NETWORK_REQUEST_FAILED" -> "İnternet bağlantınızı kontrol edin."
            "ERROR_USER_NOT_FOUND" -> "Bu e-posta ile kayıtlı bir kullanıcı bulunamadı."
            "ERROR_WRONG_PASSWORD" -> "Hatalı şifre girdiniz. Lütfen tekrar deneyin."
            "ERROR_USER_DISABLED" -> "Hesabınız devre dışı bırakılmış. Destek ekibiyle iletişime geçin."
            else -> "Bir hata oluştu: E-postanızı ve şifrenizi tekrar kontrol ediniz."
        }
    }
}