package com.example.lifruk.toolbox

import java.security.MessageDigest

/// Hashes a given password using the SHA-256 algorithm.
///
/// @param password The password to hash.
/// @return The hashed password as a hexadecimal string.
///
/// Example:
///
/// ```
/// val hashedPassword = hashPassword("mypassword")
/// ```
fun hashPassword(password: String): String {
    // hash the password using SHA-256
    val passwordHash = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())

    // return the hash as a hexadecimal string
    return passwordHash.joinToString("") { String.format("%02x", it) }
}