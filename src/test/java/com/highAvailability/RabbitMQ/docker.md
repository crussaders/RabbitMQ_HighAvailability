version: '3.9'

services:
rabbit1:
image: rabbitmq:3.12-management
hostname: rabbit1
container_name: rabbit1
environment:
- RABBITMQ_ERLANG_COOKIE=supersecretcookie
- RABBITMQ_NODENAME=rabbit@rabbit1
ports:
- "15672:15672"  # Management UI
- "5672:5672"    # AMQP
networks:
- rabbitnet

rabbit2:
image: rabbitmq:3.12-management
hostname: rabbit2
container_name: rabbit2
environment:
- RABBITMQ_ERLANG_COOKIE=supersecretcookie
- RABBITMQ_NODENAME=rabbit@rabbit2
- RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS=-rabbit cluster_nodes [{rabbit@rabbit1}],{disc}
ports:
- "15673:15672"  # Management UI
- "5673:5672"    # AMQP
depends_on:
- rabbit1
networks:
- rabbitnet

networks:
rabbitnet:
driver: bridge