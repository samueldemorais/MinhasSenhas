package samuel.morais.minhassenhas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class EditarSenhaActivity : AppCompatActivity() {
    private lateinit var alterarSenha: Button
    private lateinit var apagarSenha: Button
    private lateinit var cancelar: Button
    private lateinit var maiscula: CheckBox
    private lateinit var especial: CheckBox
    private lateinit var numeros: CheckBox
    private lateinit var seekBar: SeekBar
    private lateinit var atualNumero: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editarsenha)

        alterarSenha = findViewById(R.id.btAlterar)
        apagarSenha = findViewById(R.id.btApagar)
        cancelar = findViewById(R.id.btCancelar)
        maiscula = findViewById(R.id.cbMaiusculo)
        especial = findViewById(R.id.cbEspecial)
        numeros = findViewById(R.id.cbNumeros)
        seekBar = findViewById(R.id.seekBar)
        atualNumero = findViewById(R.id.tvTamanhoAtual)


        //Seekbar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                atualNumero.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        alterarSenha.setOnClickListener(){
            alteraSenha()
        }
        apagarSenha.setOnClickListener(){
            apagarSenha()
        }
        cancelar.setOnClickListener(){
            cancelar()
        }
    }

    fun alteraSenha(){
        val senhaAlterada = editarSenha()
        val intent = Intent()
        intent.putExtra("senhaAlterada", senhaAlterada)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    fun apagarSenha(){
        val intent = Intent()
        intent.putExtra("senhaApagada", true)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    fun cancelar(){
        finish()
    }

    fun editarSenha(): String {
        val novaSenha = Password()
        novaSenha.maiusculo = maiscula.isChecked
        novaSenha.numero = numeros.isChecked
        novaSenha.especial = especial.isChecked
        val updateSenha = novaSenha.gerarSenha(seekBar.progress)
        return updateSenha
    }

}