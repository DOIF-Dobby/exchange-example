package com.switchwon.exchange.example.system.data

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun <T, ID : Any> CrudRepository<T, ID>.findByIdOrThrow(id: ID, throwBlock: (id: ID) -> Throwable): T {
    return this.findByIdOrNull(id) ?: throw throwBlock(id)
}
