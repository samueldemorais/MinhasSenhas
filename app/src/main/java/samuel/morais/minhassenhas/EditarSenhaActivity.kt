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
import android.widget.Toast
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
    private lateinit var senhasDAO: SenhasDAO
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
        this.senhasDAO = SenhasDAO(this)

        val senhaId = intent.getIntExtra("senhaId", -1)
        val senha: Password? = if (senhaId != -1) senhasDAO.find(senhaId) else null


        //Deixar a tela de acordo com as configurações da senhas
        if (senha != null) {
            val descricaoEditable = Editable.Factory.getInstance().newEditable(senha.descricao)
            descricao.text = descricaoEditable
            seekBar.progress = senha.verificarTamanho()
            if (senha.verificarMaiusculo())
                maiscula.isChecked = true
            if (senha.verificarNumero())
                numeros.isChecked = true
            if (senha.verificarEspecial())
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

        val senhaAlterada = criarSenhaEditada(senha)
        senhasDAO.update(senhaAlterada)
        exibirMensagem("Senha alterada com sucesso")
        setResult(Activity.RESULT_OK)
        finish()
    }
    fun apagarSenha(senha: Password){
        senhasDAO.delete(senha.id)
        exibirMensagem("Senha apagada com sucesso")
        setResult(Activity.RESULT_OK)
        finish()
    }
    fun cancelar(){
        finish()
    }

    fun criarSenhaEditada(senha: Password): Password {
        val descricaoEditada = descricao.text.toString()
        val tamanhoSenha = seekBar.progress
        val maiusculo = maiscula.isChecked
        val numero = numeros.isChecked
        val especial = especial.isChecked


        if (descricaoEditada.isEmpty()) {
            throw IllegalArgumentException("Descrição não pode ser vazia")
        }


        return Password(
            senha.id,
            descricaoEditada,
            senha.gerarSenha(tamanhoSenha, maiusculo, numero, especial),
            System.currentTimeMillis(),
            System.currentTimeMillis()
        )
    }

    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }



}