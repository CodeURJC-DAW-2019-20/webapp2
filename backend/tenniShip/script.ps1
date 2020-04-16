docker run --rm -v {{el path del proyecto}}:/usr/src/project -w /usr/src/project maven:alpine mvn package

docker image build -t padawansurjc/tennishipapp -f Docker/Dockerfile .

docker pull padawansurjc/tennishipapp

cd Docker