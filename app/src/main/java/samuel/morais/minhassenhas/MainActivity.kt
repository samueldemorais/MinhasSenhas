package samuel.morais.minhassenhas

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton



class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var addButton: FloatingActionButton
    private val senhaList: MutableList<Password> = ArrayList()
    private var indiceSenha = 0
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
                        if (senhaNova != null) {
                            senhaList.add(senhaNova)
                            adapter.notifyDataSetChanged()
                        }

                        val senhaEditada: Password? = data?.getParcelableExtra("senhaAlterada")
                        if (senhaEditada != null) {
                            senhaList[indiceSenha] = senhaEditada
                            adapter.notifyDataSetChanged()
                        }
                        val senhaApagada: Password? = data?.getParcelableExtra("senhaApagada")
                        if (senhaApagada != null){
                            senhaList.remove(senhaList[indiceSenha])
                            adapter.notifyDataSetChanged()
                        }

                }}
                addButton.setOnClickListener {
                    val intent = Intent(this@MainActivity, Activity_NovaSenha::class.java)
                    resultForm.launch(intent)
                }
                listView.setOnItemLongClickListener { _, _, position, _ ->
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip: ClipData = ClipData.newPlainText("simple text", senhaList[position].senha)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(this@MainActivity, "Senha copiada para a área de transferência", Toast.LENGTH_SHORT).show()
                    true
                }


                listView.setOnItemClickListener { _, _, position, _ ->
                    val senhaSelecionada = senhaList[position]
                    indiceSenha = position
                    val intent = Intent(this@MainActivity, EditarSenhaActivity::class.java)
                    intent.putExtra("senha", senhaSelecionada)
                    resultForm.launch(intent)
                }


            }
    }
