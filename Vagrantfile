Vagrant.configure("2") do |config|
  # Локальна машина
  config.vm.define "local" do |local|
    local.vm.box = "ubuntu/bionic64"
    local.vm.hostname = "local"
    local.vm.network "private_network", ip: "192.168.56.10"
    local.vm.provision "shell", path: "bootstrap.sh"
  end

  # Віддалений сервер
  config.vm.define "server" do |server|
    server.vm.box = "ubuntu/bionic64"
    server.vm.hostname = "server"
    server.vm.network "private_network", ip: "192.168.56.11"
    server.vm.provision "shell", inline: <<-SHELL
      mkdir -p /home/vagrant/backup
    SHELL
  end
end
