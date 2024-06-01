import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database


object genres : Table() {
    val genreId = integer("genre_id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()
}


fun main() {

    Database.connect(
        url = "jdbc:mariadb://192.168.0.152:3306/ranobe_read",
        driver = "org.mariadb.jdbc.Driver",
        user = "arisen",
        password = System.getenv("maria_pass")
    )

    val Genres = arrayOf(
            "Романтика", "Трагедия", "Милитаризм", "Экшн",
    "Приключение", "Юмор", "Драма", "Фэнтези",
    "Гарем", "Боевые искусства", "Сянься"
    )

    transaction {

        // Добавление жанров из массива, проверка наличия в таблице
        Genres.forEach { genre ->
            val genreExists = genres.select { genres.name eq genre }.count() > 0
            if (!genreExists) {
                genres.insert {
                    it[name] = genre
                }
                println("Добавлен жанр: $genre")
            } else {
                println("Жанр уже существует: $genre")
            }
        }
    }

}