package com.team.cat_hackathon.presentation.fragmentEditProfile

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.cat_hackathon.data.models.User
import com.team.cat_hackathon.data.repositories.HomeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    val repository: HomeRepositoryImpl, val appContext: Application,
) : ViewModel() {


    private val _cachedUserLiveData = MutableLiveData<User>()
    val cachedUserLiveData: LiveData<User> get() = _cachedUserLiveData

    val imageChanged: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    private val _image = MutableLiveData<Uri?>(null)
    val image: LiveData<Uri?> = _image


    suspend fun updateUser(user: User) {
        val uploadedImage = uploadImage(image.value)
        uploadedImage?.let {
            user.imageUrl = it
        }
        repository.updateUser(user)
    }

    suspend fun getCachedUser(): User {
        val cachedUser = repository.getCachedUser()
        _cachedUserLiveData.postValue(cachedUser)
        return cachedUser
    }

    fun getUser(): User? {
        return cachedUserLiveData.value
    }

    fun isInputEqualToCachedUser(
        name: String,
        track: String,
        email: String,
        bio: String,
        github: String,
        linkedin: String,
        facebook: String
    ): Boolean {
        val cachedUser = cachedUserLiveData.value!!
        return cachedUser.name.trimEnd().trimStart() == name.trimEnd().trimStart() &&
                cachedUser.track?.trim() == track.trim() &&
                cachedUser.email?.trim() == email.trim() &&
                cachedUser.bio?.trim() == bio.trim() &&
                imageChanged.value!!&&
                cachedUser.githubUrl?.trim() == github.trim() &&
                cachedUser.linkedinUrl?.trim() == linkedin.trim() &&
                cachedUser.facebookUrl?.trim() == facebook.trim()
    }

    private suspend fun uploadImage(image: Uri?): String? {
        return if (image == null)
            null
        else {
            val imageStream = appContext.contentResolver.openInputStream(image)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            val baos = ByteArrayOutputStream()
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 80, baos)
            val data = baos.toByteArray()
            //todo : do it later
            val response = repository.uploadImage(data)
            null
        }
    }

    fun setImage(uri: Uri) {
        _image.value = uri
    }
}