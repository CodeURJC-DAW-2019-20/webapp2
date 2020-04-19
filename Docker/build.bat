cd ..

cd ./frontend/Angular

docker run --rm --name angular-container -v "%cd%":/angular -w /angular node:12.16.1 /bin/bash -c "npm install; npm run-script build"

xcopy /E "%cd%"\dist\Angular ..\..\backend\tenniShip\src\main\resources\static\new

cd ../../backend/tenniShip

docker run --rm -v "%cd%":/usr/src/project -w /usr/src/project maven:alpine mvn package

cd ../..

docker image build -t padawansurjc/tennishipapp -f Docker/Dockerfile .

docker login --username=%1 --password=%2

docker push padawansurjc/tennishipapp

cd Docker
