package com.crakac.ofutodon.mastodon

import com.crakac.ofutodon.mastodon.params.PageQuery
import org.junit.Assert
import org.junit.Test

class PageQueryTest {
    @Test
    fun test() {
        val query = PageQuery(maxId = 1L)
        val map = query.toMap()
        Assert.assertEquals(map["max_id"], "1")
        Assert.assertNull(map["since_id"])
    }
}