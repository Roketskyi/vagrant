Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"

  # Переадресація порту SSH
  config.vm.network "forwarded_port", guest: 22, host: 2222
  config.vm.network "public_network"
end
