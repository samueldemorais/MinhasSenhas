package samuel.morais.minhassenhas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
                val arraySenha = it.data?.getStringArrayListExtra("senhaNova")
                if (arraySenha != null) {
                    descricao = arraySenha[0]
                    tamanho = arraySenha[1]
                    senha = arraySenha[2]
                }
                val senhaEditada = it.data?.getStringExtra("senhaEditada")
                if (senhaEditada != null){
                    //Faz alguma coisa
                }

            }

        }
        addButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Activity_NovaSenha::class.java)
            resultForm.launch(intent)
        }

            //Substituir pelo listview
//        cardSenha.setOnClickListener{
//            val intent = Intent(this@MainActivity, Activity_EditarSenha::class.java)
//            resultForm.launch(intent)
//        }
    }
}