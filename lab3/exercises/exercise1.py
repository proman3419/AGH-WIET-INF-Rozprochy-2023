import cProfile
import logging
from typing import List

import numpy as np
from ray.util.client import ray


# Excercises 1.1)Try using local bubble sort and remote bubble sort,
# show difference

# Given an N x N ndarray sort each subarray with bubblesort.
# Sorting a single array won't show ray's improved performance as it deals better with calculations that can be parallelized.
# For tasks that can't be parallelized ray performs worse because it's penalized by its overhead with no benefits.


def bubble_sort(A: List[int]) -> List[int]:
    n = len(A)
    for i in range(n):
        for j in range(n):
            if A[i] < A[j]:
                A[i], A[j] = A[j], A[i]
    return A


@ray.remote
def bubble_sort_distributed(A: List[int]) -> List[int]:
    return bubble_sort(A)


def get_random_ndarray(N: int) -> List[List[int]]:
    return np.random.randint(0, high=N ** 2, size=(N, N), dtype=int).tolist()


def run_bubble_sort_local(N: int) -> None:
    A = get_random_ndarray(N)
    A_sorted = [bubble_sort(A[i]) for i in range(N)]
    assert all(A_sorted[i] == sorted(A[i]) for i in range(N))


def run_bubble_sort_remote(N: int) -> None:
    A = get_random_ndarray(N)
    A_sorted_ref = [bubble_sort_distributed.remote(A[i]) for i in range(N)]
    assert all(ray.get(A_sorted_ref[i]) == sorted(A[i]) for i in range(N))


if __name__ == "__main__":
    if ray.is_initialized:
        ray.shutdown()
    ray.init(logging_level=logging.ERROR)
    np.random.seed(5264)

    print("bubble sort local run")
    cProfile.run("run_bubble_sort_local(5*10**2)")
    # 5*10**2 -> 2019 function calls in 11.228 seconds

    print("bubble sort remote run")
    cProfile.run("run_bubble_sort_remote(5*10**2)")
    # 5*10**2 -> 2120091 function calls in 4.373 seconds

    ray.shutdown()
