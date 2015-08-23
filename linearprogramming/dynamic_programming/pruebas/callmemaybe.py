import collections
import functools


class memoized(object):
    def __init__(self, func):
        self.func = func
        self.cache = {}

    def __call__(self, *args):
        if not isinstance(args, collections.Hashable):
            # uncacheable. a list, for instance.
            # better to not cache than blow up.
            return self.func(*args)
        if args in self.cache:
            return self.cache[args]
        else:
            value = self.func(*args)
            self.cache[args] = value
            return value

    def __repr__(self):
        return self.func.__doc__

    def __get__(self, obj, objtype):
        return functools.partial(self.__call__, obj)


def knapsack(items, maxweight, maxvolume):
    # Return the value of the most valuable subsequence of the first i
    # elements in items whose weights sum to no more than j.
    @memoized
    def bestvalue(i, j, k):
        if i == 0:
            return 0
        value, weight, volume = items[i - 1]
        if weight > j or volume > k:
            return bestvalue(i - 1, j, k)
        else:
            return max(bestvalue(i - 1, j, k),
                       bestvalue(i - 1, j - weight, k) + value,
                       bestvalue(i - 1, j - weight, k-volume) + value)

    j = maxweight
    k = maxvolume
    result = []
    for i in xrange(len(items), 0, -1):
        if bestvalue(i, j, k) != bestvalue(i - 1, j, k):
            result.append(items[i - 1])
            j -= items[i - 1][1]
            k -= items[i - 1][2]

    result.reverse()
    return bestvalue(len(items), maxweight, maxvolume), result

items = [(4, 12, 2), (2, 1, 5), (6, 4, 3), (1, 1, 5), (2, 2, 1)]
print knapsack(items, 15, 9)
