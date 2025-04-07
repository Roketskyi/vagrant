Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"
  
  # VM Configuration
  config.vm.provider "virtualbox" do |vb|
    vb.memory = "4096"
    vb.cpus = 2
    vb.name = "recipes-app"
  end

  # Network Configuration
  config.vm.network "private_network", ip: "192.168.56.10"
  config.vm.network "forwarded_port", guest: 8080, host: 8080
  config.vm.network "forwarded_port", guest: 3306, host: 3307

  # Provisioning Script
  config.vm.provision "shell", inline: <<-SHELL
    # Update system
    apt-get update
    apt-get upgrade -y

    # Install Java
    apt-get install -y openjdk-11-jdk

    # Install MySQL
    apt-get install -y mysql-server

    # Install Maven
    apt-get install -y maven

    # Install Git
    apt-get install -y git

    # Configure MySQL
    mysql -e "CREATE DATABASE recipes_db;"
    mysql -e "CREATE USER 'recipes_user'@'localhost' IDENTIFIED BY 'recipes_password';"
    mysql -e "GRANT ALL PRIVILEGES ON recipes_db.* TO 'recipes_user'@'localhost';"
    mysql -e "FLUSH PRIVILEGES;"

    # Create application directory
    mkdir -p /vagrant/app
    chown -R vagrant:vagrant /vagrant/app
  SHELL
end 