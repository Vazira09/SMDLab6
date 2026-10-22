package uz.ttpu.smd_lab6.task9


enum class Severity { MINOR, MAJOR, CRITICAL, BLOCKER }

sealed class Incident(val severity: Severity) {
    class FileProblem(val fileName: String) : Incident(Severity.MAJOR)
    class DatabaseDown(val database: String) : Incident(Severity.CRITICAL)
    class SlowPage(val url: String) : Incident(Severity.MINOR)
    class OutOfMemory : Incident(Severity.BLOCKER)
    data object Crash : Incident(Severity.CRITICAL)
}

fun describe(incident: Incident): String = when (incident) {
    is Incident.FileProblem -> "File ${incident.fileName} cannot be read"
    is Incident.DatabaseDown -> "Database ${incident.database} is down"
    is Incident.SlowPage -> "Page ${incident.url} is slow"
    is Incident.OutOfMemory -> "The server ran out of memory"
    Incident.Crash -> "The application crashed"
}

fun main() {
    val log = mutableListOf<Incident>()
    println("Report: file NAME, db NAME, slow URL, crash, or end")
    while (true) {
        print("> ")
        val parts = readln().trim().split(" ", limit = 2)
        val argument = parts.getOrElse(1) { "" }
        when (parts[0]) {
            "file" -> log.add(Incident.FileProblem(argument))
            "db" -> log.add(Incident.DatabaseDown(argument))
            "slow" -> log.add(Incident.SlowPage(argument))
            "crash" -> log.add(Incident.Crash)
            "end" -> break
            else -> println("Unknown report")
        }
    }

    for (incident in log.sortedByDescending { it.severity }) {
        if (incident.severity >= Severity.CRITICAL) {
            println("ALERT!")
        }
        println("[${incident.severity}] ${describe(incident)}")
    }
    val critical = log.count { it.severity == Severity.CRITICAL }
    println("Critical incidents: $critical of ${log.size}")
}
