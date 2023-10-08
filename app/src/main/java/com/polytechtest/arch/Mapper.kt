package com.polytechtest.arch

interface Mapper<in Model, out DomainModel> {

    fun toDomain(model: Model): DomainModel
}