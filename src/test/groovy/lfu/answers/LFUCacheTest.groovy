package lfu.answers


import spock.lang.Specification

class LFUCacheTest extends Specification {

    def 'empty'() {
        given: 'empty cache'
        def cache = new LFUCache<Integer, Integer>(1)

        expect: 'get non-existing item returns null'
        !cache.get(1)
    }

    def 'put - get'() {
        given: 'empty cache'
        def cache = new LFUCache<Integer, Integer>(1)

        when: 'insert entry'
        cache.put(1, 2)

        then: 'the entry is gettable'
        cache.get(1) == 2
    }

    def 'same frequencies: verify if oldest entries are removed'() {
        given: 'empty cache with threshold 3'
        def cache = new LFUCache<Integer, Integer>(3)

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

    def 'same frequencies: changing value of existing entry marks it as a most frequently used'() {
        given: 'empty cache with threshold 3'
        def cache = new LFUCache<Integer, Integer>(3)

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

    def 'different frequencies: verify if least frequently used are removed'() {
        given: 'empty cache with threshold 3'
        def cache = new LFUCache<Integer, Integer>(3)

        and: 'fill the cache'
        cache.put(1, 1)
        cache.put(2, 2)
        cache.put(3, 3)

        and: 'increment frequencies'
        cache.get(1)
        and:
        cache.get(3)
        cache.get(3)
        cache.get(3)
        and:
        cache.get(2)
        cache.get(2)

        when: 'overflow buffer'
        cache.put(4, 4)

        then: 'least frequently used not exist'
        !cache.get(1)

        when: 'overflow buffer'
        cache.put(5, 5)

        then: 'least frequently used not exist'
        !cache.get(4)

        and: 'most frequently used entries are present'
        cache.get(2) == 2
        cache.get(3) == 3
        cache.get(5) == 5
    }

}
