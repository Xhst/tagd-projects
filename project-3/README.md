# Project 3 presentation
The purpose of the project is to model the performance of a DBMS based on your computer and the TPC-H benchmark.
The project consists of the following points; the bottleneck should be specified for each.

1. Real machine performance analysis (query execution time, service time, and center utilization) with maximum number of concurrent queries equal to 1 and the docker container configured to expose only one CPU.

2. Comparison of observed performance on the real machine versus that predicted by a simulative model.

3. Using the model to predict performance (query execution time, utilization
of centers, throughput) with a maximum number of concurrent queries equal to 2 and of the same type, and verify and discuss any deviations from the performance obtained by
running the benchmark with this configuration on the machine

4. What is the performance predicted by the model in the case where 2 CPUs are used and a maximum number of concurrent queries that varies between 1 and 5? Do they reflect the performance actually found by running the benchmark with these parameters?

   - Size the system such that the average response time is less than 30 seconds for a maximum number of concurrent queries not exceeding 20,
  ensuring that utilization is in the 60%-70% range. The storage subsystem must be implemented with a RAID 5.

5. Sizing a system that meets the following requirements:
    - **(a)** The system is subject to a workload consisting of a mix of 5 queries: A, B, C, D, E
      
    - **(b)** Queries A, B, D, E exhibit exponential arrival rates 𝜆𝑎 = 𝜆𝑏 = 𝜆𝑑 = 𝜆𝑒 =2 𝑟𝑒𝑞/𝑠𝑒𝑐; query C, on the other hand, is of bursty type, characterized by two intervals:
      - Interval A, occurs with probability 0.9 and has a duration sampled from an exponential distribution with mean 30 seconds. During this
        interval, the arrival rate is exponential with parameter 𝜆𝑐 = 2 𝑟𝑒𝑞/𝑠𝑒𝑐.
      - Interval B, occurs with probability 0.1 and has a duration sampled from
        an exponential distribution with mean 3 seconds. During this intervalinterval, the arrival rate is exponential with parameter 𝜆𝑐,𝑏𝑢𝑟𝑠𝑡𝑦 = 20 𝑟𝑒𝑞/𝑠𝑒𝑐.

    - **(c)** CPU and disk service times are extrapolated from those identified in Step 1. We assume specifically for type A and type B queries a service time for CPU and
    disk equal to that of the most CPU-intensive query type; assume for queries
    D and E a service time for CPU and disk equal to that of the most
    disk-intensive; finally, assume for queries of type C a CPU and
    disk equal to that of the query with more balanced CPU and disk service times.
    
    - **(d)** The response time of each query should not exceed, on average, the response time of the
    response time of the longest query with the system unloaded.
    
    - **(e)** The utilization of each center in the system must be between 60 percent and 70 percent
    
    - **(f)** The storage subsystem must be implemented with a RAID 5
