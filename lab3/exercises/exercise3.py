import logging
import random
import numpy as np
import ray
from ray.util.client import ray
from collections import defaultdict


# excercise 3
# 3.0 start remote cluster settings and observe actors in cluster
# a) make screenshot of dependencies
# 3.1. Modify the Actor class MethodStateCounter and add/modify methods that return the following:
# a) - Get number of times an invoker name was called
# b) - Get a list of values computed by invoker name
# 3- Get state of all invokers
# 3.2 Modify method invoke to return a random int value between [5, 25]

# 3.3 Take a look on implement parralel Pi computation
# based on https://docs.ray.io/en/master/ray-core/examples/highly_parallel.html
#
# Implement calculating pi as a combination of actor (which keeps the
# state of the progress of calculating pi as it approaches its final value)
# and a task (which computes candidates for pi)


##### task0 ###########################################################################################################
@ray.remote
def get_ray_dependencies():
    from pip._internal.operations import freeze
    pkgs = list(freeze.freeze())
    return pkgs


def task0():
    snapshot_path = "./requirements.txt"
    pkgs = ray.get(get_ray_dependencies.remote())
    with open(snapshot_path, "w+") as f:
        for pkg in pkgs:
            f.write(f"{pkg}\n")
    print(f"Dependency snapshot saved in {snapshot_path}")
##### task0 ###########################################################################################################


##### task1 ###########################################################################################################
@ray.remote
class MethodStateCounter:
    def __init__(self):
        self.invoked = defaultdict(int)
        self.calculated = defaultdict(list)

    def invoke(self, name):
        # pretend to calculate something
        self.calculated[name].append(random.randint(5, 25))
        # update times invoked
        self.invoked[name] += 1
        return self.calculated[name][-1]

    def get_invoked_by_name(self, name):
        return self.invoked[name]

    def get_calculated_by_name(self, name):
        return self.calculated[name]

    def get_invoked(self):
        return self.invoked

    def get_calculated(self):
        return self.calculated


def task1():
    callers = ["A", "B", "C"]

    # Create an instance of our Actor
    worker_invoker = MethodStateCounter.remote()

    # Iterate and call the invoke() method by random callers and keep track of who
    # called it.
    # for _ in range(10):
    #     name = random.choice(callers)
    #     worker_invoker.invoke.remote(name)

    # Invoke a random caller and fetch the value or invocations of a random caller
    for _ in range(10):
        random_name_invoker = random.choice(callers)
        times_invoked = ray.get(worker_invoker.invoke.remote(random_name_invoker))
        print(f"Named caller: {random_name_invoker} calculated {times_invoked}")

    print(f"Invoked times: {dict(ray.get(worker_invoker.get_invoked.remote()))}")
    print(f"Calculated values: {dict(ray.get(worker_invoker.get_calculated.remote()))}")
##### task1 ###########################################################################################################


##### task3 ###########################################################################################################
# references
# https://docs.ray.io/en/master/ray-core/examples/highly_parallel.html
# https://www.geeksforgeeks.org/estimating-value-pi-using-monte-carlo/


@ray.remote
class PIEstimator:
    def __init__(self):
        self.accumulated_estimation = 0
        self.estimations_cnt = 0

    def add_estimation(self, estimation):
        self.accumulated_estimation += estimation
        self.estimations_cnt += 1

    def estimate(self):
        return self.accumulated_estimation / self.estimations_cnt


@ray.remote
def pi_sample(samples, pie):
    in_cnt = 0
    for i in range(samples):
        x = random.random()
        y = random.random()
        if x * x + y * y <= 1:
            in_cnt += 1

    estimation = in_cnt / samples * 4 # Formula with explanation in reference 2
    pie.add_estimation.remote(estimation)


def task3():
    samples = 10 ** 4
    total_samples = 10 ** 6
    batches = int(total_samples / samples)
    pie = PIEstimator.remote()
    print(f"Running {batches} batches, each of them runs {samples} experiments which gives {total_samples} experiments in total")
    for _ in range(batches):
        pi_sample.remote(samples, pie)
    print(f"PI estimation: {ray.get(pie.estimate.remote())}")
##### task3 ###########################################################################################################


if __name__ == "__main__":
    if ray.is_initialized:
        ray.shutdown()
    ray.init(logging_level=logging.ERROR)
    np.random.seed(5264)

    print(">>>>> task0")
    task0()
    print()

    # task2 as part of task1
    print(">>>>> task1")
    task1()
    print()

    print(">>>>> task3")
    task3()
    print()

    ray.shutdown()
