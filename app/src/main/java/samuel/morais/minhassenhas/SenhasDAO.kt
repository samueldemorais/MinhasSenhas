package samuel.morais.minhassenhas

import android.content.ContentValues
import android.content.Context
import java.util.Calendar


class SenhasDAO{
    private val banco: BancoHelper
    constructor(context: Context) {
        this.banco = BancoHelper(context)
    }
    fun insert(senha: Password): Long {
        val dataHora = Calendar.getInstance().timeInMillis
        val cv = ContentValues()
        cv.put("descricao", senha.descricao)
        cv.put("senha", senha.senha)
        cv.put("data_cri", dataHora)
        cv.put("data_att", dataHora)
        return this.banco.writableDatabase.insert("senhas", null, cv)
    }

    fun select(): List<Password>{
        var lista = ArrayList<Password>()
        val colunas = arrayOf("id", "descricao", "senha", "data_cri", "data_att")
        val c = this.banco.readableDatabase.query("senhas", colunas, null, null, null,null, null)

        c.moveToFirst()
        for (i in 1..c.count){
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val senha = c.getString(2)
            val data_cri = c.getLong(3)
            val data_att = c.getLong(4)
            val senhaT = Password(id, descricao, senha, data_cri, data_att)
            lista.add(senhaT)
            c.moveToNext()
        }

        return lista
    }

    fun find(id: Int): Password?{
        val colunas = arrayOf("id", "descricao", "senha", "data_cri", "data_att")
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        val c = this.banco.readableDatabase.query("senhas", colunas, where, pWhere, null,null, null)

        c.moveToFirst()
        if (c.count == 1){
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val senha = c.getString(2)
            val dataCri = c.getLong(3)
            val dataAtt = c.getLong(4)
            return Password(id, descricao, senha, dataCri, dataAtt)
        }
        return null
    }

    fun delete(id: Int){
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete("senhas", where, pWhere)
    }

    fun update(senha: Password){
        val where = "id = ?"
        val pWhere = arrayOf(senha.id.toString())
        val cv = ContentValues()
        cv.put("descricao", senha.descricao)
        cv.put("senha", senha.senha)
        cv.put("data_att", Calendar.getInstance().timeInMillis)
        this.banco.writableDatabase.update("senhas", cv, where, pWhere)
    }

}