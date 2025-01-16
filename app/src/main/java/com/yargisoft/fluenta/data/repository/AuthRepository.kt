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

            Result.success("Kayıt başarılı!")
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception(mapFirebaseError(e)))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<String> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success("Giriş başarılı!")
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception(mapFirebaseError(e)))
        } catch (e: Exception) {
            Result.failure(e)
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