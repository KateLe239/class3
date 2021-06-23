import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress
import kotlin.random.Random

/*
Напишите клиента, который генерирует случайное количество случайных целых чисел (Random.nextInt),
формирует запрос с числами на сервер, отправляет его, получает от сервера и печатает результат.
Всего клиент должен посылать 100 запросов.
 */
fun main() = runBlocking {
    val hostname = "127.0.0.1"
    val port = 2323
    val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress(hostname, port))
    val input = socket.openReadChannel()
    val output = socket.openWriteChannel(autoFlush = true)

    for(i in 1..100) {
        val k: Int = Random.nextInt(10)
        var out: String = Random.nextInt(10000).toString()
        for(i in 2..k){
            out += ' '
            out += Random.nextInt(10000).toString()
        }
        println(out)
        output.writeStringUtf8("$out\r\n")
        val response = input.readUTF8Line()
        println("Server said: '$response'")
    }
}
