package com.polytechtest.data.mapper

import com.polytechtest.arch.Mapper
import com.polytechtest.domain.entity.CategoryModel
import com.polytechtest.network.entity.catogiries.CategoriesAnswer

class CategoriesMapper : Mapper<CategoriesAnswer, List<CategoryModel>> {
    override fun toDomain(model: CategoriesAnswer): List<CategoryModel> {
        val resultList = model.results
        val list = resultList.map {
            CategoryModel(
                0,
                it.display_name,
                it.list_name_encoded,
                it.newest_published_date
            )
        }
        return list.toSet().toList()
    }
}