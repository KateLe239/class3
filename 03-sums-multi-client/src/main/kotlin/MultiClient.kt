import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress
import kotlin.random.Random

/*
Напишите программу, в которой создаётся и запускается 5 клиентов из предыдущего упражнения.
Для запуска следует использовать конструкцию launch внутри тела цикла. Организуйте консольный вывод так,
чтобы было понятно, какой из клиентов получает ответ (например, добавляя номер клиента).
 */

fun main() {
    five()

}
fun five() = runBlocking {
    launch{
        for(n in 1..5){
            sumsClient("127.0.0.1", 2323, n)
        }
}

fun sumsClient(hostname: String, port: Int, num: Int) {
    val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).
    tcp().connect(InetSocketAddress(hostname, port))
    val input = socket.openReadChannel()
    val output = socket.openWriteChannel(autoFlush = true)

    println("Client $num on")

            for (i in 1..100) {
                //Thread.sleep(1_000)
                val k: Int = Random.nextInt(10)
                var out: String = Random.nextInt(10000).toString()
                for (j in 2..k) {
                    out += ' '
                    out += Random.nextInt(10000).toString()
                }
                println(out)
                output.writeStringUtf8("$out\r\n" + "$num\n")
                val response = input.readUTF8Line()
                println("Server said: '$response'")
            }

    }


    fun five() = runBlocking {
        launch{
            for(n in 1..5){
                sumsClient("127.0.0.1", 2323, n)
            }
        }
