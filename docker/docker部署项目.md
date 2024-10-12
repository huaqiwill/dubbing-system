# Docker Create Image

## Redis

### redis.conf

```ini
# 指定监听地址，默认为127.0.0.1
bind 0.0.0.0
# 配置 Redis 监听所有网络接口，以允许外部连接。

# 设置保护模式为no，以便允许外部连接
protected-mode no
# 禁用保护模式，使 Redis 可接受来自所有网络接口的连接。

# 指定端口
port 6379
# 配置 Redis 使用默认端口 6379 进行连接。

# 设置最大内存使用（根据需求调整）
maxmemory 256mb
# 将 Redis 的最大内存使用限制为 256MB，防止内存使用过多。

# 设置内存淘汰策略
maxmemory-policy allkeys-lru
# 当达到最大内存限制时，使用 LRU（最近最少使用）策略从所有键中淘汰。

# 开启持久化
save 900 1
save 300 10
save 60 10000
# 配置 RDB 持久化，在 900 秒内至少有 1 个 key 变更时触发保存，在 300 秒内至少有 10 个 key 变更时触发保存，在 60 秒内至少有 10000 个 key 变更时触发保存。

# 设定AOF持久化方式
appendonly yes
# 启用 AOF（Append Only File）持久化，记录每次写操作。

# 指定AOF文件名
appendfilename "appendonly.aof"
# 指定 AOF 文件名为 "appendonly.aof"。

# 设置访问密码
requirepass EUMbnnhVDr9gRtET3uyhCxZxq09yGXaX
# 配置 Redis 访问密码。

# 设置数据文件目录
dir /data
# 指定数据文件存储目录为 /data。

# 指定RDB文件名
dbfilename dump.rdb
# 指定 RDB 文件名为 "dump.rdb"。
```

### command

```sh
docker run -d \
  --name redis \
  --restart=always \
  -e REDIS_PASSWORD=EUMbnnhVDr9gRtET3uyhCxZxq09yGXaX \
  -v ~/redis/redis-data:/data \
  -v ~/redis/redis.conf:/usr/local/etc/redis/redis.conf \
  -p 6379:6379 \
  redis:latest \
  redis-server /usr/local/etc/redis/redis.conf
```

## MySQL

### my.cnf

```ini
[mysqld]
# 基本设置
bind-address = 0.0.0.0
port = 3306
datadir = /var/lib/mysql
server-id = 1

# 日志文件
log-error = /var/log/mysql/error.log

# 缓存和缓存池
innodb_buffer_pool_size = 4G  # 服务器有 16GB 内存
innodb_log_file_size = 1G
innodb_log_buffer_size = 64M

# 限制最大连接数
max_connections = 500  # 根据实际情况调整
thread_cache_size = 50

# 临时表和表缓存
tmp_table_size = 256M
max_heap_table_size = 256M
table_open_cache = 2000

# 慢查询日志
slow_query_log = 1
slow_query_log_file = /var/log/mysql/slow.log
long_query_time = 1

# 二进制日志
log-bin = /var/lib/mysql/mysql-bin
binlog_format = row

# 其他设置
innodb_flush_log_at_trx_commit = 2
innodb_thread_concurrency = 0
innodb_file_per_table = 1
innodb_flush_method = O_DIRECT
innodb_io_capacity = 2000
innodb_read_io_threads = 4
innodb_write_io_threads = 4
```

### command

```sh
docker run -d \
  --name mysql \
  --restart=always \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=yhWJjI1eBqOhVwbjqRMS5YNfYU9Fslo4 \
  -e MYSQL_DATABASE=spring_moyin \
  -e MYSQL_USER=moyin \
  -e MYSQL_PASSWORD=VSf7FCiS3ltrxFzDpnGxwBuQetxU7J5Y \
  -v ~/mysql/mysql_data:/var/lib/mysql \
  -v ~/mysql/my.cnf:/etc/mysql/my.cnf \
  -v ~/mysql/logs:/var/log/mysql \
  -v ~/mysql/mysql-bin:/var/lib/mysql-bin \
  -v ~/mysql/mysql-files:/var/lib/mysql-files \
  --network=mysql_network \
  mysql:8.0
```

### 备份

```sh
docker exec mysql sh -c 'exec mysqldump --all-databases -uroot -p"your_root_password"' > backup.sql
```

### 恢复

```sh
cat backup.sql | docker exec -i mysql sh -c 'exec mysql -uroot -p"your_root_password"'
```

