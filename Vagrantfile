Vagrant.configure("2") do |config|

  # Конфігурація веб-сервера
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
    web.vm.network "forwarded_port", guest: 80, host: 8080

    web.vm.provision "shell", inline: <<-SHELL
      # Оновлення пакетів
      sudo apt update -y
      
      # Встановлення Nginx, PHP і необхідних розширень
      sudo apt install -y nginx php-fpm php-mysql php-cli php-curl php-gd php-mbstring php-xml unzip wget
      
      # Завантаження та розгортання WordPress
      cd /var/www/html
      sudo rm -rf wordpress
      sudo wget https://wordpress.org/latest.tar.gz
      sudo tar -xvzf latest.tar.gz
      sudo rm latest.tar.gz
      sudo chown -R www-data:www-data /var/www/html/wordpress

      # Налаштування Nginx для WordPress
      sudo tee /etc/nginx/sites-available/wordpress <<EOF
      server {
          listen 80;
          server_name localhost;
          root /var/www/html/wordpress;

          index index.php index.html index.htm;
          location / {
              try_files \$uri \$uri/ /index.php?\$args;
          }

          location ~ \.php\$ {
              include snippets/fastcgi-php.conf;
              fastcgi_pass unix:/run/php/php7.4-fpm.sock;
              fastcgi_param SCRIPT_FILENAME \$document_root\$fastcgi_script_name;
              include fastcgi_params;
          }
      }
      EOF

      sudo ln -s /etc/nginx/sites-available/wordpress /etc/nginx/sites-enabled/
      sudo rm /etc/nginx/sites-enabled/default
      sudo systemctl restart nginx php7.4-fpm
    SHELL
  end

  # Конфігурація серверу бази даних
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"
    db.vm.network "forwarded_port", guest: 3306, host: 13306

    db.vm.provision "shell", inline: <<-SHELL
      # Оновлення пакетів
      sudo apt update -y
      
      # Встановлення MariaDB
      sudo apt install -y mariadb-server mariadb-client

      # Налаштування бази даних для WordPress
      sudo mysql -e "CREATE DATABASE wordpress;"
      sudo mysql -e "CREATE USER 'wp_user'@'%' IDENTIFIED BY 'wp_pass';"
      sudo mysql -e "GRANT ALL PRIVILEGES ON wordpress.* TO 'wp_user'@'%';"
      sudo mysql -e "FLUSH PRIVILEGES;"

      # Дозволяємо зовнішнє підключення
      sudo sed -i "s/bind-address.*/bind-address = 0.0.0.0/" /etc/mysql/mariadb.conf.d/50-server.cnf
      sudo systemctl restart mariadb
    SHELL
  end

end
