package samuel.morais.minhassenhas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novasenha)

        this.atualNumero = this.findViewById(R.id.tvTamanhoAtual)
        this.gerar = this.findViewById(R.id.btGerar)
        this.cancelar = this.findViewById(R.id.btCancelar)
        this.cbMaiusculo = this.findViewById(R.id.cbMaiusculo)
        this.cbNumero = this.findViewById(R.id.cbNumeros)
        this.cbEspecial = this.findViewById(R.id.cbEspecial)
        this.descricao = this.findViewById(R.id.etDescricao)
        this.seekBar = this.findViewById(R.id.seekBar)

        val novaSenha = Password()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                atualNumero.text = progress.toString()
                novaSenha.tamanho = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        this.gerar.setOnClickListener {
            val inputText = descricao.text.toString()
            if (inputText.isNotEmpty()) {
                //Descricao
                novaSenha.descricao = inputText
                //CheckBox
                novaSenha.maiusculo = cbMaiusculo.isChecked
                novaSenha.numero = cbNumero.isChecked
                novaSenha.especial = cbEspecial.isChecked
                //GerarSenha
                novaSenha.gerarSenha(novaSenha.tamanho)


                val intent = Intent()
                intent.putExtra("novaSenha", novaSenha)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        this.cancelar.setOnClickListener(){
            finish()
        }
    }
}
