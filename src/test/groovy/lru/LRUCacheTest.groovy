package lru

import spock.lang.Specification

class LRUCacheTest extends Specification {
    def 'empty'() {
        given:
        def empty = new LRUCache<Integer, Integer>(1)

        expect:
        !empty.get(1)
    }

    def 'single value'() {
        given:
        def single = new LRUCache<Integer, Integer>(1)

        when:
        single.put(1, 2)

        then:
        single.get(1) == 2
    }

    def 'get refreshes recently used'() {
        given:
        def single = new LRUCache<Integer, Integer>(2)

        and:
        single.put(1, 1)
        single.put(2, 2)

        when:
        single.get(1)

        and:
        single.put(3, 3)

        then:
        !single.get(2)
    }
}
