package com.crakac.ofutodon.mastodon.legacy.parameter

class FieldsAttributes(
    val fields: List<FieldParam>
) {
    fun toFieldMap(): Map<String, String> {
        return HashMap<String, String>().apply {
            fields.forEachIndexed { index, fieldParam ->
                this["fields_attributes[$index][name]"] = fieldParam.name
                this["fields_attributes[$index][value]"] = fieldParam.value
            }
        }
    }
}