package moe.gkd.tvproxy

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.gkd.tvproxy.adb.AdbClient
import moe.gkd.tvproxy.adb.AdbKey
import moe.gkd.tvproxy.adb.PreferenceAdbKeyStore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        connectAdb()
    }

    val key: AdbKey
        get() {
            val key = try {
                AdbKey(PreferenceAdbKeyStore(ShizukuSettings.getPreferences()), "moe.gkd.tvproxy")
            } catch (e: Throwable) {
                e.printStackTrace()
                throw RuntimeException("Failed to load key")
            }
            return key
        }

    private fun showToast(str: String) = lifecycleScope.launch(Dispatchers.Main) {
        if (str.isBlank()) return@launch
        Toast.makeText(this@MainActivity, str, Toast.LENGTH_SHORT).show()
    }


    fun connectAdb() = lifecycleScope.launch(Dispatchers.IO) {
        val command = "settings put global http_proxy ${PROXY_HOST}:${PROXY_PORT}"
        AdbClient(LOCAL_HOST, LOCAL_HOST_PORT, key).runCatching {
            connect()
            shellCommand(command) {
                val str = it.decodeToString()
                Log.d("shellCommand", str)
                showToast("设置成功")
            }
            close()
            System.exit(0)
        }.onFailure {
            it.printStackTrace()
        }
    }

}