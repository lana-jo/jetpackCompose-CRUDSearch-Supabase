package com.lj.crud_supabase.presentation.utils

/**
 * Class untuk verifikasi/validasi semua inputan user
 * Menyediakan method untuk validasi berbagai tipe data
 */
object InputValidator {

    /**
     * Validasi Product Name
     *
     * @param name Nama produk yang akan divalidasi
     * @return ValidationResult berisi status dan pesan error
     */
    fun validateProductName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Product name cannot be empty"
            )
            name.trim().isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Product name cannot contain only spaces"
            )
            name.length < 3 -> ValidationResult(
                isValid = false,
                errorMessage = "Product name must be at least 3 characters"
            )
            name.length > 100 -> ValidationResult(
                isValid = false,
                errorMessage = "Product name must not exceed 100 characters"
            )
            !name.matches(Regex("^[a-zA-Z0-9\\s\\-()]+$")) -> ValidationResult(
                isValid = false,
                errorMessage = "Product name contains invalid characters"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Product Price
     *
     * @param price Harga produk (String)
     * @return ValidationResult berisi status dan pesan error
     */
    fun validateProductPrice(price: String): ValidationResult {
        return when {
            price.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Product price cannot be empty"
            )
            price.toDoubleOrNull() == null -> ValidationResult(
                isValid = false,
                errorMessage = "Product price must be a valid number"
            )
            price.toDouble() <= 0 -> ValidationResult(
                isValid = false,
                errorMessage = "Product price must be greater than 0"
            )
            price.toDouble() > 999_999_999.99 -> ValidationResult(
                isValid = false,
                errorMessage = "Product price is too large"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Product Price (Double)
     *
     * @param price Harga produk (Double)
     * @return ValidationResult berisi status dan pesan error
     */
    fun validateProductPrice(price: Double): ValidationResult {
        return when {
            price <= 0 -> ValidationResult(
                isValid = false,
                errorMessage = "Product price must be greater than 0"
            )
            price > 999_999_999.99 -> ValidationResult(
                isValid = false,
                errorMessage = "Product price is too large"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Email
     *
     * @param email Email yang akan divalidasi
     * @return ValidationResult berisi status dan pesan error
     */
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Email cannot be empty"
            )
            !email.contains("@") -> ValidationResult(
                isValid = false,
                errorMessage = "Email must contain @ symbol"
            )
            !email.contains(".") -> ValidationResult(
                isValid = false,
                errorMessage = "Email must contain domain"
            )
            !email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$")) -> ValidationResult(
                isValid = false,
                errorMessage = "Email format is invalid"
            )
            email.length > 254 -> ValidationResult(
                isValid = false,
                errorMessage = "Email is too long"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Password
     *
     * @param password Password yang akan divalidasi
     * @return ValidationResult berisi status dan pesan error
     */
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Password cannot be empty"
            )
            password.length < 6 -> ValidationResult(
                isValid = false,
                errorMessage = "Password must be at least 6 characters"
            )
            password.length > 50 -> ValidationResult(
                isValid = false,
                errorMessage = "Password must not exceed 50 characters"
            )
            !password.matches(Regex(".*[A-Z].*")) -> ValidationResult(
                isValid = false,
                errorMessage = "Password must contain at least one uppercase letter"
            )
            !password.matches(Regex(".*[a-z].*")) -> ValidationResult(
                isValid = false,
                errorMessage = "Password must contain at least one lowercase letter"
            )
            !password.matches(Regex(".*[0-9].*")) -> ValidationResult(
                isValid = false,
                errorMessage = "Password must contain at least one digit"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Password Confirmation
     *
     * @param password Password yang akan dicocokkan
     * @param confirmPassword Password confirmation
     * @return ValidationResult berisi status dan pesan error
     */
    fun validatePasswordConfirmation(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Password confirmation cannot be empty"
            )
            password != confirmPassword -> ValidationResult(
                isValid = false,
                errorMessage = "Passwords do not match"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Username
     *
     * @param username Username yang akan divalidasi
     * @return ValidationResult berisi status dan pesan error
     */
    fun validateUsername(username: String): ValidationResult {
        return when {
            username.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Username cannot be empty"
            )
            username.length < 3 -> ValidationResult(
                isValid = false,
                errorMessage = "Username must be at least 3 characters"
            )
            username.length > 20 -> ValidationResult(
                isValid = false,
                errorMessage = "Username must not exceed 20 characters"
            )
            !username.matches(Regex("^[a-zA-Z0-9_.-]+$")) -> ValidationResult(
                isValid = false,
                errorMessage = "Username can only contain letters, numbers, dots, dashes and underscores"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Nomor Telepon
     *
     * @param phoneNumber Nomor telepon yang akan divalidasi
     * @return ValidationResult berisi status dan pesan error
     */
    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        return when {
            phoneNumber.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Phone number cannot be empty"
            )
            !phoneNumber.matches(Regex("^[0-9+\\-\\s()]+$")) -> ValidationResult(
                isValid = false,
                errorMessage = "Phone number contains invalid characters"
            )
            phoneNumber.replace(Regex("[^0-9]"), "").length < 10 -> ValidationResult(
                isValid = false,
                errorMessage = "Phone number must be at least 10 digits"
            )
            phoneNumber.replace(Regex("[^0-9]"), "").length > 15 -> ValidationResult(
                isValid = false,
                errorMessage = "Phone number is too long"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Search Query
     *
     * @param query Search query yang akan divalidasi
     * @return ValidationResult berisi status dan pesan error
     */
    fun validateSearchQuery(query: String): ValidationResult {
        return when {
            query.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "Search query cannot be empty"
            )
            query.length < 2 -> ValidationResult(
                isValid = false,
                errorMessage = "Search query must be at least 2 characters"
            )
            query.length > 100 -> ValidationResult(
                isValid = false,
                errorMessage = "Search query must not exceed 100 characters"
            )
            else -> ValidationResult(
                isValid = true,
                errorMessage = null
            )
        }
    }

    /**
     * Validasi Form lengkap untuk Add Product
     *
     * @param name Nama produk
     * @param price Harga produk (String)
     * @return FormValidationResult dengan status semua field
     */
    fun validateAddProductForm(name: String, price: String): FormValidationResult {
        val nameValidation = validateProductName(name)
        val priceValidation = validateProductPrice(price)

        return FormValidationResult(
            isValid = nameValidation.isValid && priceValidation.isValid,
            nameValidation = nameValidation,
            priceValidation = priceValidation
        )
    }

    /**
     * Validasi Form lengkap untuk Update Product
     *
     * @param name Nama produk
     * @param price Harga produk (Double)
     * @return FormValidationResult dengan status semua field
     */
    fun validateUpdateProductForm(name: String, price: Double): FormValidationResult {
        val nameValidation = validateProductName(name)
        val priceValidation = validateProductPrice(price)

        return FormValidationResult(
            isValid = nameValidation.isValid && priceValidation.isValid,
            nameValidation = nameValidation,
            priceValidation = priceValidation
        )
    }

    /**
     * Validasi Form lengkap untuk Login
     *
     * @param email Email user
     * @param password Password user
     * @return FormValidationResult dengan status semua field
     */
    fun validateLoginForm(email: String, password: String): FormValidationResult {
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)

        return FormValidationResult(
            isValid = emailValidation.isValid && passwordValidation.isValid && email.isNotEmpty() && password.isNotEmpty(),
            emailValidation = emailValidation,
            passwordValidation = passwordValidation
        )
    }

    /**
     * Validasi Form lengkap untuk Register
     *
     * @param email Email user
     * @param password Password user
     * @param confirmPassword Password confirmation
     * @return FormValidationResult dengan status semua field
     */
    fun validateRegisterForm(email: String, password: String, confirmPassword: String): FormValidationResult {
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        val confirmPasswordValidation = validatePasswordConfirmation(password, confirmPassword)

        return FormValidationResult(
            isValid = emailValidation.isValid && passwordValidation.isValid && confirmPasswordValidation.isValid,
            emailValidation = emailValidation,
            passwordValidation = passwordValidation,
            confirmPasswordValidation = confirmPasswordValidation
        )
    }
}

/**
 * Data class untuk hasil validasi single input
 */
data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

/**
 * Data class untuk hasil validasi form lengkap
 */
data class FormValidationResult(
    val isValid: Boolean,
    val nameValidation: ValidationResult? = null,
    val priceValidation: ValidationResult? = null,
    val emailValidation: ValidationResult? = null,
    val passwordValidation: ValidationResult? = null,
    val confirmPasswordValidation: ValidationResult? = null
)