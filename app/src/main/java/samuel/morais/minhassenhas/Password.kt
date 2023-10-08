package samuel.morais.minhassenhas

class Password {
    private lateinit var tamanho: Number
    private lateinit var descricao: String
    var numero: Boolean = false
    var especial: Boolean = false
    var maiusculo: Boolean = false


//    val senhaGerada = gerarSenha(tamanho)

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

        return password.toString()
    }
}