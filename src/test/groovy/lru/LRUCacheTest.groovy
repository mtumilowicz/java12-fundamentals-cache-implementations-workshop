package lru

import spock.lang.Specification

class LRUCacheTest extends Specification {
    def 'empty'() {
        given:
        def empty = new LRUCache(1)

        expect:
        !empty.get(1)
    }
}
