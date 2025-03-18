#!/bin/bash

# Зміни для шляхи до файлів
SERVER_IP="192.168.56.11"
SERVER_USER="vagrant"
REMOTE_BACKUP_DIR="/home/vagrant/backup"
LOCAL_DATA_DIR="/home/vagrant/data"
ARCHIVE_NAME="backup_$(date +%Y%m%d_%H%M%S).tar.gz"
LOG_FILE="/home/vagrant/backup.log"

# Логування початку
echo "[$(date)] Початок резервного копіювання" >> $LOG_FILE

# Перевірка доступності сервера
ping -c 1 $SERVER_IP > /dev/null 2>&1
if [ $? -ne 0 ]; then
  echo "[$(date)] Сервер недоступний!" >> $LOG_FILE
  exit 1
fi

# Перевірка наявності віддаленої папки
ssh ${SERVER_USER}@${SERVER_IP} "mkdir -p ${REMOTE_BACKUP_DIR}"

# Архівування даних
ARCHIVE_PATH="/home/vagrant/$ARCHIVE_NAME"
tar -czf $ARCHIVE_PATH -C $LOCAL_DATA_DIR .

# Передача архіву
scp $ARCHIVE_PATH ${SERVER_USER}@${SERVER_IP}:${REMOTE_BACKUP_DIR}/

# Видалення архіву локально
rm $ARCHIVE_PATH

# Очищення старих резервних копій на сервері, залишити 3 останні
ssh ${SERVER_USER}@${SERVER_IP} "ls -t ${REMOTE_BACKUP_DIR}/backup_*.tar.gz | tail -n +4 | xargs -r rm"

# Логування завершення
echo "[$(date)] Резервне копіювання завершено успішно" >> $LOG_FILE
