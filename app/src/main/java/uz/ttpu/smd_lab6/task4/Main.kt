package uz.ttpu.smd_lab6.task4


sealed class Payment {
    data class CreditCard(val number: String, val expiry: String) : Payment()
    data class PayPal(val email: String) : Payment()
    data class Crypto(val wallet: String) : Payment()
    data object Cash : Payment()
}

fun readPayment(): Payment? {
    println("1) Credit card   2) PayPal   3) Cash   4) Crypto")
    print("Choose a payment method: ")
    return when (readln().trim()) {
        "1" -> {
            print("Card number: ")
            val number = readln().trim()
            print("Expiry (MM/YY): ")
            Payment.CreditCard(number, readln().trim())
        }
        "2" -> {
            print("PayPal e-mail: ")
            Payment.PayPal(readln().trim())
        }
        "3" -> Payment.Cash
        "4" -> {
            print("Wallet address: ")
            Payment.Crypto(readln().trim())
        }
        else -> null
    }
}

fun process(payment: Payment, amount: Double) {
    when (payment) {
        is Payment.CreditCard ->
            println("Charged $amount to the card ending ${payment.number.takeLast(4)} (expires ${payment.expiry})")
        is Payment.PayPal ->
            println("Sent a payment request for $amount to ${payment.email}")
        is Payment.Crypto ->
            println("Waiting for a transfer to wallet ${payment.wallet}")
        Payment.Cash ->
            println("Please pay $amount in cash at the desk")
    }
}

fun main() {
    print("Amount to pay: ")
    val amount = readln().trim().toDoubleOrNull()
    if (amount == null) {
        println("Invalid amount")
        return
    }
    val payment = readPayment()
    if (payment == null) {
        println("Unknown payment method")
        return
    }
    println("You chose: $payment")
    process(payment, amount)
}
