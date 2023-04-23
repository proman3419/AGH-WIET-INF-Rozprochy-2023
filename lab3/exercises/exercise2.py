import logging
from time import sleep
from typing import List, Set

import numpy as np
from ray.util.client import ray


# Excercises 2.1) Create large llists and python ldictionaries,
# put them in object store. Write a Ray task to process them.


def create_llist(N: int) -> "ray.ObjectRef":
    llist = np.random.randint(0, high=N, size=N, dtype=int)
    return ray.put([ray.put(llist[i]) for i in range(N)])


def create_ldict(N: int) -> "ray.ObjectRef":
    llist = np.random.randint(0, high=N, size=N, dtype=int)
    return ray.put({i: llist[i] for i in range(N)})


# For each list, dict pair:
# Select values (key, value) pairs from the dict such that the key exists in the list and the value is prime
@ray.remote
def process(llists: List["ray.ObjectRef"], ldicts: List["ray.ObjectRef"]) -> "ray.ObjectRef":
    def generate_primes(N: int) -> Set[int]:
        return set([x for x in range(1, N+1) if is_prime(x)])

    def is_prime(x: int) -> bool:
        if x < 2: return False
        if x == 2: return True
        if x % 2 == 0: return False

        i = 3
        while i * i <= x:
            if x % i == 0:
                return False
            i += 2

        return True

    M = len(llists)
    assert len(ldicts) == M

    llist = ray.get(llists[0])
    N = len(llist)
    assert len(llist) > 0

    primes = generate_primes(N)
    results = []
    for i in range(M):
        llist = [ray.get(x) for x in ray.get(llists[i])]
        ldict = ray.get(ldicts[i])

        result = []
        for key in set(llist):
            if key in ldict:
                val = ldict[key]
                if val in primes:
                    result.append((key, val))

        results.append(ray.put(result))
    return results


def print_results(results: "ray.ObjectRef") -> None:
    results = [ray.get(r) for r in ray.get(results)]
    for i, result in enumerate(results):
        print(f">>>>> Result {i}")
        for key, val in result:
            print(f"({key}, {val})", end=", ")
        print()


if __name__ == "__main__":
    if ray.is_initialized:
        ray.shutdown()
    ray.init(logging_level=logging.ERROR)
    np.random.seed(5264)

    N = 10 ** 3
    M = 10 ** 1

    llists = []
    ldicts = []
    for i in range(M):
        llists.append(create_llist(N))
        ldicts.append(create_ldict(N))
    llists = ray.put(llists)
    ldicts = ray.put(ldicts)

    result = process.remote(llists, ldicts)
    print_results(result)

    ray.shutdown()
