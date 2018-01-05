package xyz.yshj.treasure

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TreasureApplication

fun main(args: Array<String>) {
    SpringApplication.run(TreasureApplication::class.java, *args)
}
