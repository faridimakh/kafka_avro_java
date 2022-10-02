package com.org.far.principals;

import com.example.Station;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class testPartitioner implements Partitioner {


    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        int p;
        if (((Station) o1).getContractName().equalsIgnoreCase("marseille"))
            p = 0;
        else p = 1;

        return p;
    }

    @Override
    public void close() {
        System.out.println("consumer closed");

    }

    @Override
    public void configure(Map<String, ?> map) {
        map.get("vilib.station.name");
    }
}