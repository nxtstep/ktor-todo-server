# This document describes the setup for developing the service

For running a transient jdbc service for developing and testing your APIs
without to much trouble you can run a mysql:5.7 container without any (permanent) backing volume.

## MySQL

```bash
docker run --platform linux/x86_64 \
  -d [-p 6606:3306] \
  -e MYSQL_ROOT_PASSWORD=secret \
  -e MYSQL_DATABASE=todos \
  [--volume volume-name:/var/lib/mysql] \
  mysql:5.7
```

## Docker compose

While developing you can also rebuild the docker image(s) with compose

```bash
docker compose build [--no-cache]
```

And run the distribution version with

```bash
docker compose up -d
```

Shut it down

```bash
docker compose down
```
