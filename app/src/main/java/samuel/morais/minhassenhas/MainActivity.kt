package samuel.morais.minhassenhas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Objects


class MainActivity : AppCompatActivity() {
    private lateinit var addButton: FloatingActionButton
    private lateinit var descricao: String
    private lateinit var tamanho: String
    private lateinit var senha: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.addButton = this.findViewById(R.id.ButtonAdd)

        val resultForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val senhaNova: Password? = intent.getParcelableExtra("senhaNova")
                if (senhaNova != null) {

                }
                val senhaEditada: Password? = intent.getParcelableExtra("senhaEditada")
                if (senhaEditada != null){

                }

            }

        }
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Activity_NovaSenha::class.java)
            resultForm.launch(intent)
        }

        cardSenha.setOnClickListener{
            val intent = Intent(this@MainActivity, EditarSenhaActivity::class.java)
            resultForm.launch(intent)
        }

        cardSenha.setOnLongClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", senha)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(applicationContext,"Senha copiada para a área de transferência",Toast.LENGTH_SHORT).show()
        }
    }
}