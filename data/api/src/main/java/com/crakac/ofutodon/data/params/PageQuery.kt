package com.crakac.ofutodon.data.params

data class PageQuery(
    val maxId: Long? = null,
    val minId: Long? = null,
    val sinceId: Long? = null,
    val limit: Int? = null,
    val offset: Int? = null,
) {
    fun toMap() = mapOf(
        "max_id" to maxId,
        "min_id" to minId,
        "since_id" to sinceId,
        "limit" to limit,
        "offset" to offset,
    ).filterValues { it != null }
        .mapValues { it.value.toString() }
}
