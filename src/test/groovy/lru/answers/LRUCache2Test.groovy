package lru.answers


import spock.lang.Specification

class LRUCache2Test extends Specification {

    def 'empty'() {
        given: 'empty cache'
        def cache = new LRUCache2<Integer, Integer>(1)

        expect: 'get non-existing item returns null'
        !cache.get(1)
    }

    def 'put - get'() {
        given: 'empty cache'
        def cache = new LRUCache2<Integer, Integer>(1)

        when: 'insert entry'
        cache.put(1, 2)

        then: 'the entry is gettable'
        cache.get(1) == 2
    }

    def 'get marks entry as a most recently used'() {
        given: 'empty cache with threshold 2'
        def cache = new LRUCache2<Integer, Integer>(2)

        and: 'fill the cache'
        cache.put(1, 1)
        cache.put(2, 2)

        when: 'get entry oldest entry'
        cache.get(1)

        and: 'trigger cache eviction'
        cache.put(3, 3)

        then: 'entry that was get is present'
        cache.get(1) == 1

        and: 'oldest entry disappears'
        !cache.get(2)
    }

    def 'verify if oldest entries are removed'() {
        given: 'empty cache with threshold 3'
        def cache = new LRUCache2<Integer, Integer>(3)

        when: 'overflow threshold by 2'
        cache.put(1, 1)
        cache.put(2, 2)
        cache.put(3, 3)
        cache.put(4, 4)
        cache.put(5, 5)

        then: 'oldest entries are removed'
        !cache.get(1)
        !cache.get(2)

        and: 'newest entries are present'
        cache.get(3) == 3
        cache.get(4) == 4
        cache.get(5) == 5
    }

    def 'changing value of existing entry marks it as a most recently used'() {
        given: 'empty cache with threshold 3'
        def cache = new LRUCache<Integer, Integer>(3)

        when: 'overflow threshold by 2'
        cache.put(1, 1)
        cache.put(2, 2)

        and: 'replace value to 3'
        cache.put(1, 3)

        and: 'put other values'
        cache.put(4, 4)
        cache.put(5, 5)

        then: 'oldest entries are removed'
        !cache.get(2)

        and: 'newest entries are present'
        cache.get(1) == 3
        cache.get(4) == 4
        cache.get(5) == 5
    }
}
