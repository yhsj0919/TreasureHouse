package xyz.yshj.treasure

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TreasureApplication

fun main(args: Array<String>) {
    runApplication<TreasureApplication>(*args)
}
