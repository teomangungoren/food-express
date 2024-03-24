package com.foodexpress.authservice.domain.model

import com.foodexpress.authservice.domain.enums.UserRole
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Id
    val id: String,
    val email: String,
    @get:JvmName("username2")
    val username: String,
    @get:JvmName("password2")
    var password: String,
    @Embedded
    @AttributeOverride(name="street", column = Column(name = "BILLING_STREET"))
    @AttributeOverride(name = "zipcode",column = Column(name = "BILLING_ZIPCODE", length = 5))
    @AttributeOverride(name = "city", column = Column(name = "BILLING_CITY"))
    val billingAdress: Adress,
    @CreatedDate
    val createdDate: Instant? = null,
    @LastModifiedDate
    val updatedAt: Instant? = null,
    val role: UserRole = UserRole.USER
):UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
