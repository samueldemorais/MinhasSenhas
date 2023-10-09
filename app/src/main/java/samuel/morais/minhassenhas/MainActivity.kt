package samuel.morais.minhassenhas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Objects


class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var addButton: FloatingActionButton
    private lateinit var descricao: String
    private lateinit var tamanho: String
    private lateinit var senha: String
    private val senhaList: MutableList<Password> = ArrayList()
    var numSenhaSelecionada = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        addButton = findViewById(R.id.ButtonAdd)


        val adapter = SenhaAdapter(this, R.layout.list_item, senhaList)
        listView.adapter = adapter

        this.addButton = this.findViewById(R.id.ButtonAdd)


        val resultForm = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                    if (it.resultCode == RESULT_OK){
                        val data = it.data
                        val senhaNova: Password? = data?.getParcelableExtra("novaSenha")
                        Log.d("msg", "voltei de la")
                        if (senhaNova != null) {
                            val senhaTeste = senhaNova.toString()
                            Log.d("msg", senhaTeste)
                            senhaList.add(senhaNova)
                            adapter.notifyDataSetChanged()
                        }

                        val senhaEditada: Password? = data?.getParcelableExtra("senhaAlterada")
                        Log.d("EDITADA", senhaEditada.toString())
                        if (senhaEditada != null) {
                            senhaList[numSenhaSelecionada] = senhaEditada
                            adapter.notifyDataSetChanged()
                        }
                        val senhaApagada: Password? = data?.getParcelableExtra("senhaApagada")
                        Log.d("EDITADA", senhaApagada.toString())
                        if (senhaApagada != null){
                            Log.d("EDITADA", senhaList.toString())
                            senhaList.remove(senhaList[numSenhaSelecionada])
                            Log.d("EDITADA", senhaList.toString())
                            adapter.notifyDataSetChanged()
                        }

                }}
                addButton.setOnClickListener {
                    val intent = Intent(this@MainActivity, Activity_NovaSenha::class.java)
                    resultForm.launch(intent)
                }
                listView.setOnItemLongClickListener { _, _, position, _ ->
                    // Lógica para o clique longo
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("simple text", senhaList[position].senha)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this@MainActivity, "Senha copiada para a área de transferência", Toast.LENGTH_SHORT).show()
                    true
                }


                listView.setOnItemClickListener { _, _, position, _ ->
                    val senhaSelecionada = senhaList[position]
                    numSenhaSelecionada = position
                    Log.d("msg", "oi vim pra ca")
                    val intent = Intent(this@MainActivity, EditarSenhaActivity::class.java)
                    intent.putExtra("senha", senhaSelecionada)
                    resultForm.launch(intent)
                }


            }
    }
