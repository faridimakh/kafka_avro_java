kafka-topics --create --zookeeper zookeeper:2181 --topic topicstation --replication-factor 1 --partitions 2 --config "cleanup.policy=compact" --config "retention.ms=1" --config "segment.ms=1" --config "min.cleanable.dirty.ratio=0.01"

kafka-topics --bootstrap-server localhost:9092 --describe --topic topicstation

kafka-topics --bootstrap-server localhost:9092 --delete --topic topicstation

kafka-run-class kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic topicstation --time -1

kafka-topics --create --zookeeper zookeeper:2181 --topic topicstation --replication-factor 1 --partitions 1 --config "cleanup.policy=compact" --config min.cleanable.dirty.ratio=0.001 --config segment.ms=5000

--ksqlDB:
CREATE STREAM stationstream WITH (KAFKA_TOPIC = 'topicstation',VALUE_FORMAT = 'AVRO');
select * from stationstream emit changes limit 10;
CREATE TABLE tablestations (number VARCHAR PRIMARY KEY, name VARCHAR,available_bike_stands INT,available_bikes INT,banking boolean, bike_stands INT) WITH (KAFKA_TOPIC = 'topicstation',VALUE_FORMAT='AVRO');
available_bike_stands INT,available_bikes INT,banking boolean, bike_stands INT },

--mongodb CLI
mongo --port 27017 -u farid -p ff --authenticationDatabase fardb




