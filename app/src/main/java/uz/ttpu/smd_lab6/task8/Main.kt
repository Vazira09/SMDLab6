package uz.ttpu.smd_lab6.task8


sealed class ValidationError(val field: String, val message: String) {
    class EmptyName : ValidationError("name", "The name must not be empty")
    class TooYoung : ValidationError("age", "You must be 18 or older")
    class InvalidAge(text: String) : ValidationError("age", "'$text' is not an age between 0 and 120")
    class BadEmail(text: String) : ValidationError("email", "'$text' is not an e-mail address")

    protected constructor(code: Int) : this("code", "Error number $code")
}

fun validate(name: String, age: String, email: String): List<ValidationError> {
    val errors = mutableListOf<ValidationError>()
    if (name.isBlank()) errors.add(ValidationError.EmptyName())

    val ageInt = age.toIntOrNull()
    if (ageInt == null || ageInt !in 0..120) {
        errors.add(ValidationError.InvalidAge(age))
    } else if (ageInt < 18) {
        errors.add(ValidationError.TooYoung())
    }

    if ('@' !in email) errors.add(ValidationError.BadEmail(email))
    return errors
}

fun main() {
    print("Name: ")
    val name = readln()
    print("Age: ")
    val age = readln()
    print("E-mail: ")
    val email = readln()
    val errors = validate(name, age, email)
    if (errors.isEmpty()) {
        println("Registration accepted")
    } else {
        errors.forEach { println("${it.field}: ${it.message}") }
    }
}
