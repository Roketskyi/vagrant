Vagrant.configure("2") do |config|

  # Конфігурація веб-сервера
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/focal64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
    web.vm.network "forwarded_port", guest: 80, host: 8080
    web.vm.provision "shell", inline: <<-SHELL
      sudo apt update -y
      sudo apt install -y nginx php-fpm php-pgsql
      sudo systemctl enable nginx
      sudo systemctl start nginx
    SHELL
  end

  # Конфігурація серверу бази даних
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/focal64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"
    db.vm.network "forwarded_port", guest: 5432, host: 15432
    db.vm.provision "shell", inline: <<-SHELL
      sudo apt update -y
      sudo apt install -y postgresql postgresql-contrib
      sudo systemctl enable postgresql
      sudo systemctl start postgresql
    SHELL
  end

end
