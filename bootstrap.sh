#!/bin/bash

# Генеруємо SSH ключ, якщо не існує
if [ ! -f /home/vagrant/.ssh/id_rsa ]; then
  ssh-keygen -t rsa -b 2048 -f /home/vagrant/.ssh/id_rsa -q -N ""
fi

# Копіюємо публічний ключ на сервер
sshpass -p "vagrant" ssh-copy-id -o StrictHostKeyChecking=no -i /home/vagrant/.ssh/id_rsa.pub vagrant@192.168.56.11

# Створюємо директорію data з тестовими файлами
mkdir -p /home/vagrant/data
echo "Test file 1" > /home/vagrant/data/file1.txt
echo "Test file 2" > /home/vagrant/data/file2.txt

# Копіюємо скрипт резервного копіювання
cp /vagrant/backup.sh /home/vagrant/backup.sh
chmod +x /home/vagrant/backup.sh

# Додаємо cron завдання
(crontab -l 2>/dev/null; echo "0 2 * * * /home/vagrant/backup.sh >> /home/vagrant/backup.log 2>&1") | crontab -
