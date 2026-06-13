package com.github.nyxiereal.freenitroemojis

import android.content.Context
import java.net.URL

import com.revenge.api.plugin.Plugin
import com.revenge.api.patcher.Patcher

class FreeNitroEmojis : Plugin() {

    private val patcher = Patcher()

    override fun onStart(context: Context) {

        patcher.before("com.discord.models.message.Message", "constructor") { param ->

            var content = param.args[4] as? String ?: return@before

            content = Regex("""<(a)?:(F_)?([a-zA-Z0-9_]+):(\d+)>""").replace(content) {

                val animated = if (it.groupValues[1].isNotEmpty()) "a" else ""
                val name = it.groupValues[3]
                val id = it.groupValues[4]

                "<$animated:$name:$id>"
            }

            param.args[4] = content
        }
    }

    override fun onStop() {
        patcher.unpatchAll()
    }
}
