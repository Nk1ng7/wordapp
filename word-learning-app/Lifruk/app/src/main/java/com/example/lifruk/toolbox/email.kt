import java.util.regex.Pattern

fun isEmailWellFormatted(value: String): Boolean {
    val pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    )
    return pattern.matcher(value).matches()
}