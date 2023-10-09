package samuel.morais.minhassenhas

import android.os.Parcel
import android.os.Parcelable

class Password() : Parcelable {
    var tamanho = 0
    lateinit var descricao: String
    var numero: Boolean = false
    var especial: Boolean = false
    var maiusculo: Boolean = false
    private lateinit var senha: String

    constructor(parcel: Parcel) : this() {
        tamanho = parcel.readInt()
        descricao = parcel.readString() ?: ""
        senha = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(tamanho)
        parcel.writeString(descricao)
        parcel.writeString(senha)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Password> {
        override fun createFromParcel(parcel: Parcel): Password {
            return Password(parcel)
        }

        override fun newArray(size: Int): Array<Password?> {
            return arrayOfNulls(size)
        }
    }



    fun gerarSenha(tamanho: Int): String {
        val password = StringBuilder()
        val listaCaracteres = StringBuilder()
        val minuscula = "abcdefghijklmnopqrstuvwxyz"
        val maiuscula = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numeros = "0123456789"
        val especiais = "!@#/$%^&*()_+-=[]{}|;:,.<>?"

        listaCaracteres.append(minuscula)
        if (maiusculo)
            listaCaracteres.append(maiuscula)
        if (numero)
            listaCaracteres.append(numeros)
        if(especial)
            listaCaracteres.append(especiais)

        repeat(tamanho) {
            val randomIndex = (listaCaracteres.indices).random()
            val randomCaracteres = listaCaracteres[randomIndex]
            password.append(randomCaracteres)
        }
        senha = password.toString()
        return senha
    }
}