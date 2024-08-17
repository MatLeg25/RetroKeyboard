package com.example.retrokeyboard.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.retrokeyboard.R

fun Context.copyTextToClipboard(text: String) {
    val clipboard = getSystemService(this, ClipboardManager::class.java) as ClipboardManager
    val clip = ClipData.newPlainText("RetroKeyboardApp", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(this, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
}