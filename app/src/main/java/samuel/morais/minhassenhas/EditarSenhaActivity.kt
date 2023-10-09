package samuel.morais.minhassenhas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
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
    private lateinit var descricao: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editarsenha)

        descricao = findViewById(R.id.etDescricao)
        alterarSenha = findViewById(R.id.btAlterar)
        apagarSenha = findViewById(R.id.btApagar)
        cancelar = findViewById(R.id.btCancelar)
        maiscula = findViewById(R.id.cbMaiusculo)
        especial = findViewById(R.id.cbEspecial)
        numeros = findViewById(R.id.cbNumeros)
        seekBar = findViewById(R.id.seekBar)
        atualNumero = findViewById(R.id.tvTamanhoAtual)

        val senha = intent.getParcelableExtra<Password>("senha")
        //Deixar a tela de acordo com as configurações da senhas
        if (senha != null) {
            val descricaoEditable = Editable.Factory.getInstance().newEditable(senha.descricao)
            descricao.text = descricaoEditable
            seekBar.progress = senha.tamanho
            if (senha.maiusculo)
                maiscula.isChecked = true
            if (senha.numero)
                numeros.isChecked = true
            if (senha.especial)
                especial.isChecked = true



            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    atualNumero.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })

            alterarSenha.setOnClickListener() {
                alteraSenha(senha)
            }
            apagarSenha.setOnClickListener() {
                apagarSenha(senha)
            }
            cancelar.setOnClickListener() {
                cancelar()
            }
        } else {
            //da erro
        }
    }

    fun alteraSenha(senha: Password){

        val senhaAlterada = editarSenha(senha)

        val intent = Intent()
        intent.putExtra("senhaAlterada", senhaAlterada)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    fun apagarSenha(senha: Password){
        val intent = Intent()
        intent.putExtra("senhaApagada", senha)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
    fun cancelar(){
        finish()
    }

    fun editarSenha(novaSenha: Password): Password {
        novaSenha.maiusculo = maiscula.isChecked
        novaSenha.numero = numeros.isChecked
        novaSenha.especial = especial.isChecked
        novaSenha.gerarSenha(seekBar.progress)
        novaSenha.descricao = descricao.text.toString()
        novaSenha.tamanho = seekBar.progress
        return novaSenha
    }

}