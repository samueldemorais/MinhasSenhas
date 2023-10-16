package samuel.morais.minhassenhas

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class Password() : Parcelable {
    var tamanho = 0
    var descricao = ""
    var numero: Boolean = false
    var especial: Boolean = false
    var maiusculo: Boolean = false
    lateinit var senha: String


    constructor(parcel: Parcel) : this() {
        tamanho = parcel.readInt()
        descricao = parcel.readString() ?: ""
        numero = parcel.readByte() != 0.toByte()
        especial = parcel.readByte() != 0.toByte()
        maiusculo = parcel.readByte() != 0.toByte()
        senha = parcel.readString() ?: ""

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(tamanho)
        parcel.writeString(descricao)
        parcel.writeByte(if (numero) 1 else 0)
        parcel.writeByte(if (especial) 1 else 0)
        parcel.writeByte(if (maiusculo) 1 else 0)
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

    override fun toString(): String {
        return "Tamanho: $tamanho, Descrição: $descricao, Número: $numero, Especial: $especial, Maiúsculo: $maiusculo, Senha: $senha"
    }


    fun gerarSenha(tamanho: Int): String {
        val password = StringBuilder()
        val listaCaracteres = StringBuilder()
        val minuscula = "abcdefghijklmnopqrstuvwxyz"
        val maiuscula = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numeros = "0123456789"
        val especiais = "!@#/$%^&*()_+-=[]{}|;:,.<>?"
        var digSenha = tamanho

        listaCaracteres.append(minuscula)
        if (maiusculo){
            listaCaracteres.append(maiuscula)
            val randomIndex = (maiuscula.indices).random()
            val randomCaracteres = maiuscula[randomIndex]
            password.append(randomCaracteres)
            digSenha -=1
        }

        if (numero) {
            listaCaracteres.append(numeros)
            val randomIndex = (numeros.indices).random()
            val randomCaracteres = numeros[randomIndex]
            password.append(randomCaracteres)
            digSenha -=1
        }
        if(especial){
            listaCaracteres.append(especiais)
            val randomIndex = (especiais.indices).random()
            val randomCaracteres = especiais[randomIndex]
            password.append(randomCaracteres)
            digSenha -=1
        }


        repeat(digSenha) {
            val randomIndex = (listaCaracteres.indices).random()
            val randomCaracteres = listaCaracteres[randomIndex]
            password.append(randomCaracteres)
        }

        val charArray = password.toString().toCharArray()
        charArray.shuffle()
        senha = String(charArray)
        return senha
    }
}