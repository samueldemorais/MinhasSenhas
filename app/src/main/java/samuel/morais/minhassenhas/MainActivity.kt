package samuel.morais.minhassenhas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var addButton: FloatingActionButton
    private lateinit var descricao: String
    private lateinit var tamanho: String
    private lateinit var senha: String
    private val senhaList: MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        addButton = findViewById(R.id.ButtonAdd)

        val adapter = ArrayAdapter<String>(this, R.layout.list_item, senhaList)
        listView.adapter = adapter

        val resultForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val arraySenha = it.data?.getStringArrayListExtra("senhaNova")
                if (arraySenha != null) {
                    descricao = arraySenha[0]
                    tamanho = arraySenha[1]
                    senha = arraySenha[2]
                }
                val senhaEditada = it.data?.getStringArrayListExtra("senhaEditada")
                if (senhaEditada != null){
                    descricao = senhaEditada[0]
                    tamanho = senhaEditada[1]
                    senha = senhaEditada[2]
                }

            }

        }
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Activity_NovaSenha::class.java)
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