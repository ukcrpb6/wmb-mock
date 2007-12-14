INSERT INTO component (name, ttl, ttd) values('component1', 1000, 2000);
INSERT INTO component (name, ttl, ttd) values('component2', 1000, 2000);

INSERT INTO lookup (component_id, name, value) values(1, 'key1', 'value1');
INSERT INTO lookup (component_id, name, value) values(1, 'key2', 'value2');
INSERT INTO lookup (component_id, name, value) values(1, 'key3', 'value3');
INSERT INTO lookup (component_id, name, value) values(2, 'key1', 'value4');
INSERT INTO lookup (component_id, name, value) values(2, 'key5', 'value5');
