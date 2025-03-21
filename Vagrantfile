Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"

  config.vm.network "forwarded_port", guest: 8080, host: 8080
  config.vm.network "private_network", ip: "192.168.33.10"

  config.vm.provider "virtualbox" do |vb|
    vb.memory = "2048"
    vb.cpus = 2
  end

  config.vm.provision "shell", inline: <<-SHELL
    apt-get update
    apt-get upgrade -y
    
    apt-get install -y openjdk-11-jdk maven git
    
    java -version
    mvn -version
  SHELL
end
