import java.util.Scanner

fun main() {
    archiveMenu()
}

private val scanner = Scanner(System.`in`)
private val archives = mutableListOf<Archive>()

private fun addArchive(archive: Archive) {
    archives.add(archive)
}

private fun createArchive() {
    println("Введите название архива, который хотите создать")
    println("Для отмены введите цифру 2")
    val nameOfArchive = scanner.nextLine()

    when {
        nameOfArchive == "2" -> {
            println("Создание архива было отменено!")
            return
        }

        nameOfArchive.isNotBlank() -> {
            val newArchive = Archive(nameOfArchive)
            addArchive(newArchive)
            println("Архив \"$nameOfArchive\" успешно создан")
        }

        else -> println("Название не должно быть пустым!")
    }
}

private fun archiveMenu() {
    while (true) {
        println("Вывожу список архивов:")
        archives.forEachIndexed { index, archive ->
            println("${index + 1}. ${archive.name}")
        }

        println("${archives.size + 1}. Выход")
        println("- Выберите архив, введя его номер или ${archives.size + 1} для выхода")
        println("- Для создания нового архива введите 0.")
        val input = scanner.nextLine()

        when {
            input == "0" -> {
                createArchive()
            }

            input == "${archives.size + 1}" -> {
                println("Завершение работы. Спасибо за использование заметок!")
                return
            }

            input.toIntOrNull() != null -> {
                val archiveIndex = input.toInt()
                if (archiveIndex in 1..archives.size) {
                    noteMenu(archives[archiveIndex - 1])
                } else {
                    println("Нет архива по этим номером!")
                }
            }

            else -> println("Некорректный ввод! Повторите еще раз.")
        }
    }
}

private fun noteMenu(archive: Archive) {
    while (true) {
        println("Архив: ${archive.name}")
        println("Вывожу список заметок:")
        val lastNoteIndex = archive.notes.size
        archive.notes.forEachIndexed { index, note ->
            println("${index + 1}. ${note.title}")
        }
        println("${lastNoteIndex + 1}. Вернуться к выбору архива")
        println("- Выберите заметку введя ее номер или ${lastNoteIndex + 1} для возврата.")
        println("- Для создания новой заметки введите 0")
        val input = scanner.nextLine()

        when {
            input == "0" -> {
                createNote(archive)
            }

            input == "${lastNoteIndex + 1}" -> {
                println("Возвращаю к выбору архива.")
                return
            }

            input.toIntOrNull() != null -> {
                val noteIndex = input.toInt()
                if (noteIndex in 1..archive.notes.size) {
                    viewNoteContent(archive.notes[noteIndex - 1])
                    println("Для возврата в предыдущее меню введите любой символ.")
                    scanner.nextLine()
                } else {
                    println("Нет заметки с таким номером!")
                }
            }

            else -> {
                println("Некорректный ввод! Повторите еще раз.")
            }
        }
    }
}

private fun createNote(archive: Archive) {
    println("Введите заголовок новой заметки:")
    println("Для отмены введите 2.")
    val noteTitle = scanner.nextLine()
    when {
        noteTitle == "2" -> {
            println("Создание заметки отменено.")
            return
        }

        noteTitle.isNotBlank() -> {
            println("Введите содержимое новой заметки:")
            val noteContent = scanner.nextLine()
            if (noteContent.isNotEmpty()) {
                val newNote = Note(noteTitle, noteContent)
                archive.addNote(newNote)
                println("Заметка под названием \"$noteTitle\" добавлена в архив.")
            } else {
                println("Текст заметки не может быть пустым!")
                return
            }
        }

        else -> {
            println("Заголовок заметки не может быть пустым.")
        }
    }
}

private fun viewNoteContent(note: Note) {
    println("Содержимое заметки:")
    println("Заголовок: ${note.title}")
    println("Содержание: ${note.textOfNote}")
}