package com.polytechtest.data.mapper

import com.polytechtest.arch.Mapper
import com.polytechtest.domain.entity.CategoryModel
import com.polytechtest.network.entity.catogiries.AnswerData

class CategoriesMapper : Mapper<AnswerData, List<CategoryModel>> {
    override fun toDomain(model: AnswerData): List<CategoryModel> {
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