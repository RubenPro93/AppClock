package com.example.apprelogio

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.apprelogio.databinding.ActivityFullscreenBinding
import java.util.Objects


class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private var isCheked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Comentário: Verificamos se o dispositivo está usando a versão Android R ou superior.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Comentário: Se for Android R ou superior, escondemos a barra de status.
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            // Comentário: Se for uma versão anterior ao Android R, adicionamos uma bandeira para exibir em tela cheia.
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        // Comentário: Queremos manter a tela sempre ligada.
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Comentário: Vamos criar um receptor para obter informações sobre a bateria.
        val bateriaReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                // Comentário: Quando recebemos informações sobre a bateria...
                if (p1 != null) {
                    // Comentário: Obtemos o nível da bateria.
                    val nivel: Int = p1.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    // Comentário: Mostramos o nível da bateria em um pequeno aviso na tela.
                    //Toast.makeText(applicationContext, nivel.toString(), Toast.LENGTH_SHORT).show()
                    binding.textNivelBateria.text = "${nivel.toString()}%"
                }
            }
        }

        // Comentário: Registramos o receptor para receber informações sobre a bateria.
        registerReceiver(bateriaReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))


        // Código para quando o botão de verificar o nível da bateria é clicado
        binding.checkNivelBateria.setOnClickListener {
            if (isCheked) {
                // Comentário: Se estiver marcado (checked), esconde o texto que mostra o nível da bateria.
                isCheked = false
                binding.textNivelBateria.visibility = View.GONE
            } else {
                // Comentário: Se não estiver marcado, mostra o texto que mostra o nível da bateria.
                isCheked = true
                binding.textNivelBateria.visibility = View.VISIBLE
            }
            // Comentário: Atualiza a marcação do botão de verificar o nível da bateria.
            binding.checkNivelBateria.isChecked = isCheked
        }

        // Comentário: Faz uma animação para deslizar o menu para baixo (500 pixels).
        binding.layoutMenu.animate().translationY(500F)

        // Código para quando a imagem de preferências é clicada
        binding.imageViewPreferencias.setOnClickListener {
            // Comentário: Mostra o menu e faz uma animação para deslizá-lo para cima.
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(0F).setDuration(
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            )
        }

        // Código para quando a imagem de fechar é clicada
        binding.imageViewFechar.setOnClickListener {
            // Comentário: Faz uma animação para deslizar o menu para cima (esconde o menu).
            binding.layoutMenu.animate().translationY(binding.layoutMenu.measuredHeight.toFloat())
                .setDuration(
                    resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                )
        }

    }


}