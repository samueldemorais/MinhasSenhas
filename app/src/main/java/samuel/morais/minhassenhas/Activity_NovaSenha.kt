package samuel.morais.minhassenhas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

class Activity_NovaSenha : AppCompatActivity() {
    private lateinit var atualNumero: TextView
    private lateinit var gerar: Button
    private lateinit var cancelar: Button
    private lateinit var cbMaiusculo: CheckBox
    private lateinit var cbNumero: CheckBox
    private lateinit var cbEspecial: CheckBox
    private lateinit var descricao: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var senhasDAO: SenhasDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novasenha)

        this.atualNumero = findViewById(R.id.tvTamanhoAtual)
        this.gerar = findViewById(R.id.btGerar)
        this.cancelar = findViewById(R.id.btCancelar)
        this.cbMaiusculo = findViewById(R.id.cbMaiusculo)
        this.cbNumero = findViewById(R.id.cbNumeros)
        this.cbEspecial = findViewById(R.id.cbEspecial)
        this.descricao = findViewById(R.id.etDescricao)
        this.seekBar = findViewById(R.id.seekBar)


        this.senhasDAO = SenhasDAO(this)

        val novaSenha = Password("", "")

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                atualNumero.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        this.gerar.setOnClickListener {
            Log.d("NovaSenhaKT", "passei no GERAR do nova senha")
            val inputText = descricao.text.toString()
            if (inputText.isNotEmpty()) {
                // Descricao
                novaSenha.descricao = inputText
                novaSenha.gerarSenha(seekBar.progress, cbMaiusculo.isChecked, cbNumero.isChecked, cbEspecial.isChecked)
                Log.d("NovaSenhaKT", "GEREI do nova senha")
                senhasDAO.insert(novaSenha)
                Log.d("NovaSenhaKT", "INSERI do nova senha")
                finish()
            }
        }

        this.cancelar.setOnClickListener {
            Log.d("NovaSenhaKT", "passei no cancelar do nova senha")
            finish()
        }
    }
}

