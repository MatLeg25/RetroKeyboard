package com.example.retrokeyboard.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService

fun Context.copyTextToClipboard(text: String) {
    val clipboard = getSystemService(this, ClipboardManager::class.java) as ClipboardManager
    val clip = ClipData.newPlainText("RetroKeyboardApp", text)
    clipboard.setPrimaryClip(clip)
}